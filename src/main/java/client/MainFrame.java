package client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import server.VideoFile;
import server.VideoList;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

public class MainFrame extends JFrame {

	private JPanel contentPane;  
	public DefaultListModel<String> titleList;

	private JPanel panel;  

	private JPanel progressPanel;  
	private JPanel controlPanel;  
	private JButton btnStop, btnPlay, btnPause; 
	static String IP = "127.0.0.1";
	static int PORT = 1234;
	Socket server;
	ObjectInputStream in;
	PrintWriter out;

	EmbeddedMediaPlayerComponent playerComponent;  

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new MainFrame().setVisible(true);
			}
		});

	}

	public MainFrame() {
		setTitle("VLC Player");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(200, 80, 900, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		// init Socket

		try {
			server = new Socket(IP, PORT);
			in = new ObjectInputStream(server.getInputStream());
			out = new PrintWriter(server.getOutputStream());
			// BufferedReader wt = new BufferedReader(new
		} catch (UnknownHostException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		titleList = new DefaultListModel();
		JList<String> list = new JList<String>(titleList);

		// Get Video List
		JPanel listPanel = new JPanel();
		listPanel.setLayout(new BorderLayout());
		// listPanel.setLayout(new FlowLayout(1));
		listPanel.setSize(100, 400);

		JButton getListBtn = new JButton("Get List");
		getListBtn.setPreferredSize(new Dimension(150, 30));
		getListBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				// List<VideoFile> videoList;
				
				out.println("GetList");
				out.flush();
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

		});

		// Video List

		JScrollPane scp = new JScrollPane(list);
		scp.setSize(190, 500);

		listPanel.add(getListBtn, BorderLayout.NORTH);
		listPanel.add(scp, BorderLayout.CENTER);
		// listPanel.setBounds(1, 1, 200, 300);
		contentPane.add(listPanel, BorderLayout.WEST);

		JPanel videoPane = new JPanel();
		contentPane.add(videoPane, BorderLayout.CENTER);
		videoPane.setLayout(new BorderLayout(0, 0));

		playerComponent = new EmbeddedMediaPlayerComponent();
		videoPane.add(playerComponent);


		panel = new JPanel();  
		videoPane.add(panel, BorderLayout.SOUTH);

		progressPanel = new JPanel();  
		panel.add(progressPanel, BorderLayout.NORTH);

		controlPanel = new JPanel();  
		panel.add(controlPanel, BorderLayout.SOUTH);

		btnStop = new JButton("STOP");
		btnStop.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				// PlayerMain.stop();
				playerComponent.getMediaPlayer().stop();
			}
		});
		controlPanel.add(btnStop);

		btnPlay = new JButton("PLAY");
		btnPlay.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				String selectName = list.getSelectedValue();
				out.println(selectName);
				out.flush();
				System.out.println("send list"+list.getSelectedValue());
				
				recvFile(server,selectName);
				playerComponent.getMediaPlayer().playMedia(selectName);//
				// please

				
				
//				playerComponent.getMediaPlayer().playMedia(list.getSelectedValue());
			}
		});
		controlPanel.add(btnPlay);

		btnPause = new JButton("PAUSE");
		btnPause.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				// PlayerMain.pause();
				playerComponent.getMediaPlayer().pause();
			}
		});
		controlPanel.add(btnPause);

	}

	public EmbeddedMediaPlayer getMediaPlayer() {
		return playerComponent.getMediaPlayer();
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
                System.out.println("Start recive file...");
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
