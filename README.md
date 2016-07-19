# Prerequirements

* windows 7 64-bit 
* VLC media player for windows 64-bit 

 

# Install

* Install 

Please install VLC media player client at C:\VLC for [**windows7 64-bit**](http://www.videolan.org/vlc/download-windows.html). 

>Notice: Please choose installer for 64 bit version

* Import 

	* Download this project code to your computer
	* Import project to Eclipse
	
		 File-> import-> Maven->Existing Maven projects
		 
* Environment setting
 
 VLC_PLUGIN_PATH:  C:\VLC\plugins
 Path:   C:\VLC\plugins;
 
 reboot your computer after set environment variable.
 

		 
		 
# Run

* Run Server

First, please run the Server.java(src/main/java/server/Server.java), and do not shutdown it.

* Run Client
 
Run the MainFrame.java(src/main/java/MainFrame.java). A player panel will be shown. All the operations is based on this player.

* Play video

	* Click "Get List" button
	* Choose one of the video title
	* Click "Play"
	
> Notice: Please ensure that video files which shown in Player list are exist in project root path(/). The file(/videoList.xml) defines the list of video. You can modify this file to match your video file. 
