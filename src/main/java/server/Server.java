package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Server {

	static private XMLReader reader;
	
	public Server() {
		reader = new XMLReader("videoList.xml");
	}
	
	static public List<VideoFile> getList()
	{
		reader = new XMLReader("videoList.xml");
		return reader.getList();
	}
	
	public static void main(String[] args) {
		try{
			
			ServerSocket server = new ServerSocket(1234);
			Socket client = server.accept();
			//对象数据的输入与输出，需要用ObjectInputStream和ObjectOutputStream进行
			ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
			//返回给客户端的对象
			VideoList videoList = new VideoList();
			videoList.setVideoList(getList());
			out.writeObject(videoList);
			out.flush();
			out.close();
				
		}catch (IOException e){
			
		}
		
		
		
	}
	
}
