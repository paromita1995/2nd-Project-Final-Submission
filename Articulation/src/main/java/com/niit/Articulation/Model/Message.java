package com.niit.Articulation.Model;



public class Message {
	
	private String message;
	private int id;
	private String userId;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Message() {
		
	}
	public Message(String message, int id,String userId) {
		this.message = message;
		this.id = id;
		this.userId=userId;
		
	}
	

}
