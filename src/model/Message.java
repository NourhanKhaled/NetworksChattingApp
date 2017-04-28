package model;

public class Message {
	
	boolean me;
    String message;
	
	public Message(boolean me, String message){
		this.me=me;
		this.message= message;
	}
	
	public String getMessage() {
		return message;
	}
	
	public Boolean isMe() {
		return me;
	}

}
