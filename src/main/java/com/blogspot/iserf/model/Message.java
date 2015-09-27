package com.blogspot.iserf.model;

public class Message {

	private String type;
    private String text;
	
	public Message() {
	}
    
	public Message(String type, String text) {
		this.type = type;
		this.text = text;
	}
    
    
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}

	
	
}
