package server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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
			
			
			 BufferedReader in = new BufferedReader(new InputStreamReader(  
		                client.getInputStream())); 
			
			
			//Recv choosed video
			while(true)
			{
				
				//1.get list request
				 String str = in.readLine(); 
				 if(str.equals("GetList"))
				 {
					 VideoList videoList = new VideoList();
						videoList.setVideoList(getList());
						out.writeObject(videoList);
						out.flush();
				 }
				 else{
					 
					 sendFile(client,str);
				 }
				//2.choose video request
				
				
			}
			
			
				
		}catch (IOException e){
			
		}
		
		
		
	}
	
	

    private static void sendFile(Socket socket,String title) throws IOException {
		// TODO Auto-generated method stub
    	 int length = 0;
    	DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
         File file = new File("1.mp4");
         FileInputStream fis = new FileInputStream(file);
         byte[] sendBytes = new byte[1024];
         while ((length = fis.read(sendBytes, 0, sendBytes.length)) > 0) {
             dos.write(sendBytes, 0, length);
             dos.flush();
         }
         if(fis!=null)
         {
        	 fis.close();
         }
         if(dos!=null)
         {
        	 dos.close();
         }
	}
    
    
    
    
	
}
