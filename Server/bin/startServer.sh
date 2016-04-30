#!/bin/bash
mysql -u root -proot < ./create_table.sql
killall -9 java
find . -type f -name '*.class' -delete
javac -cp ../lib/mysql-connector-java-5.1.38-bin.jar:. guuber/cmu/edu/server/Server.java
java -cp ../lib/mysql-connector-java-5.1.38-bin.jar:. guuber/cmu/edu/server/Server
