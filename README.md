# Guuber [![Build Status](https://travis-ci.org/helunwencser/Guuber.svg?branch=master)](https://travis-ci.org/helunwencser/Guuber)

##Android Application for Drivers/Passengers to find Passengers/Drivers

18-641, Spring 2016, Carnegie Mellon University  

Lunwen He, Yanning Liu, Ziming Wang

##How to run

###Runtime requirement

Java: JDK 8
Android 5.1
MySQL

###Download
Guuber is a public repository hosted in github. The link of Guuber project is: 
https://github.com/helunwencser/Guuber.git

To download Guuber:
```bash
git clone https://github.com/helunwencser/Guuber.git
```

###Server
After downloading Guuber, you can start server using those commands:
```bash
cd Guuber/Server/src/
sudo chmod +x ./startServer.sh
./startServer.sh
```
Note: Make sure you have installed MySQL database. It will promote you input your MySQL username and  password.

###Client
1. The Guuber/Guuber directory is the Android project for Guuber client. You can import it into Android Studio.
2. Configure Server public ip in ws/remote/ServerConfig.java
3. Google API key: you can either a. put your own Google Map API key in AndroidManifest.xml and res/values/google_maps_api.xml, or b. Use our Google Map API (already written in the two xml files), if so, please provide us with the SHA-1 certificate fingerprint of your device, as we need to add it to our API credentials.
4. You can run app either on an Android device or an emulator with hardcoded GPS information.


##File structure
Document: requirements, design, test, and other related documents.

Guuber: the source code of client

Server: the source code of server

###Contact us
If you have any issue to setup Guuber, please feel free to contact us.

Lunwen He: lunwenh@andrew.cmu.edu

Yanning Liu: yanningl@andrew.cmu.edu

Ziming Wang: zimingw@andrew.cmu.edu


##Acknowledgements:
---------------------------------------------------------
Bob Singh and the teaching staff of 18-641, 
Java smartphone, at Carnegie Mellon University, Spring 2016.
