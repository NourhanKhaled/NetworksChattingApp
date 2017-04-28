package model;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.LinkedBlockingQueue;

import view.MainFrame;

public class Client	{ 
	String outgoingMessage;
	String incomingMessage;
	String name;
	Socket socket;
	LinkedBlockingQueue<String> queue;
	DataOutputStream outBytes;
	TreeMap<String,ArrayList<Message>> conv;
	TreeSet<String> unread;
	MainFrame frame;
	public boolean isValid(String txt) throws IOException{
//		outBytes.writeBytes(outgoingMessage+'\n');
//		while(queue.isEmpty()){
//			System.out.print("");
//		};
//		String incomingMessage = queue.remove();
//		//incoming Message indicate validity
//		System.out.println("");
//		System.out.println(incomingMessage);
//		
//		if(!incomingMessage.equals("Name already exists")){
//			
//		}  break;
//			
//	}
		
			//System.out.println("Enter your username: ");
			//outgoingMessage = in.readLine();
			//chat.getFn();
		
			//System.out.println("h1"); //debug
			outBytes.writeBytes(txt + '\n');
			while(queue.isEmpty()) {
				System.out.print("");
			};
			incomingMessage = queue.remove();
			//System.out.println("h2"); //debug
			
			
			if(incomingMessage.equals("Name already exists"))
				return false;
			else
			{
				name = txt;
				//System.out.println("h3"); //debug
				putMembers();
				return true;
			}
	
	}
	

	public String getMyServerMembers(int num) throws IOException{
	
		//System.out.println("c1"); //debug
		outBytes.writeBytes("YourMember\n"+num+"\n");
		
		String result= "";
		//System.out.println("c21");
		while(queue.isEmpty()){
			System.out.print("");
		};
		
		//System.out.println("c2"); //debug
		
		incomingMessage = queue.remove();
		int x = Integer.parseInt(incomingMessage.toString());
		while(x-->0)
		{
			while(queue.isEmpty()){
				System.out.print("");
			};
			incomingMessage = queue.remove();
			if(x!=0)
				result=result+ incomingMessage+" | ";
			else 
				result=result+ incomingMessage;
		}
		
		//System.out.println("c3"); //debug
		
		return result;
	}
	
	public void putMembers() throws IOException{
		outBytes.writeBytes("member\n");
//		incomingMessage = queue.remove();
//		System.out.println(incomingMessage);
		while(queue.isEmpty()){
			System.out.print("");
		};
		incomingMessage = queue.remove();
		
		//System.out.println(incomingMessage);
		
		int x = Integer.parseInt(incomingMessage.toString());
		while(x-->0)
		{
			while(queue.isEmpty()){
				System.out.print("");
			};
			System.out.println(incomingMessage+" "+x);
			incomingMessage = queue.remove();
			conv.put(incomingMessage, new ArrayList<Message>());
		}
	}
	
	public void SendMessage(String to, String message) throws IOException{
		
		outBytes.writeBytes(name+'\n');
		
		outBytes.writeBytes(to+'\n');
		while(queue.isEmpty()){
			System.out.print("");
		};
		incomingMessage = queue.remove();					
		
		if(incomingMessage.equals("Invalid Client"))
			return;
	
		outBytes.writeBytes(message+'\n');
	}
	
	public void quit() throws IOException{
		socket.close();
		
	}
	
	public Client(TreeMap<String,ArrayList<Message>> conv, MainFrame frame, TreeSet<String> unread)throws Exception 
	{
		this.frame=frame;
		this.conv=conv;
		this.unread=unread;
		queue = new  LinkedBlockingQueue<String>();
		String incomingMessage;
		String name;
		String[] hostnames= ServerNames.serverName;
		int idx=(int)(Math.random()*4);
		int proxy[]=new int [4];
		proxy[0]=6000;
		proxy[1]=6100;
		proxy[2]=6200;
		proxy[3]=6300;
		
		//int rand= (int)(Math.random()*4);
		System.out.println(idx);
		socket = new Socket(hostnames[2],proxy[2]);
		//unread= new TreeSet<String>();
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		outBytes = new DataOutputStream(socket.getOutputStream());
		Client_Thread client_thread = new Client_Thread(queue,socket,conv,frame,unread);
		Thread thread = new Thread(client_thread);
		thread.start();
		
		
		
//		while(true) 
//		{ 
//			System.out.println("Enter your username: ");
//			outgoingMessage = in.readLine();
//			outBytes.writeBytes(outgoingMessage+'\n');
//			while(queue.isEmpty()){
//				System.out.print("");
//			};
//			incomingMessage = queue.remove();
//			
//			System.out.println("");
//			System.out.println(incomingMessage);
//			
//			if(!incomingMessage.equals("Name already exists"))  break;
//				
//		}

		//name = getName();
//		while(true)
//		{
//			System.out.println("Type 'M' to view online members or 'C' to initiate chat or 'BYE' to quit");
//			outgoingMessage = in.readLine().trim();
//			
//			if(outgoingMessage.equals("M"))
//			{
//				outBytes.writeBytes("member\n");
//				while(queue.isEmpty()){
//					System.out.print("");
//				};
//				
//				incomingMessage = queue.remove();
//				int x = Integer.parseInt(incomingMessage.toString());
//				while(x-->0)
//				{
//					while(queue.isEmpty()){
//						System.out.print("");
//					};
//					incomingMessage = queue.remove();
//					System.out.println(incomingMessage);
//				}
//			}
//			else if(outgoingMessage.equalsIgnoreCase("C"))
//			{	outBytes.writeBytes(name+'\n');
//				
//					System.out.println("Enter recipient's name");
//					outBytes.writeBytes(in.readLine()+'\n');//need to check for validity
//					while(queue.isEmpty()){
//						System.out.print("");
//					};
//					incomingMessage = queue.remove();					
//					
//					if(incomingMessage.equals("Invalid Client"))
//					{
//						System.out.println("Member does not exist");
//						continue;
//					}
//					
//				
//				System.out.println("Please enter your message in a single line");
//				outBytes.writeBytes(in.readLine()+'\n');
//			}
//			else
//			{
//				if(outgoingMessage.equalsIgnoreCase("BYE") || outgoingMessage.equalsIgnoreCase("QUIT"))
//				{
//					socket.close();
//					break;
//				}
//				else
//				System.out.println("Invalid Input -_-");
//			}
//			
//		}
	}
	
/*	public void setListener(ChatGUI chat){
		this.chat=chat;
	}*/
	
	
}