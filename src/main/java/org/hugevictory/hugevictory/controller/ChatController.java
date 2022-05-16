package org.hugevictory.hugevictory.controller;

import java.util.HashMap;
import java.util.Map;

import org.hugevictory.hugevictory.model.ChatMessage;
import org.hugevictory.hugevictory.model.Student;
import org.hugevictory.hugevictory.repository.ChatMessageRepository;
import org.hugevictory.hugevictory.repository.ChatMessageService;
import org.hugevictory.hugevictory.repository.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class ChatController {
	
	private static boolean isChatRunning = false;
	private static final String TEACHER_USERNAME = "admin";
	private Map<String, String> studentMap = new HashMap<>();
	@Autowired
	private ChatMessageService chatMessageService;
	@Autowired
	private StudentService studentService;
	
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
		return "chat";
	}
	
	@RequestMapping("/StudentPortal")
	public String foo(@RequestParam("UUID")String StudentID) {
		
		if(StudentID != null && isChatRunning) {
			if(studentService.getStudentByUUID(StudentID) != null) {
				return "chat";
			} else {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "entity not found");
			}
		} else {
			return "redirect";
		}
	}

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
		chatMessageService.addChatMessage(chatMessage);
        return chatMessage;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(@Payload ChatMessage chatMessage, 
                               SimpMessageHeaderAccessor headerAccessor) {
        // Add username in web socket session
    	
        //headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }
    
    //depreciated
    @RequestMapping(value = "/studentOLD", params = {"id"}, method = RequestMethod.GET, produces = "text/plain")
    @ResponseBody
    public String getStudentInfo(@RequestParam("id") String id) {
    	String result = studentMap.get(id);
    	return result;
    }

}