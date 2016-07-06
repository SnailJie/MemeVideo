package client;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

public class MainFrame extends JFrame{

	 private JPanel contentPane; //顶层容器，整个播放页面的容器  
	    
	    
	    private JPanel panel;   //控制区域容器  
	   
	    private JPanel progressPanel;   //进度条容器  
	    private JPanel controlPanel;    //控制按钮容器  
	    private JButton btnStop,btnPlay,btnPause;   //控制按钮，停止、播放、暂停  
	    
	      
	    EmbeddedMediaPlayerComponent playerComponent;   //媒体播放器组件  
	    public static void main(String[] args) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					new MainFrame().setVisible(true);;
				}
			});
		}

	    //MainWindow构造方法，创建视屏播放的主界面  
	    public MainFrame(){  
	        setTitle("VLC Player");  
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
	        setBounds(200,80,900,600);  
	        contentPane=new JPanel();  
	        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));  
	        contentPane.setLayout(new BorderLayout(0,0));  
	        setContentPane(contentPane);  
	          
	          
	        //Video List
			//leftPanel
			DefaultListModel<String> titleList=new DefaultListModel();
			titleList.addElement("avengers-featurehp");
			titleList.addElement("B");
			JList<String> list=new JList<String>(titleList);
			JScrollPane scp=new JScrollPane(list);
			contentPane.add(scp,BorderLayout.WEST);
	         
	          
	        /*视屏窗口中播放界面部分*/  
	        JPanel videoPane=new JPanel();  
	        contentPane.add(videoPane, BorderLayout.CENTER);  
	        videoPane.setLayout(new BorderLayout(0,0));  
	          
	        playerComponent=new EmbeddedMediaPlayerComponent();  
	        videoPane.add(playerComponent);  
	          
	        /*视屏窗口中控制部分*/  
	          
	        panel=new JPanel();     //实例化控制区域容器  
	        videoPane.add(panel,BorderLayout.SOUTH);  
	          
	        progressPanel=new JPanel(); //实例化进度条容器  
	        panel.add(progressPanel, BorderLayout.NORTH);  
	   
	          
	        controlPanel=new JPanel();      //实例化控制按钮容器  
	        panel.add(controlPanel,BorderLayout.SOUTH);  
	          
	        //添加停止按钮  
	        btnStop=new JButton("停止");  
	        btnStop.addMouseListener(new MouseAdapter() {  
	            @Override  
	            public void mouseClicked(MouseEvent e) {  
	                // TODO Auto-generated method stub  
//	                PlayerMain.stop();  
	            }  
	        });  
	        controlPanel.add(btnStop);  
	          
	        //添加播放按钮  
	        btnPlay=new JButton("播放");  
	        btnPlay.addMouseListener(new MouseAdapter() {  
	            @Override  
	            public void mouseClicked(MouseEvent e) {  
	                // TODO Auto-generated method stub  
	            	playerComponent.getMediaPlayer().playMedia("avengers-featurehp.mp4");// please
	        		
	            }  
	        });  
	        controlPanel.add(btnPlay);  
	          
	        //添加暂停按钮  
	        btnPause=new JButton("暂停");  
	        btnPause.addMouseListener(new MouseAdapter() {  
	            @Override  
	            public void mouseClicked(MouseEvent e) {  
	                // TODO Auto-generated method stub  
	                PlayerMain.pause();  
	            }  
	        });  
	        controlPanel.add(btnPause);  
	          
	    }  
	      
	    //获取播放媒体实例（某个视频）  
	    public EmbeddedMediaPlayer getMediaPlayer() {  
	        return playerComponent.getMediaPlayer();  
	    }  
	      
	   
	
}
