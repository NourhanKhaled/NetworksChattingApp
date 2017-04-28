package model;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.TreeMap;
import java.util.TreeSet;

class Server3 {
	
	TreeMap<String,Connection> tm;
	ServerSocket welcomeSocket;
	Socket server2;
	TreeSet<Pair> ts;
	Server_Thread ServerThread;
	Thread server_Thread;
	Sender sender;
	Socket server4;
	int num;
	public Server3() throws IOException{
		
		welcomeSocket = new ServerSocket(6200);
		tm= new TreeMap<String,Connection>();
		server2= new Socket(ServerNames.serverName[1],6100);
		server4= welcomeSocket.accept();
		ts= new TreeSet<Pair>();
		sender = new Sender(tm);
		num=3;
		ServerThread= new Server_Thread(num,server2,server4,sender,tm,ts);
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
		Server3 s= new Server3();
		
	}
	
}
