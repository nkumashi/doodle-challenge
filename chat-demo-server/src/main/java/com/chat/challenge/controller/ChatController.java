package com.chat.challenge.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.chat.challenge.model.ChatMessage;
import com.chat.challenge.model.ChatNotification;
import com.chat.challenge.service.ChatMessageService;
import com.chat.challenge.service.ChatRoomService;

@Controller
public class ChatController {
	@Autowired
	private ChatMessageService chatMessageService;
    @Autowired
    private ChatRoomService chatRoomService;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    
    @MessageMapping("/chat.sendMessage")
    public void sendMessage(@Payload String chatMessage) {    	
        messagingTemplate.convertAndSend("/topic/public",
              new SimpleDateFormat("HH:mm:ss").format(new Date()) + chatMessage);
    }
    
    //@SendTo("/topic/public")
//    public void sendMessage(@Payload ChatMessage chatMessage) {
//    	Optional<String> chatId = chatRoomService
//                .getChatId(chatMessage.getSenderId(), chatMessage.getRecipientId(), true);
//        chatMessage.setChatId(chatId.get());
//        ChatMessage saved = chatMessageService.save(chatMessage);
//        messagingTemplate.convertAndSend("/topic/public",
//              new ChatNotification(
//              saved.getId(),
//              saved.getSenderId(),
//              saved.getSenderName()));
//        // return chatMessage;
//    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(@Payload ChatMessage chatMessage, 
                               SimpMessageHeaderAccessor headerAccessor) {
        // Add username in web socket session
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSenderName());
        return chatMessage;
    }

}
