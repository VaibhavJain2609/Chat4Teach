package org.hugevictory.hugevictory.repository; 

import org.hugevictory.hugevictory.model.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Integer> {
}
