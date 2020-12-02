package com.chat.challenge.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {
	public static final String MESSAGE_FOR_ALL_ERRORS = "Internal Server Error";
	public static final String MESSAGE_FOR_ALL_EXCEPTIONS = "Internal Server Error";

	// All compile time exceptions
	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<Object> handleAllExceptions(Exception e) {
		APIError apiError = createError(MESSAGE_FOR_ALL_ERRORS, HttpStatus.INTERNAL_SERVER_ERROR, e);
		e.printStackTrace(System.out);
		return new ResponseEntity<>(apiError, apiError.getStatus());
	}

	// All runtime exceptions
	@ExceptionHandler({RuntimeException.class})
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<Object> handleRunTimeException(RuntimeException e) {
		APIError apiError = createError(MESSAGE_FOR_ALL_EXCEPTIONS, HttpStatus.INTERNAL_SERVER_ERROR, e);
		e.printStackTrace(System.out);
		return new ResponseEntity<>(apiError, apiError.getStatus());        
	}
	
	private APIError createError(String msg, HttpStatus status, Exception e) {
		APIError apiError = new APIError(status);
		apiError.setMessage(msg);
		apiError.setDebugMessage(e.getMessage());
		return apiError;
	}
}
