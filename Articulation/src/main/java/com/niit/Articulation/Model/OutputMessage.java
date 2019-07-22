package com.niit.Articulation.Model;

import java.util.Date;


public class OutputMessage extends Message {
	
	private Date time;
	
	public OutputMessage(Message original, Date time) {
		super(original.getMessage(), original.getId(),original.getUserId());
		this.time = time;
	}


	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

}