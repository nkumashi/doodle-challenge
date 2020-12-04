package com.chat.challenge.repository;

import com.chat.challenge.model.ChatMessage;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {
	public Page<ChatMessage> findAll(Pageable page);
	public List<ChatMessage> findFirst10ByOrderByCreatedAtDesc();
}
