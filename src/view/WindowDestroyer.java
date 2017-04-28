package view;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import model.Client;

public class WindowDestroyer extends WindowAdapter {
	Client client;
	
	public WindowDestroyer(Client client){
		this.client=client;
	}
	public void windowClosing(WindowEvent e) {
		try {
			
			client.quit();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.exit(0);
	}

}
