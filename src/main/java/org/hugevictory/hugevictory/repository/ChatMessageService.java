package org.hugevictory.hugevictory.repository;

import java.util.List;
import java.util.Optional;
import org.hugevictory.hugevictory.model.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatMessageService 
{
	@Autowired
	public ChatMessageRepository chatMessageRepository;
	
	public void addChatMessage(ChatMessage chatMessage)
	{
		chatMessageRepository.save(chatMessage);
	}
	
	public Optional<ChatMessage> getChatMessage(int id)
	{
		return chatMessageRepository.findById(id);
	}

	public List<ChatMessage> getAllChatMessages() {
		return chatMessageRepository.findAll();
	}
}