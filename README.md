# Prerequirement

* Ubuntu 14.04 64-bit 
* VLC media player 

This program is developed in Ubuntu 14.04

# Install

1.Install 

Please install VLC media player client for Ubuntu from http://www.videolan.org/vlc/download-ubuntu.html. You can follow the Installation the Command line way to install VCL.

2. Import 

	* Download this project code to your computer
	* Import project to Eclipse
	
		 File-> import-> Maven->Existing Maven projects
		 
# Run

1. Run Server

First, please run the Server.java(src/main/java/server/Server.java), and do not shutdown it.

2. Run Client
 
Run the MainFrame.java(src/main/java/MainFrame.java). A player panel will be shown. All the operations is based on this player.

3. Play video

	* Click "Get List" button
	* Choose one of the video title
	* Click "Play"
	
> Notice: Please ensure that video files which shown in Player list are exist in project root path(/). The file(/videoList.xml) defines the list of video. You can modify this file to match your video file. 
