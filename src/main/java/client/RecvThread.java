package client;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class RecvThread implements Runnable {

	public Socket client;
	private String fileName;

	public RecvThread(Socket client, String name) {
		this.client = client;
		this.fileName = name;
		
	}

	public void run() {
		try{
			 
		System.out.println( "Thread running");
		DataInputStream dis = new DataInputStream(client.getInputStream());
		// 文件名和长度
		long fileLength = dis.readLong();
		FileOutputStream fos = new FileOutputStream(new File(fileName));

		byte[] sendBytes = new byte[1024];
		int transLen = 0;
		System.out.println("----开始接收文件<" + fileName + ">,文件大小为<" + fileLength + ">----");
		while (true) {
			int read = 0;
			read = dis.read(sendBytes);
			if (read == -1)
				break;
			transLen += read;
			System.out.println("接收文件进度" + 100 * transLen / fileLength + "%...");
			fos.write(sendBytes, 0, read);
			fos.flush();
		}
		System.out.println("----接收文件<" + fileName + ">成功-------");
		}
		catch(IOException e)
		{
			
		}

	}

}
