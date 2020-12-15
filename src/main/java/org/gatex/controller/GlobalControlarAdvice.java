package org.gatex.controller;


import lombok.extern.slf4j.Slf4j;
import org.gatex.exception.InvalidKeyException;
import org.gatex.exception.RecordNotFoundException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@ControllerAdvice
public class GlobalControlarAdvice {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Set<String>> handleInputValidation(MethodArgumentNotValidException ex, WebRequest req) {

		Set<String> errors = new HashSet<>();
		for (FieldError error : ex.getBindingResult().getFieldErrors()) {
			errors.add(error.getDefaultMessage());
		}
		for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
			errors.add(error.getDefaultMessage());
		}
		log.error("handleInputValidation {}", ex.getMessage());
		return new ResponseEntity<>(errors, HttpStatus.NOT_ACCEPTABLE);
	}
	
	@ExceptionHandler(DuplicateKeyException.class)
	public  ResponseEntity<String> handleDuplicateKey(DuplicateKeyException ex, WebRequest req) {
		log.error("handleDuplicateKey {} {}", ex.getMessage(), ex);
		return new ResponseEntity<>("Record exists !", HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(RecordNotFoundException.class)
	public ResponseEntity<String> handleRecordNotFoundException(RecordNotFoundException e, WebRequest req) {
		log.error("handleRecordNotFoundException {} {}", e.getMessage(), e);
		return new ResponseEntity<>("Record not Found !", HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(InvalidKeyException.class)
	public ResponseEntity<String> handleInvalidKeyException(InvalidKeyException e, WebRequest req) {
		log.error("handleInvalidKeyException {} {}", e.getMessage(), e);
		return new ResponseEntity<>("Access Denied !", HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleAllOtherException(Exception e, WebRequest req) {
		log.info(e.getClass().toString());
		log.error("handleAllOtherException {} {}", e.getMessage(), e);
		return new ResponseEntity<>("Something went wrong with the server !", HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
