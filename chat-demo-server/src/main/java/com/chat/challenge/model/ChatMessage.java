package com.chat.challenge.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document
public class ChatMessage {
	@Id
	private String chatId;
	
	@DBRef
	private ChatRoom chatRoom;
	@DBRef
	private ChatUser sentByUser;
	
	private String content;
	private LocalDateTime createdAt;
	
//	private MessageType type;
//	private MessageStatus status;
//	public enum MessageStatus {
//	    RECEIVED,
//	    DELIVERED
//	}	
//	public enum MessageType {
//		CHAT,
//		JOIN,
//		LEAVE
//	}
	
	@Override
	public String toString() {
		ObjectMapper mapper = new ObjectMapper();

		String jsonString = "";
		try {
			mapper.enable(SerializationFeature.INDENT_OUTPUT);
			jsonString = mapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return jsonString;
	}
}
