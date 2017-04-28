package view;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.swing.JFrame;

import model.Client;
import model.Message;

@SuppressWarnings("serial")
public class MainFrame extends JFrame{
	StartWindowPanel startWindow;
	TreeMap<String,ArrayList<Message>> tm;
	TreeSet<String> hasUnreadMessage;
	Client client;
	
	public MainFrame() throws Exception{
		
		setLocationRelativeTo(null);	// place it in the middle of the screen
		setSize(getMaximumSize());
		setSize(650,600);
		setResizable(false);			// because of the null layout
		
		tm= new TreeMap<String,ArrayList<Message>>(); 
		
		hasUnreadMessage= new TreeSet<String>();
		client = new Client(tm,this,hasUnreadMessage);
		WindowDestroyer myListener = new WindowDestroyer(client);
		addWindowListener(myListener);
		
		startWindow = new StartWindowPanel(client,this, tm,hasUnreadMessage);
		add(startWindow);
		startWindow.setVisible(true);
		startWindow.revalidate();
		startWindow.repaint();
		
		revalidate();
		repaint();
		setVisible(true);
	}
	
	public void removed(String name){
		startWindow.chatFrame.finalPanel.removed(name);
	}
	
	public void refreshMembers() {
		//System.out.println("startwindow: " + startWindow== null);
		//System.out.println("chatFrame: " + startWindow.chatFrame); 
	//	System.out.println("finalPanel "+ startWindow.chatFrame.finalPanel);
		if(startWindow.chatFrame==null)
			return;
		startWindow.chatFrame.finalPanel.refreshMembers();
	}
	
	public static void main(String[] args) throws Exception {
	
		new MainFrame();
	}
}
