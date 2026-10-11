#!/bin/sh
mv /otus_homework_2/* .
mvn test -Dmoon_ip=$MOON_IP -Dmoon_port=$MOON_PORT -Dbrowser=$BROWSER