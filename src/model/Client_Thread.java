package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.LinkedBlockingQueue;

import view.MainFrame;


public class Client_Thread implements Runnable{
	
	
	LinkedBlockingQueue<String> queue;
	Socket socket;
	BufferedReader received;
	TreeMap<String, ArrayList<Message>> conv;
	MainFrame frame;
	//StringBuilder emp= new StringBuilder("");
	TreeSet<String> unread;
	public  Client_Thread( LinkedBlockingQueue<String>queue, Socket socket, TreeMap<String, ArrayList<Message>> conv, MainFrame frame,TreeSet<String> unread) throws IOException
	{
		this.unread= unread;
		this.frame=frame;
		this.conv= conv;
		this.queue=queue;
		this.socket=socket;
		received = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}


	public void run() 
	{
	
		while(true)
		{
			try 
			{
				String x=received.readLine();
				//System.out.println(x); //debug
				if(x.startsWith("From:"))
				{
					//System.out.println(x);
					//System.out.println(received.readLine());
					conv.get(x.substring(6)).add(new Message(false,received.readLine()));
					unread.add(x.substring(6));
					frame.refreshMembers();
			    }
				else if(x.startsWith("joined"))
				{
					//System.out.println("what");
					//int s= Integer.parseInt(received.readLine());
					String y= received.readLine();
					//System.out.println(y);
					conv.put(y, new ArrayList<Message>());
					//System.out.println("what2");
					//System.out.println(frame==null);
					frame.refreshMembers();
				}
				
				else if(x.startsWith("removed")) {
					//System.out.println("received remove"); //debug
					String s = received.readLine();
					conv.remove(s);
					frame.removed(s);
				}
				else	
				{
					//System.out.println("sent to client " + x);
					queue.add(x);					
				}
			} 
			catch (IOException e) 
			{
				break;
			}
		}
		
	}
	
	

}
