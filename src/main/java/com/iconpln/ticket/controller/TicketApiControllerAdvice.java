package com.iconpln.ticket.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.iconpln.ticket.exception.EventNotFoundException;
import com.iconpln.ticket.exception.TicketsNotAvailableException;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class TicketApiControllerAdvice extends ResponseEntityExceptionHandler  {

	private final Logger log = LogManager.getLogger(TicketApiControllerAdvice.class);

	@ExceptionHandler(value = { IllegalArgumentException.class, IllegalStateException.class })
	protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
		log.error("FATAL ERROR!!! : "+ ex.getMessage());
		String bodyOfResponse = "Fatal Error, please contact administrator";
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
	}

	@ExceptionHandler(value = { EventNotFoundException.class, TicketsNotAvailableException.class})
	protected ResponseEntity<Object> handleBusinessException(RuntimeException e) {
		log.warn("Error Request : "+ e.getMessage());
		return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
	}
	@Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

	    Map<String, String> errors = new HashMap<>();
	    ex.getBindingResult().getAllErrors().forEach((error) -> {
	        String fieldName = ((FieldError) error).getField();
	        String errorMessage = error.getDefaultMessage();
	        errors.put(fieldName, errorMessage);
	    });

		return new ResponseEntity<>(errors, HttpStatus.OK);
	
	}
	 
}
