package model;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.TreeMap;
import java.util.TreeSet;

public class Server_Thread implements Runnable {
	Socket before;
	Socket after;
	TreeSet<Pair> ts;
	BufferedReader received;
	DataOutputStream forward;
	TreeMap<String,Connection>clients;
	Sender sender;
	int server_num;
	
	
	
	

	public Server_Thread(int server_num,Socket before, Socket after, Sender sender,TreeMap<String,Connection>clients,TreeSet<Pair> ts) throws IOException {
		this.before = before;
		this.after=after;
		this.server_num= server_num;
		this.ts = ts;
		this.clients=clients;
		this.sender=sender;
		received = new BufferedReader(new InputStreamReader(before.getInputStream()));
		forward = new DataOutputStream(after.getOutputStream());
		

	}

	public void run() {
		while (true) {
			String x;
			try {
				x = received.readLine();
				if (x.startsWith("joined")) {
					//System.out.println("server thread");
					int num= Integer.parseInt(received.readLine());
					String name = received.readLine();
					int ttl= Integer.parseInt(received.readLine())-1;
						
					ts.add(new Pair(name,num));
					sender.sendToclients("joined", name);
					if(ttl==0)
						continue;
					forward.writeBytes("joined\n"+num+"\n"+name+"\n"+ttl+"\n");
					//forward.writeBytes(name+"\n"+ttl+"\n");
					
					//System.out.println(ts.toString());
				}
				else if(x.startsWith("removed")){
					String name = received.readLine();
					int ttl= Integer.parseInt(received.readLine())-1;
					ts.remove(new Pair(name,0));
					sender.sendToclients("removed", name);
					
					//System.out.println("send to clients removed"); // debug
					
					if(ttl!=0){
						forward.writeBytes("removed\n"+name+"\n"+ttl+"\n");
						//forward.writeBytes(name+"\n"+ttl+"\n");
					}
				}
				
				else if(x.startsWith("Chat"))
				{
					
					String src= received.readLine();
					String dest= received.readLine();
					String mes= received.readLine();
					int ttl= Integer.parseInt(received.readLine())-1;
					if(clients.containsKey(dest)){
						Connection c= clients.get(dest);
						c.sendMessage("From: "+src+"\n"+mes+"\n");
					}
					else{
						if(ttl != 0){
							forward.writeBytes("Chat\n"+src+"\n" +dest+"\n"+mes+"\n"+ttl+"\n");
						}
							
					}
					
				}
				
				
				
			} catch (IOException e) {
				e.printStackTrace();
			}// outToServer.writeBytes("joined\n");

		}
	}
	
	void sendMessage(String s) throws IOException{
		forward.writeBytes(s);
	}
}
