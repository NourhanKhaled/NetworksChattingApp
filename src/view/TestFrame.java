package view;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.swing.JFrame;

import model.Client;
import model.Message;

@SuppressWarnings("serial")
public class TestFrame extends JFrame{
	TestPanelFinal finalPanel;
	//TreeMap<String,ArrayList<Message>> tm;
	
	public TestFrame(String me,MainFrame mainFrame,TreeMap<String,ArrayList<Message>> tm,TreeSet<String> hasUnreadMessage,Client client) throws Exception{
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);	// place it in the middle of the screen
		setSize(640,600);
		setResizable(false);			// because of the null layout
		
		
		finalPanel = new TestPanelFinal(me,tm, hasUnreadMessage, client, this, new TreeMap<String, ChatBoxPanel>());
		add(finalPanel);
		finalPanel.setVisible(true);
		finalPanel.revalidate();
		finalPanel.repaint();
		
		revalidate();
		repaint();
		setVisible(true);
	}
	
	
	
	
}
