package com.chat.challenge.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chat.challenge.model.ChatMessage;
import com.chat.challenge.repository.ChatMessageRepository;

@Service
public class ChatMessageService {
    @Autowired
    private ChatMessageRepository chatMessageRepository;

    public ChatMessage save(ChatMessage chatMessage) {
    	return chatMessageRepository.save(chatMessage);
    }
    
    public List<ChatMessage> findAllChatMessages() {
    	return chatMessageRepository.findAll();
    }
}