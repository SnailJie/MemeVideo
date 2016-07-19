package client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import com.sun.jna.NativeLibrary;

import server.VideoFile;
import server.VideoList;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

public class MainFrame extends JFrame {

	private JPanel contentPane;
	public DefaultListModel<String> titleList;
	private JPanel panel;
	private JPanel progressPanel;
	private JPanel controlPanel;
	private JButton btnStop, btnPlay, btnPause;
	static String IP = "127.0.0.1";
	static int PORT = 1234;
	private Socket server;
	private ObjectInputStream in;
	private PrintWriter out;
	private BufferedReader infoIn;
	private String selectVideoTitle = null;
	private EmbeddedMediaPlayerComponent playerComponent;

	public static void main(String[] args) {
		String NATIVE_LIBRARY_SEARCH_PATH = "C:\\VLC";
		NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), NATIVE_LIBRARY_SEARCH_PATH);

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

		titleList = new DefaultListModel();
		JList<String> list = new JList<String>(titleList);

		JPanel listPanel = new JPanel();
		listPanel.setLayout(new BorderLayout());
		// listPanel.setLayout(new FlowLayout(1));
		listPanel.setSize(100, 400);

		JButton getListBtn = new JButton("Get List");
		getListBtn.setPreferredSize(new Dimension(150, 30));
		getListBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				connectServer();
				out.println("GetList");
				out.flush();
				SocketUtil.getVideoList(infoIn, in, titleList);

			}
		});

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
				playerComponent.getMediaPlayer().stop();
			}
		});
		controlPanel.add(btnStop);

		btnPlay = new JButton("PLAY");
		btnPlay.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				selectVideoTitle = list.getSelectedValue();
				if (selectVideoTitle == null) {
					JOptionPane.showMessageDialog(null, "Please select a video", " Error ", JOptionPane.ERROR_MESSAGE);
				} else {
					out.println(selectVideoTitle);
					out.flush();
					SocketUtil.recvFile(server, selectVideoTitle);

					playerComponent.getMediaPlayer().playMedia(selectVideoTitle);//
				}

			}

		});
		controlPanel.add(btnPlay);

		btnPause = new JButton("PAUSE");
		btnPause.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				playerComponent.getMediaPlayer().pause();
			}
		});
		controlPanel.add(btnPause);

	}

	public EmbeddedMediaPlayer getMediaPlayer() {
		return playerComponent.getMediaPlayer();
	}

	private void connectServer() {
		try {
			server = new Socket(IP, PORT);
			in = new ObjectInputStream(server.getInputStream());
			out = new PrintWriter(server.getOutputStream());
			infoIn = new BufferedReader(new InputStreamReader(server.getInputStream()));
			// BufferedReader wt = new BufferedReader(new
		} catch (UnknownHostException e2) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "UnknownHost,Please check IP address ", " Connection Fail ",
					JOptionPane.ERROR_MESSAGE);
			e2.printStackTrace();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "Please ensure server is avaliable  ", " Connection Fail ",
					JOptionPane.ERROR_MESSAGE);
			e2.printStackTrace();
		}

	}

}
