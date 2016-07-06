package server;

import java.util.List;

public class Server {

	private XMLReader reader;
	
	public Server() {
		reader = new XMLReader("videoList.xml");
	}
	
	public List<VideoFile> getList()
	{
		return reader.getList();
	}
}
