package model;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.TreeMap;
import java.util.TreeSet;

class Server4 {
	
	TreeMap<String,Connection> tm;
	ServerSocket welcomeSocket;
	Socket server3;
	TreeSet<Pair> ts;
	Server_Thread ServerThread;
	Thread server_Thread;
	Sender sender;
	Socket server1;
	int num;
	public Server4() throws IOException{
		
		welcomeSocket = new ServerSocket(6300);
		tm= new TreeMap<String,Connection>();
		server1= new Socket(ServerNames.serverName[0],6000);
		server3= new Socket(ServerNames.serverName[2],6200);
		ts= new TreeSet<Pair>();
		sender = new Sender(tm);
		num=4;
		ServerThread= new Server_Thread(num,server3,server1,sender,tm,ts);
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
		Server4 s= new Server4();
		
	}
	
}
