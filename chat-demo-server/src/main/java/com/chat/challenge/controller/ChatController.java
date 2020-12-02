package com.chat.challenge.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chat.challenge.dto.ChatMessageDTO;
import com.chat.challenge.dto.ChatUserDTO;
import com.chat.challenge.model.ChatMessage;
import com.chat.challenge.model.ChatUser;
import com.chat.challenge.service.ChatMessageService;
import com.chat.challenge.service.ChatUserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api")
public class ChatController {		
	@Autowired
	private ChatUserService chatUserService;
	@Autowired
	private ChatMessageService chatMessageService;

	@GetMapping("/home")
	public String home() {		
	 	return "Hello from Server!";
	}
	
	@PostMapping("/user")
	public ResponseEntity<ChatUserDTO> createUser(@RequestBody ChatUserDTO userDTO) {
		Optional<ChatUser> user = chatUserService.findByUsername(userDTO.getUsername());
		if(user.isPresent()) {
			log.info("User found: " + user.get());
			
			// user already exists, treating as success for simplicity
			return new ResponseEntity<>(this.convertChatUserToDto(user.get()), HttpStatus.ACCEPTED);
		} 	
		
		// create new user
		ChatUser newUser = null;
		try {
			newUser = chatUserService.save(new ChatUser(userDTO.getUsername()));
			log.info("User created: " + newUser);
			
			return new ResponseEntity<>(this.convertChatUserToDto(newUser), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}				
	}
	
	@GetMapping("/chatMessages")
	public ResponseEntity<List<ChatMessageDTO>> getAllChatMessages() {
		List<ChatMessage> chatMessages = chatMessageService.findAllChatMessages();
		List<ChatMessageDTO> chatMessageDTOs = chatMessages.stream()
		          .map(this::convertChatMessageToDto)
		          .collect(Collectors.toList());
		return new ResponseEntity<>(chatMessageDTOs, HttpStatus.ACCEPTED);
	}
	
	private ChatMessageDTO convertChatMessageToDto(ChatMessage message) {
		ModelMapper modelMapper = new ModelMapper();
		ChatMessageDTO dto = modelMapper.map(message, ChatMessageDTO.class);	    
	    return dto;
	}
	
	private ChatUserDTO convertChatUserToDto(ChatUser user) {
		ModelMapper modelMapper = new ModelMapper();
		ChatUserDTO dto = modelMapper.map(user, ChatUserDTO.class);	    
	    return dto;
	}
}
