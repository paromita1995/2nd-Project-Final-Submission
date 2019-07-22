package com.niit.Articulation.Controller;

import java.util.Date;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.niit.Articulation.Model.Message;
import com.niit.Articulation.Model.OutputMessage;



@Controller
public class ChatController {

	//Logger log = Logger.getLogger(ChatController.class);
	
	@MessageMapping("/chat")
	@SendTo("/topic/message")
	public OutputMessage sendMessage(Message message) {
		System.out.println("Calling the method sendMessage().");
		
		
	//	message.setUserId(userId);
		System.out.println("Message : "+message.getMessage());
		
		System.out.println("Message ID : "+message.getId());
System.out.println("Message userId: "+message.getUserId());
				
		return new OutputMessage(message, new Date(System.currentTimeMillis()));

	}
	
}

