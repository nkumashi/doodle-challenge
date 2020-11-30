package com.chat.challenge.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
	private String id;
	private String chatId;
	private String senderId;
	private String recipientId;
	private String senderName;
	private String recipientName;
	private String content;
	private Date timestamp;
	private MessageType type;
	private MessageStatus status;

	public enum MessageStatus {
	    RECEIVED, DELIVERED
	}
	
	public enum MessageType {
		CHAT,
		JOIN,
		LEAVE
	}
}
