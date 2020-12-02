package com.chat.challenge.controller;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.chat.challenge.dto.ChatMessageDTO;
import com.chat.challenge.model.ChatMessage;
import com.chat.challenge.model.ChatRoom;
import com.chat.challenge.service.ChatMessageService;
import com.chat.challenge.service.ChatRoomService;
import com.chat.challenge.service.ChatUserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class WSChatController {
	private static final String GLOBAL_ROOM_NAME = "Global";
	private static final String GLOBAL_ROOM_TOPIC = "Common";
	
    @Autowired
    private ChatRoomService chatRoomService;
    @Autowired
	private ChatUserService chatUserService;
    @Autowired
    private ChatMessageService chatMessageService;
    
    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessageDTO sendMessage(@Payload ChatMessageDTO chatMessage) {   
    	log.info("Received chat message: " + chatMessage);
    	
    	// get room, for simplicity we are maintaining only one chat room with a single topic
    	ChatRoom globalRoom = null;
    	Optional<ChatRoom> optionalRoom = chatRoomService.findByRoomName(GLOBAL_ROOM_NAME);
		if(optionalRoom.isPresent()) {
			// room already exists
			globalRoom = optionalRoom.get();
		} else {
			// creating the global room
			globalRoom = chatRoomService.save(new ChatRoom(GLOBAL_ROOM_NAME, GLOBAL_ROOM_TOPIC));
		}
		log.info("Created global room: " + globalRoom);
		
		// let's save this chat message
		ChatMessage message = new ChatMessage();		
		message.setContent(chatMessage.getContent());		
		message.setChatRoom(globalRoom);
		message.setSentByUser(chatUserService.findByUserId(chatMessage.getSentByUser().getUserId()).get());
		message = chatMessageService.save(message);	
		
		LocalDateTime localDate = LocalDateTime.now();	
		String dateString = localDate.getDayOfMonth() + " " + localDate.getMonth() + " " +
				localDate.getYear() + " " + localDate.getHour() + ":" + localDate.getMinute();
		chatMessage.setCreatedAt(dateString);
		
		log.info("Chat message saved: " + message);						
		
        return chatMessage;
    }
}
