package view;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Client;
import model.Message;

@SuppressWarnings("serial")
public class StartWindowPanel extends JPanel{
	
	JButton join;
	JLabel enterUserName;
	JLabel alreadyExists;	
	JTextFieldLimit username;
	MainFrame mainFrame;
	TestFrame chatFrame;
	TreeSet<String> unread;
	Client client;
	double h;
	double w;
	Font f;
	
	TreeMap<String,ArrayList<Message>> tm;
	
	String enteredUsername;
	
	public StartWindowPanel(Client client,MainFrame mainFrame, TreeMap<String,ArrayList<Message>> tm,TreeSet<String> unread){
		setLayout(null);
		setSize(600,550);
		this.tm = tm;
		this.unread=unread;
		this.client=client;
		this.mainFrame = mainFrame;
		h = mainFrame.getHeight();
		w = mainFrame.getWidth();
		f = new Font("Lucida Sans Typewriter", Font.BOLD, 20);
		
		enterUserName = new JLabel("      Enter username: ");
		//enterUserName.setFont(f);
		enterUserName.setBounds((int)(w*0.5-75), (int)(h*0.3), 150, 30);
		//enterUserName.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		add(enterUserName);
		
		
		username = new JTextFieldLimit(15);
		//username.setText("@");
		username.setHorizontalAlignment(JTextFieldLimit.CENTER);
		username.setBounds((int)(w*0.5-110), enterUserName.getY()+enterUserName.getHeight(), 220, 50);
		//Border roundedBorder = new LineBorder(Color.black, 1, false);
		//username.setBorder(roundedBorder);
		//username.setText("@username");
		username.setFont(f);
		username.setForeground(Color.BLACK);
		//username.getDocument().addDocumentListener(new MyDocumentListener(playerNameTyped));
		add(username);
		
		
		
		join = new JButton("Join");
		//join.setBounds(265, 180, 300, 60);
		join.setBounds((int)(w*0.5-75), username.getY()+username.getHeight()+40, 150, 50);
		add(join);
		join.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Boolean isValid = true;
				
				try {
					isValid = client.isValid(username.getText());
					System.out.println(isValid);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				if(!isValid) {
					add(alreadyExists);
					revalidate();
					repaint();
				}
				else {
					enteredUsername = username.getText();
					if(enteredUsername.charAt(0) == '@')
						enteredUsername = enteredUsername.substring(1, enteredUsername.length()-1);
					System.out.println(enteredUsername);
					try {
						chatFrame = new TestFrame(enteredUsername,mainFrame, tm,unread,client);
						chatFrame.setVisible(true);
						mainFrame.setVisible(false);
						chatFrame.revalidate();
						chatFrame.repaint();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					
					
				}
			}
		});
		
		
		alreadyExists = new JLabel("Username already exists!");
		alreadyExists.setBounds(300, 150, 200, 50);
		alreadyExists.setBounds((int)(w*0.5-75), username.getY()+username.getHeight(), 150, 25);
		alreadyExists.setForeground(Color.RED);
		//EnterUserName.setBorder(BorderFactory.createEmptyBorder());
		//logo.setContentAreaFilled(false);
		
		
	}
	
	protected void paintComponent(Graphics G){
		super.paintComponent(G);
		Image image = new ImageIcon("bg.jpg").getImage();
		
		G.drawImage(image, 0, 0, this);
	}

}