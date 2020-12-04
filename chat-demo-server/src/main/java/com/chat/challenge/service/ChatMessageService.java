package com.chat.challenge.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.chat.challenge.model.ChatMessage;
import com.chat.challenge.repository.ChatMessageRepository;

@Service
public class ChatMessageService {
	@Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private ChatMessageRepository chatMessageRepository;

    public ChatMessage save(ChatMessage chatMessage) {
    	return chatMessageRepository.save(chatMessage);
    }
    
    public Page<ChatMessage> findAllChatMessages(Pageable page) {
    	return chatMessageRepository.findAll(page);
    }
    
    public List<ChatMessage> findFirst10ByOrderByCreatedAtDesc() {
    	return chatMessageRepository.findFirst10ByOrderByCreatedAtDesc();
    }
    
    public List<ChatMessage> findNextNRecords(int limit) {
    	Query query = new Query();
        query.limit(limit);
        query.with(Sort.by(Sort.Direction.ASC, "createdAt"));
        return mongoTemplate.find(query, ChatMessage.class);
    }
    
    public void deleteAllRecords() {
    	chatMessageRepository.deleteAll();
    }
}