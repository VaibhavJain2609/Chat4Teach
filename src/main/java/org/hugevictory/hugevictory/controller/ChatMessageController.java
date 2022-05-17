package org.hugevictory.hugevictory.controller;

import java.util.List;
import java.util.Optional;

import org.hugevictory.hugevictory.model.ChatMessage;
import org.hugevictory.hugevictory.repository.ChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatMessageController 
{
	@Autowired
	private ChatMessageService chatMessageService;
	
	@RequestMapping(value="/messages/{id}", method = RequestMethod.GET, produces = "application/json")
	public Optional<ChatMessage> getChatMessage(@PathVariable int id)
	{
		return chatMessageService.getChatMessage(id);
	}

	@RequestMapping(value = "/messages", method = RequestMethod.GET, produces = "application/json")
	public List<ChatMessage> getAllChatMessages(){return  chatMessageService.getAllChatMessages();}

	@RequestMapping(value="/messages/add", method=RequestMethod.POST)
	public void addChatMessage(@RequestBody ChatMessage chatMessage)
	{
		chatMessageService.addChatMessage(chatMessage);
	}
}
