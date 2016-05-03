#!/bin/bash

#this shell script define for starting server

echo -n "Enter the mysql username: "
read username
echo -n "Enter the mysql password: "
read -s password

#create mysql database
mysql -u $username -p$password < ./create_table.sql
killall -9 java
find . -type f -name '*.class' -delete
javac -cp ../lib/mysql-connector-java-5.1.38-bin.jar:. guuber/cmu/edu/server/Server.java
java -cp ../lib/mysql-connector-java-5.1.38-bin.jar:. guuber/cmu/edu/server/Server
