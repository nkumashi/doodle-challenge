package com.chat.challenge.repository;

import com.chat.challenge.model.ChatMessage;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {
	public List<ChatMessage> findAll();
}
