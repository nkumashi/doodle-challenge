package com.chat.challenge.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chat.challenge.model.ChatUser;
import com.chat.challenge.repository.ChatUserRepository;

@Service
public class ChatUserService {
	@Autowired
	private ChatUserRepository chatUserRepository;
	
	public Optional<ChatUser> findByUsername(String username) {
		return chatUserRepository.findByUsername(username);
	}
	
	public Optional<ChatUser> findByUserId(String userId) {
		return chatUserRepository.findById(userId);
	}
	
	public void delete(ChatUser user) {
		chatUserRepository.delete(user);
	}
	
	public ChatUser save(ChatUser user) {
		return chatUserRepository.save(user);
	}
	
	public void deleteAllRecords() {
		chatUserRepository.deleteAll();
    }
}
