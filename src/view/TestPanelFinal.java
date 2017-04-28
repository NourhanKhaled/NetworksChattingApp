package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import model.Client;
import model.Message;

@SuppressWarnings("serial")
public class TestPanelFinal extends JPanel{

	JPanel chatBox;
	JTextFieldLimitSpace messageBox;

	//JTextPane tempPanel;
	JLabel tempPanel;
	JScrollPane scrollPane;
	
	JButton send;
	JButton serverList;
	JButton logout;

	JTextArea memberListBox;
	JPanel memberListPanel;
	String enteredUsername;
	TreeMap<String,ArrayList<Message>> tm;
	TreeMap<String, ChatBoxPanel> history;
	TreeSet<String> hasUnreadMessage;
	Client client;
	String me;
	String nameClient = "";	
	TestFrame testFrame;
	//ArrayList<Message> chatHistory;
	


	public TestPanelFinal(String me,TreeMap<String,ArrayList<Message>> tm,TreeSet<String> hasUnreadMessage,Client client, TestFrame testFrame, TreeMap<String, ChatBoxPanel> history){
		this.tm=tm;
		this.me=me;
		this.hasUnreadMessage= hasUnreadMessage;
		this.client=client;
		setLayout(null);
		setSize(600,550);

		this.testFrame = testFrame;
		this. history = history;
		//chatHistory = new ArrayList<Message>();
		/*		chatHistory.add(new Message(true, "hi"));
		chatHistory.add(new Message(false, "oh, hi there!"));
		chatHistory.add(new Message(true, "doing good?"));
		chatHistory.add(new Message(false, "yeah I'm doing quite alright, you?"));
		chatHistory.add(new Message(true, "same here! Hey, wanted to ask you a quick question."));
		chatHistory.add(new Message(false, "sure mate, shoot!"));
		chatHistory.add(new Message(true, "should I ditch this whole networks project? It's killing me, it really is."));
		chatHistory.add(new Message(false, "hey chill. It'll be alright. You'll feel awesome once it's all done, believe me."));
		chatHistory.add(new Message(true, "you really think so?"));
		chatHistory.add(new Message(false, "oh yes! Just trust me on this!"));
		chatHistory.add(new Message(true, "thank a lot mate, that really helped"));
		chatHistory.add(new Message(false, "hey any time!"));*/

//		chatHistory.add(new Message(true, "hi"));
//		chatHistory.add(new Message(false, "oh, hi there!"));
//		chatHistory.add(new Message(true, "doing good?"));
//		chatHistory.add(new Message(false, "yeah I'm doing alright,u?"));
//		chatHistory.add(new Message(true, "same here!"));
//		chatHistory.add(new Message(false, "was just checking on you"));
//		chatHistory.add(new Message(true, "that's very sweet of you!"));
//		chatHistory.add(new Message(false, "ok. bye"));
//		chatHistory.add(new Message(true, "BYE!"));
//		chatHistory.add(new Message(true, "Was nice talking to you!!"));
//		chatHistory.add(new Message(false, "WAS NICE TALKING TO U TOO"));	// MAX LENGTH = 25!
//		
//		chatHistory.add(new Message(true, "hi"));
//		chatHistory.add(new Message(false, "oh, hi there!"));
//		chatHistory.add(new Message(true, "doing good?"));
//		chatHistory.add(new Message(false, "yeah I'm doing alright,u?"));
//		chatHistory.add(new Message(true, "same here!"));
//		chatHistory.add(new Message(false, "was just checking on you"));
//		chatHistory.add(new Message(true, "that's very sweet of you!"));
//		chatHistory.add(new Message(false, "ok. bye"));
//		chatHistory.add(new Message(true, "BYE!"));
//		chatHistory.add(new Message(true, "Was nice talking to you!!"));
//		chatHistory.add(new Message(false, "WAS NICE TALKING TO UT"));	// MAX LENGTH = 22!
//		chatHistory.add(new Message(false, "HDISKDHEIDHDISKDHEIDHDISKDHEIDHDISKDHEID")); // HDISKDHEID
//		
		// CHAT BOX PANEL

		chatBox = new JPanel();
		chatBox.setBounds(200, 20, 420, 500);
		chatBox.setBorder(BorderFactory.createEmptyBorder());
		chatBox.setOpaque(false);
		
		add(chatBox);
		
		
		// MESSAGE BOX

		messageBox = new JTextFieldLimitSpace(40);
		messageBox.setBounds(200, 500, 345, 40);
		Font f = new Font("Arial Rounded MT Bold", Font.PLAIN, 15);
		messageBox.setFont(f);
		messageBox.setText("  Type message here.");
		add(messageBox);
		
		
		// SEND BUTTON
		
		send = new JButton("Send");
		send.setBounds(560, 500, 60, 40);
		add(send);
		send.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String mess= messageBox.getText();
					client.SendMessage(nameClient, mess);
					ArrayList<Message> m=tm.get(nameClient);
					//if(m==null)
						//tm.put(, value)
					m.add(new Message(true,mess));
					hasUnreadMessage.add(nameClient);
					messageBox.setText("");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		
		// SERVER LIST BUTTON
		serverList = new JButton("Server List");
		serverList.setBounds(25, 440, 160, 50);
		add(serverList);
		serverList.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JDialog serverListDialog = new JDialog();
				serverListDialog.setSize(500,300);
				serverListDialog.setLocationRelativeTo(null);
				
				JPanel tempPanel = new JPanel();
				tempPanel.setLayout(new FlowLayout());
				tempPanel.setBounds(0, 200, 500, 100);
				
				JPanel tempPanel2 = new JPanel();
				//tempPanel2.setLayout(new FlowLayout());
				tempPanel2.setBounds(0, 0, 500, 200);
				
				JLabel p = new JLabel();
				p.setSize(500,200);
				
				JButton server1 = new JButton("Server 1");
				server1.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							String s = client.getMyServerMembers(1);
							p.setText(s);
							tempPanel2.removeAll();
							tempPanel2.add(p);
							//tempPanel.removeAll();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
					}
				});
				
				JButton server2 = new JButton("Server 2");
				server2.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							String s = client.getMyServerMembers(2);
							p.setText(s);
							tempPanel2.removeAll();
							tempPanel2.add(p);
							//tempPanel.removeAll();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
					}
				});
				
				
				JButton server3 = new JButton("Server 3");
				server3.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							String s = client.getMyServerMembers(3);
							p.setText(s);
							tempPanel2.removeAll();
							tempPanel2.add(p);
							//tempPanel.removeAll();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
					}
				});
				
				
				JButton server4 = new JButton("Server 4");
				server4.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							String s = client.getMyServerMembers(4);
							p.setText(s);
							tempPanel2.removeAll();
							tempPanel2.add(p);
							//tempPanel.removeAll();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
					}
				});
				
				
				tempPanel.add(server1);
				tempPanel.add(server2);
				tempPanel.add(server3);
				tempPanel.add(server4);
		
				serverListDialog.add(tempPanel);
				serverListDialog.add(tempPanel2);
				//System.out.println("b1");//debug
				serverListDialog.setVisible(true);
			}
		});
		
		
		// LOGOUT BUTTON
		logout = new JButton("Log Out");
		logout.setBounds(25, 490, 160, 50);
		add(logout);
		logout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					client.quit();
					testFrame.dispose();
					MainFrame mainFrame = new MainFrame();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		
		
		// MEMBER LIST PANEL
		memberListPanel = new JPanel();
		memberListPanel.setBounds(25, 28, 160, 395);
		memberListPanel.setLayout(new BorderLayout());
		add(memberListPanel);
		refreshMembers();
		
	}
	
	
	// OTHER METHODS
	public void removed(String name) {
		history.remove(name);
		System.out.println("before refresh"); //debug
		refreshMembers();
	}
	
	public void refreshMembers() {
		//System.out.println("called"); //debug
		System.out.println("inside refresh"); //debug
		int sizeList = tm.size();
		JPanel tempPanel = new JPanel();
		tempPanel.setLayout(new GridLayout(sizeList, 1));
		tempPanel.setSize(160, 375);
		
		for (String name: tm.keySet())
		{
			if(name.equals("@1")) //debug
				System.out.println("how"); //debug
			JButton button = new JButton();
//			if(name.equals(me))
//				button.setText("me");
//			else 
				button.setText(name); 
			if(hasUnreadMessage.contains(name))
				button.setForeground(Color.RED);
			tempPanel.add(button);
			if(!history.containsKey(name))
				history.put(name, new ChatBoxPanel(tm.get(name)));
			
			button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					nameClient = name;
					ChatBoxPanel chatHistory = history.get(name);
					if(hasUnreadMessage.contains(name)){
						history.remove(name);
						chatHistory=new ChatBoxPanel(tm.get(name));
						history.put(name, chatHistory);
						hasUnreadMessage.remove(name);
						refreshMembers();
					}
						
						
					chatBox.removeAll();
					chatBox.add(chatHistory);
					revalidate();
					repaint();
				}
			});
		}
		
		//remove(memberListPanel);
		memberListPanel.removeAll();
		JScrollPane scrollPane = new JScrollPane(tempPanel);
		memberListPanel.add(scrollPane, BorderLayout.CENTER);
		add(memberListPanel);
		memberListPanel.revalidate();
		memberListPanel.repaint();
		
	}
	
	
	protected void paintComponent(Graphics G){
		super.paintComponent(G);
		Image image = new ImageIcon("bg3.png").getImage();
		
		G.drawImage(image, 0, 0, this);
	}
	

}