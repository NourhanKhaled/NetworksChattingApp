package model;

public class Pair implements Comparable<Pair>{
	String name;
	int server;
	public Pair(String name, int ser){
		this.name=name;
		server=ser;
	}
	@Override
	public int compareTo(Pair o) {
		return name.compareTo(o.name);
	}
	
	

}
