package com.chat.challenge.service;

import com.chat.challenge.model.ChatRoom;
import com.chat.challenge.repository.ChatRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChatRoomService {
    @Autowired
    private ChatRoomRepository chatRoomRepository;

    public List<ChatRoom> findAllRooms() {
		return chatRoomRepository.findAll();
	}
    
    public Optional<ChatRoom> findByRoomName(String roomname) {
		return chatRoomRepository.findByRoomName(roomname);
	}
    
    public ChatRoom save(ChatRoom room) {
		return chatRoomRepository.save(room);
	}

}
