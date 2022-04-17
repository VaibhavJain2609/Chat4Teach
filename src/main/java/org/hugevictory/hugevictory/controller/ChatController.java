package org.hugevictory.hugevictory.controller;

import org.hugevictory.hugevictory.model.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ChatController {
	
	private static boolean isChatRunning = false;
	private static final String TEACHER_USERNAME = "teacher";
	
 	static public void startChat() {
		isChatRunning = true;
	}
	
	static public void stopChat() {
		isChatRunning = false;
	}
	
	@RequestMapping("/chat")
	public String openChat(@AuthenticationPrincipal User user ) {
		
		try {
			if(user.getUsername().equals(TEACHER_USERNAME)) {
				startChat();
			}
		} catch(Exception e) {
			System.out.println("is a null user");
		}
		
		if(isChatRunning) {
			return "chat";
		} else {
			return "redirect";
		}
	}

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        return chatMessage;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(@Payload ChatMessage chatMessage, 
                               SimpMessageHeaderAccessor headerAccessor) {
        // Add username in web socket session
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }

}