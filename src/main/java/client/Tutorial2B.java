package client;

import java.awt.Container;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;

public class Tutorial2B {

	private final EmbeddedMediaPlayerComponent mediaPlayerComponent;
	public Container container ;
	public JFrame frame;
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new Tutorial2B();
			}
		});
	}

	private Tutorial2B() {
//		frame = new JFrame("vlcj Tutorial");
		frame = new MainFrame();
		//container = frame.getContentPane();
		
		//choose video
		
		//leftPanel
		DefaultListModel<String> titleList=new DefaultListModel();
		titleList.addElement("A");
		titleList.addElement("B");
		JList<String> list=new JList<String>(titleList);
		JScrollPane scp=new JScrollPane(list);
		scp.setBounds(1, 1, 300, 400);
		//container.add(scp);
		 
		 
		 

		
		
		
		
		//player
		mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
//		mediaPlayerComponent.
		//mediaPlayerComponent.setBounds(100, 2, 100, 300);
		// frame.setContentPane(mediaPlayerComponent);
//		container.add(mediaPlayerComponent);

		frame.setLocation(100, 100);
		frame.setSize(1000, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		mediaPlayerComponent.getMediaPlayer().playMedia("avengers-featurehp.mp4");// please
																					// change
																					// it
																					// to
																					// an
																					// existed
																					// media
																					// file
	}
}
