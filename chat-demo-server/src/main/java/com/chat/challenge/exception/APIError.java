package com.chat.challenge.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/*
JSON structure of the error response sent to clients:
{
 “status”: “BAD_REQUEST”,
 “timestamp”: “29–03–2019 02:52:52”,
 “message”: “No Content Found”,
 “debugMessage”: “No Content Found”,
 “stackTrace”: “Error Stack Trace”
}
 */

@Data
class APIError {	
	public static final String DATE_FORMAT = "dd-MM-yyyy hh:mm:ss";
	
	private HttpStatus status;
	private String message;
	private String debugMessage;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
	private LocalDateTime timestamp;

	private APIError() {
		timestamp = LocalDateTime.now();
	}

	public APIError(HttpStatus status) {
		this();
		this.status = status;
	}

	public APIError(HttpStatus status, Throwable ex){
		this();
		this.status = status;
		this.message = "Unexpected error";
		this.debugMessage = ex.getLocalizedMessage();
	}

	public APIError(HttpStatus status, String message, Throwable ex) {
		this();
		this.status = status;
		this.message = message;
		this.debugMessage = ex.getLocalizedMessage();
	}
}
