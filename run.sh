#!/bin/bash

print_help() {
    echo "Script for testing and running application"
    echo "Options: "
    echo "-t    TEST"
    echo "-r    RUN APP"
    echo "-h    PRINT HELP"
}

if [[ $# -lt 1  || $1 == "-h" ]]; then
    print_help
fi

if [[ $1 == "-t" ]]; then
    mvn test
elif [[ $1 == "-r" ]]; then
    mvn spring-boot:run
fi