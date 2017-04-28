package model;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.TreeMap;
import java.util.TreeSet;

public class Connection implements Runnable {
	Socket socket;
	String name;
	TreeMap<String, Connection> clients;
	TreeSet<Pair> ts;
	BufferedReader inFromClient;
	DataOutputStream outToClient;
	//DataOutputStream outToServer;
	Sender sender;
	Server_Thread thread;
	int server_num;
	//Queue<String> queue= new LinkedList<String>();

	public Connection(int server_num,Socket socket,Server_Thread thread,Sender sender, TreeMap<String, Connection> cl,TreeSet<Pair> ts) throws IOException 
	{
		this.server_num=server_num;
		this.socket = socket;
		this.clients = cl;
		this.ts=ts;
		this.sender=sender;
		this.thread=thread;
		inFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		outToClient = new DataOutputStream(socket.getOutputStream());
		
		//outToServer= new DataOutputStream(server2.getOutputStream());
	}

	public String getMemberList() 
	{
		String out = ts.size()+"\n";
		//System.out.println("sent num"); //debug
		
		for (Pair cl : ts) 
			out += cl.name + "\n";
		
		return out;
	}
	
	public String getMemberList(int s)
	{
		
		
		String client="";
		int count=0;
		
		for (Pair cl : ts){ 
			if(cl.server==s){
				client += cl.name + "\n";
				count++;
			}
		}
		
		String out= count+"\n";
		return out+client;
		
	}
	
//	public boolean canJoin(String nm) throws IOException{
//		
//		outToServer.writeBytes("canJoin\n");
//		outToServer.writeBytes(nm+'\n');
//		while(queue.isEmpty());
//		String fromServer= queue.remove();
//		if(fromServer.equals("Name already exists\n"))
//			return false;
//		else return true;
//		
//		
//	}
	public void joined() throws IOException{
		ts.add(new Pair(name,server_num));
		clients.put(name, this);
		thread.sendMessage("joined\n"+server_num+"\n"+name+"\n"+3+"\n");
		//outToServer.writeBytes("joined\n"+server_num+"\n");
		//outToServer.writeBytes(name+"\n"+3+"\n");
		sender.sendToclients("joined", name);
	}
	
	

	public boolean join() throws IOException{
		while (true) 
		{ 
			name = "";
			try 
			{
				//System.out.println("l1"); //debug
				name = inFromClient.readLine();
				//System.out.println("l1"); //debug
				if(name==null)
					return false;
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
			if (ts.contains(new Pair(name,0))) 
			{
				try 
				{
					outToClient.writeBytes("Name already exists\n");
				} 
				catch (IOException e) 
				{
					e.printStackTrace();
				}
			} 
			else 
			{
				
				
				try 
				{
					//System.out.println("l3");//debug
					outToClient.writeBytes("Registered Successfully\n");
					//System.out.println("l4"); //debug

				} 
				catch (IOException e) 
				{

					e.printStackTrace();
				}
				break;
			}
		}
		joined();
		return true;
	}
	
	public void quit() throws IOException{
		//System.out.println("quit method in server1");
		clients.remove(name);
		ts.remove(new Pair(name,0));
		sender.sendToclients("removed", name);
		thread.sendMessage("removed\n"+name+'\n'+"3\n");
		//outToServer.writeBytes("removed\n");
		//outToServer.writeBytes(name+'\n'+"3\n");
		//System.out.println("quit method in server"); //debug
	}
	
	
	public void run() 
	{
		//System.out.println("h-1"); //debug
		try {
			if(!join()){

				return;
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//System.out.println("fieh eih"); //debug

		while (true) 
		{
		
			String clientSentence;
			try 
			{

				clientSentence = inFromClient.readLine();
				//for(int i=0; i<50000000; i++)
				//System.out.println(clientSentence); //debug
				if (clientSentence == null) 
				{
					quit();
					break;
				}
				
				if (clientSentence.equals("member")) 
				{
					outToClient.writeBytes(getMemberList());
				} else 
					if(clientSentence.startsWith("YourMember")){
						Integer n= Integer.parseInt(inFromClient.readLine());
						//System.out.println("balabizo1"); //debug
						outToClient.writeBytes(getMemberList(n));
					}
					else
				{
					String src = clientSentence;
					String dest = inFromClient.readLine();
					
						if (ts.contains(new Pair(dest,0))) 
						{
							outToClient.writeBytes("eshta\n");
							String msg = inFromClient.readLine();
							
							if(clients.containsKey(dest)){
								Connection c = clients.get(dest);
								c.sendMessage("From: " + src + "\n" + msg + '\n');
							}
							else {
								thread.sendMessage("Chat\n"+src+"\n"+dest+'\n'+msg+'\n'+"3\n");
//								outToServer.writeBytes("Chat\n");
//								outToServer.writeBytes(src+"\n");
//								outToServer.writeBytes(dest+'\n');
//								outToServer.writeBytes(msg+'\n'+"3\n");
								
							}
						} 
						else 
						{
							outToClient.writeBytes("Invalid Client\n");
							continue;
						}
					}

				

			} 
			catch (IOException e) 
			{
				try {
					quit();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				//e.printStackTrace();
				return;
			}
			
		}

	}

	public void sendMessage(String msg) throws IOException 
	{
		outToClient.writeBytes(msg);
	}

}
