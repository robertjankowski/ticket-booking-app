#!/bin/bash
# remember to specify path to `jq`

read -p "Select date to watch movie (pattern: yyyy-MM-ddTHH:mm:ss): " date

# get all of movies which are 15 minutes before {date} or more
path_for_movies="localhost:8080/movie/date?time="${date}
curl -s ${path_for_movies} | ../jq-win64.exe > movies.txt
cat movies.txt

read -p "Choose movie title: " title
read -p "Specify screening time (pattern: yyyy-MM-ddTHH:mm:ss): " movie_time

path_for_rooms="localhost:8080/room/movie/"${title}"/"${movie_time}
curl -s ${path_for_rooms} | ../jq-win64.exe > rooms.txt
cat rooms.txt

read -p "Select room id: " room_id
# add new user or select existing one
read -p "Name: " name
read -p "Surname: " surname

path_for_user="localhost:8080/user/"${name}"/"${surname}
curl -s ${path_for_user} | ../jq-win64.exe -r '.id' > user.txt
user_id=$(cat user.txt)
if [[ ! -s user.txt ]]; then
    echo "User does not exist. Adding new user..."
    curl -s -X POST -H "Content-Type: application/json" \
    -d "{ \"name\": \""${name}"\", \"surname\": \""${surname}"\" }" "localhost:8080/user" > tmp.txt
    curl -s ${path_for_user} | ../jq-win64.exe -r '.id' > user.txt
    user_id=$(cat user.txt)
fi

read -p "Select tickets [ADULT, CHILD, STUDENT] (commas between): " tickets
ticket_request="[ "
for ticket in $(echo ${tickets} | sed -n 1'p' | tr ',' '\n')
do
    ticket_request="$ticket_request \"${ticket}\",\n"
done
ticket_request=${ticket_request::-3}" ]"
echo -e ${ticket_request} > ticket_request.json

path_to_reserve="localhost:8080/room/reserve/"${room_id}"/"${title}"/"${movie_time}"/"${user_id}
curl -s -X POST -H "Content-Type: application/json" -d  @ticket_request.json "${path_to_reserve}" > result.txt
cat result.txt | ../jq-win64.exe

rm *.txt ticket_request.json