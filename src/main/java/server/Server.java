package server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Server {

	static private XMLReader reader;

	public Server() {
		reader = new XMLReader("videoList.xml");
	}

	static public List<VideoFile> getList() {
		reader = new XMLReader("videoList.xml");
		return reader.getList();
	}

	public static void main(String[] args) {
		try {
			ServerSocket server = new ServerSocket(1234);
			Socket client = server.accept();
			ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			PrintWriter infoOut = new PrintWriter(client.getOutputStream());
			// Recv choosed video
			while (true) {
				String str = in.readLine();
				if (str.equals("GetList")) {
					if (getList().size() != 0) {
						infoOut.println("ReplyList");
						infoOut.flush();
						VideoList videoList = new VideoList();
						videoList.setVideoList(getList());
						out.writeObject(videoList);
						out.flush();
					} else {
						infoOut.println("Nothing");
						infoOut.flush();
					}
				}
				else {
					sendFile(client, str);
				}

			}

		} catch (IOException e) {

		}

	}

	private static void sendFile(Socket socket, String title) throws IOException {
		int length = 0;
		DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
		// File file = new File("1.mp4");
		File file = new File(title);
		FileInputStream fis = new FileInputStream(file);
		byte[] sendBytes = new byte[1024];
		while ((length = fis.read(sendBytes, 0, sendBytes.length)) > 0) {
			dos.write(sendBytes, 0, length);
			dos.flush();
		}
		if (fis != null) {
			fis.close();
		}
		if (dos != null) {
			dos.close();
		}
	}

}
