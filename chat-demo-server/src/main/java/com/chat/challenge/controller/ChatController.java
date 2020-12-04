package com.chat.challenge.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chat.challenge.dto.ChatMessageDTO;
import com.chat.challenge.dto.ChatUserDTO;
import com.chat.challenge.model.ChatMessage;
import com.chat.challenge.model.ChatUser;
import com.chat.challenge.service.ChatMessageService;
import com.chat.challenge.service.ChatUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api")
@Api(value = "Chat")
public class ChatController {	
	private static final String INITIAL_PAGE = "0";
	private static final String MAX_RECORDS_PER_PAGE = "10";
	
	@Autowired
	private ChatUserService chatUserService;
	@Autowired
	private ChatMessageService chatMessageService;

	@GetMapping("/home")
	public String home() {		
	 	return "Hello from Server!";
	}
	
	@ApiOperation(value = "Create a new user")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "New user record created.", response = ChatUserDTO.class),
            @ApiResponse(code = 202, message = "User already exists but request was accepted.", response = ChatUserDTO.class),
            @ApiResponse(code = 500, message = "Internal server error.")
    })
	@PostMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
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
	
	@ApiOperation(value = "Retrieve paged chat messages")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "No records present.", response = ChatMessageDTO.class),
            @ApiResponse(code = 200, message = "Found records matching criteria.", response = ChatMessageDTO.class),            
    })
	@GetMapping(value = "/pagedChatMessages", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ChatMessageDTO>> getPagedChatMessages(@RequestParam(defaultValue = INITIAL_PAGE) int page,
		      @RequestParam(defaultValue = MAX_RECORDS_PER_PAGE) int size) {
		Pageable paging = PageRequest.of(page, size, Sort.by("createdAt").descending());
		List<ChatMessage> chatMessages = new ArrayList<ChatMessage>();
		Page<ChatMessage> pages = chatMessageService.findAllChatMessages(paging);
		chatMessages = pages.getContent();
		if (chatMessages.isEmpty()) {
	        return new ResponseEntity<>(new ArrayList<ChatMessageDTO>(), HttpStatus.NO_CONTENT);
	    }
					
		List<ChatMessageDTO> chatMessageDTOs = chatMessages.stream()
		          .map(this::convertChatMessageToDto)
		          .collect(Collectors.toList());
		return new ResponseEntity<>(chatMessageDTOs, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Retrieve latest chat messages")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "No records present.", response = ChatMessageDTO.class),
            @ApiResponse(code = 202, message = "Found records matching criteria.", response = ChatMessageDTO.class),            
    })
	@GetMapping("/latestChatMessages")
	public ResponseEntity<List<ChatMessageDTO>> getLatestChatMessages(@RequestParam(defaultValue = MAX_RECORDS_PER_PAGE) int limit) {
		List<ChatMessage> chatMessages = chatMessageService.findNextNRecords(limit);
		if (chatMessages.isEmpty()) {
	        return new ResponseEntity<>(new ArrayList<ChatMessageDTO>(), HttpStatus.NO_CONTENT);
	    }
					
		List<ChatMessageDTO> chatMessageDTOs = chatMessages.stream()
		          .map(this::convertChatMessageToDto)
		          .collect(Collectors.toList());
		return new ResponseEntity<>(chatMessageDTOs, HttpStatus.ACCEPTED);
	}
	
	private ChatMessageDTO convertChatMessageToDto(ChatMessage message) {
		//ModelMapper modelMapper = new ModelMapper();			    
		//ChatMessageDTO dto = modelMapper.map(message, ChatMessageDTO.class);
		
		ChatMessageDTO dto = new ChatMessageDTO();
		dto.setContent(message.getContent());
		dto.setSentByUser(convertChatUserToDto(message.getSentByUser()));
		
		LocalDateTime now = message.getCreatedAt(); 
		String dateString = now.getDayOfMonth() + " " + now.getMonth() + " " + now.getYear() + " " + getTime(now);
		dto.setCreatedAt(dateString);	
		
	    return dto;
	}
	
	private ChatUserDTO convertChatUserToDto(ChatUser user) {
		ModelMapper modelMapper = new ModelMapper();
		ChatUserDTO dto = modelMapper.map(user, ChatUserDTO.class);	    
	    return dto;
	}
	
	private String getTime(LocalDateTime now) {
    	int hour = now.getHour();
    	int minute = now.getMinute();
    	
    	return (hour < 10 ? "0" + hour : "" + hour) + ":" + (minute < 10 ? "0" + minute : "" + minute);
    }
}

