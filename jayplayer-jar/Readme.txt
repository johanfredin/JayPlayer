JayPlayer version 0.9
=============

Copyright (c) 2013 Johan Fredin.

The JayPlayer is a free to use open source music player built in java.
JayPlayer will play all your mp3 and wav files stored locally.
Users can load by draging tracks into the player or by loading through the file menu.
Users can also create their own playlists.

Playlists and user settings will be saved into two separate JSON files that will be created in users home folder.
Removing theese files will not result in JayPlayer not working.

JayPlayer depends on javafx and therefore will not run on the OpenJDK.


Build Instructions
-----------------------

JayPlayer can be built using Maven. For installing maven please refer to the official site: http://maven.apache.org/download.cgi.
There you will find all info you need for installing maven. But if you use Linux, maven can be installed simply by opening the terminal
and writing: "sudo apt-get install maven". 

After downloading the repository simply open up a command prompt inside the "jayplayer-jar" directory and type: "mvn install". 
This will compile all source files and place it into a runnable jar file inside the target directory. As of version 0.9, maven will create 
two jar files for you. The one you want to run is "jayplayer-0.9.one-jar.jar". Run it with the oracle java runtime. 

More Maven commands:

	mvn clean	-	will remove everything from target directory
	mvn clean install	- will remove everything and then install it again	
