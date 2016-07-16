package client;

import java.awt.HeadlessException;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

import server.VideoFile;
import server.VideoList;

public class SocketUtil {


    public static void getVideoList( BufferedReader infoIn,ObjectInputStream in,DefaultListModel<String> titleList)   {
		// TODO Auto-generated method stub
    	try {
    		String recvInfo = infoIn.readLine(); 
			if(recvInfo.equals("Nothing")) {
				JOptionPane.showMessageDialog(null, "No videos are available ", " ERROR ", JOptionPane.ERROR_MESSAGE);
				return;
			}
		} catch (HeadlessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	VideoList videoback = new VideoList();
		try {
			videoback = (VideoList) in.readObject();
			List<VideoFile> videoList = videoback.getVideoList();
			for (int i = 0; i < videoList.size(); i++) {
				titleList.addElement(videoList.get(i).getFilename());
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
    

    public static void recvFile(Socket socket,String name) {
        byte[] inputByte = null;
        int length = 0;
        DataInputStream dis = null;
        FileOutputStream fos = null;
        try {
            try {
                dis = new DataInputStream(socket.getInputStream());
                fos = new FileOutputStream(new File(name));
                inputByte = new byte[1024];
                while ((length = dis.read(inputByte, 0, inputByte.length)) > 0) {
                    System.out.println(length);
                    fos.write(inputByte, 0, length);
                    fos.flush();
                }
                System.out.println("Done");
            } finally {
                if (fos != null)
                    fos.close();
                if (dis != null)
                    dis.close();
                if (socket != null)
                    socket.close();
            }
        } catch (Exception e) {
        }
    }
	
	
}
