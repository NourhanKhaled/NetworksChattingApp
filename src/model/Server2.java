package model;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.TreeMap;
import java.util.TreeSet;

class Server2 {
	
	TreeMap<String,Connection> tm;
	ServerSocket welcomeSocket;
	Socket server1;
	TreeSet<Pair> ts;
	Server_Thread ServerThread;
	Thread server_Thread;
	Sender sender;
	Socket server3;
	int num;
	public Server2() throws IOException{
		
		welcomeSocket = new ServerSocket(6100);
		tm= new TreeMap<String,Connection>();
		server1= new Socket(ServerNames.serverName[0],6000);
		ts= new TreeSet<Pair>();
		sender = new Sender(tm);
		num=2;
		server3= welcomeSocket.accept();
		ServerThread= new Server_Thread(num,server1,server3,sender,tm,ts);
		server_Thread= new Thread(ServerThread);
		server_Thread.start();
		
		running();
		
	}
	
	public void running() throws IOException
	{
		while(true) 
		{
			Socket connectionSocket = welcomeSocket.accept();
			Connection connection = new Connection(num,connectionSocket,ServerThread,sender,tm,ts);
			Thread thread = new Thread(connection);
			thread.start();
			
			
		}
		
	}
	
	
	
	public static void main(String args[]) throws Exception { 
		Server2 s= new Server2();
		
	}
	
}
