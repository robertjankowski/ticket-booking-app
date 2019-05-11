[![Build Status](https://travis-ci.com/robertjankowski/ticket-booking-app.svg?token=xJWSE2zxzWsgsf4jc3ef&branch=master)](https://travis-ci.com/robertjankowski/ticket-booking-app)
# ticket-booking-app
Ticket booking app for Touk recruitment process


## Running and testing app
To run application execute
```bash
./run.sh -r  
```
To run tests
```bash
./run.sh -t
```
Moreover, the Travis is configured so every push to repository means that tests are executed.

## Business scenario (use case)
To run sample use case execute `use_case.sh`

## Endpoints
[`jq`](https://github.com/stedolan/jq) was used to process JSON. Usage example for Windows users
```text
curl <url> | /path/to/jq.exe
```

#### List of available endpoints

**RoomController**
1. /room - get all room (GET)
2. /room/reserve/{id}/{title}/{date}/{userId} - reserve room {id} for movie with {title} at {date} for user with {userId}

Example 1.
```bash
curl localhost:8080/room | jq-win64.exe
```
```json
[
  {
    "id": 1,
    "movies": [
      {
        "title": "Jason Bourne",
        "screeningTimes": [
          "2019-05-08T12:20:00",
          "2019-05-08T14:45:00",
          "2019-05-08T16:30:00"
        ]
      },
      {
        "title": "Inception",
        "screeningTimes": [
          "2019-05-08T12:30:00",
          "2019-05-08T14:55:00",
          "2019-05-08T16:00:00",
          "2019-05-08T10:20:00",
          "2019-05-08T18:00:00"
        ]
      },
      {
        "title": "The Dark Knight",
        "screeningTimes": [
          "2019-05-08T10:40:00",
          "2019-05-08T12:55:00",
          "2019-05-08T17:30:00"
        ]
      },
      {
        "title": "Piękny umysł",
        "screeningTimes": [
          "2019-05-08T12:45:00",
          "2019-05-08T13:44:00",
          "2019-05-08T16:10:00"
        ]
      }
    ],
    "row": 5,
    "seats": 10
  },
  {
    "id": 2,
    "movies": [
      {
        "title": "Inception",
        "screeningTimes": [
          "2019-05-08T12:30:00",
          "2019-05-08T14:55:00",
          "2019-05-08T16:00:00",
          "2019-05-08T10:20:00",
          "2019-05-08T18:00:00"
        ]
      },
      {
        "title": "The Dark Knight",
        "screeningTimes": [
          "2019-05-08T10:40:00",
          "2019-05-08T12:55:00",
          "2019-05-08T17:30:00"
        ]
      },
      {
        "title": "Piękny umysł",
        "screeningTimes": [
          "2019-05-08T12:45:00",
          "2019-05-08T13:44:00",
          "2019-05-08T16:10:00"
        ]
      }
    ],
    "row": 4,
    "seats": 20
  }
]
```
Example 2. (reserve room 1, movie Inception, user with id = 2 bought 2 tickets: for adult and child)
```bash
curl -X POST -H "Content-Type: application/json" \ 
    -d "[ \"ADULT\", \"CHILD\" ]" localhost:8080/room/reserve/1/Inception/2019-05-08T12:30:00/2 | jq-win64.exe
```
```json
{
  "message": "Reservation for: User{id=2, name='Mateusz', surname='Janik', payments=-37.5} room: 1 movie: Inception total cost: 37.5"
}
```

**Movie controller**
1. /movie - get all movies sorted by title and screening time (GET)
2. /movie/date?time=some_date - get movies which screening times are greater than 15 minutes (GET)

Example 1:
```bash
curl localhost:8080/movies | jq-win64.exe
```
```json
[
    {
      "title": "Inception",
      "screeningTimes": [
        "2019-05-08T10:20:00",
        "2019-05-08T12:30:00",
        "2019-05-08T14:55:00",
        "2019-05-08T16:00:00",
        "2019-05-08T18:00:00"
      ]
    },
    {
      "title": "Jason Bourne",
      "screeningTimes": [
        "2019-05-08T12:20:00",
        "2019-05-08T14:45:00",
        "2019-05-08T16:30:00"
      ]
    },
    {
      "title": "Piękny umysł",
      "screeningTimes": [
        "2019-05-08T12:45:00",
        "2019-05-08T13:44:00",
        "2019-05-08T16:10:00"
      ]
    },
    {
      "title": "The Dark Knight",
      "screeningTimes": [
        "2019-05-08T10:40:00",
        "2019-05-08T12:55:00",
        "2019-05-08T17:30:00"
      ]
    }
  ]
```
Example 2:
```bash
curl localhost:8080/movie/date?time=2019-05-08T12:19:00 | jq-win64.exe
```
```json
[
  {
    "title": "Inception",
    "screeningTimes": [
      "2019-05-08T12:30:00"
    ]
  },
  {
    "title": "Jason Bourne",
    "screeningTimes": [
      "2019-05-08T12:20:00"
    ]
  }
]
```

**UserController**
1. /user - add new user (POST)
2. /user - list all users (GET)
3. /{name}/{surname} - get user with {name} and {surname} (GET)
4. /{name}/{surname} - remove user with {name} and {surname} (DELETE)

Example 1.
```bash
curl -X POST -H "Content-Type: application/json" \
    -d "{ \"name\": \"Robert\", \"surname\": \"Jankowski\" }" localhost:8080/user | jq-win64.exe
```
```json
{
  "name": "Robert",
  "surname": "Jankowski",
  "payments": 0
}
```

Example 2.
```bash
curl localhost:8080/user | jq-win64.exe
```
```json
[
  {
    "name": "Jan",
    "surname": "Nowak",
    "payments": 0
  },
  {
    "name": "Mateusz",
    "surname": "Janik",
    "payments": 0
  },
  {
    "name": "Bartek",
    "surname": "Kaczmarek",
    "payments": 0
  }
]

```
Example 3.
```bash
curl localhost:8080/user/Jan/Nowak | jq-win64.exe
```
```
{
    "name": "Jan",
    "surname": "Nowak",
    "payments": 0
}
```

Example 4.
```bash
curl localhost:8080/user/Jan/Nowak | jq-win64.exe
```
```json
{
  "message": "User removed"
}
```