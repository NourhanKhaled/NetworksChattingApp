package model;

import java.io.IOException;
import java.util.TreeMap;

public class Sender {
	
	TreeMap<String,Connection> tm;
	
	public Sender(TreeMap<String,Connection> tm){
		this.tm=tm;
	}
	
	public void sendToclients(String first, String name) throws IOException{
		
		//System.out.println("first:"+first +" "+name);
		for(Connection x:tm.values()){
			x.sendMessage(first+"\n");
			x.sendMessage(name+"\n");
			
			
		}
		
	}

}
