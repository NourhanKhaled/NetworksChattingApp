package model;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.TreeMap;
import java.util.TreeSet;

class Server {
	
	TreeMap<String,Connection> tm;
	ServerSocket welcomeSocket;
	Socket server2;
	Socket server4;
	TreeSet<Pair> ts;
	Server_Thread ServerThread;
	Thread server_Thread;
	Sender sender;
	int num;
	
	public Server() throws IOException{
		
		welcomeSocket = new ServerSocket(6000);
		tm= new TreeMap<String,Connection>();
		server2= welcomeSocket.accept();//server2 is connecting to me
		ts= new TreeSet<Pair>();
		sender = new Sender(tm);
		num=1;
		server4= welcomeSocket.accept();
		ServerThread= new Server_Thread(num,server4,server2,sender,tm,ts);
		server_Thread= new Thread(ServerThread);
		server_Thread.start();

		
		running();
	}
	
	public void running() throws IOException{
		while(true) 
		{
			Socket connectionSocket = welcomeSocket.accept();
			Connection connection = new Connection(num,connectionSocket,ServerThread,sender,tm,ts);
			Thread thread = new Thread(connection);
			thread.start();
			
			
		}
		
	}
	
	
	public static void main(String args[]) throws Exception { 
		Server s= new Server();
	}
	
}
