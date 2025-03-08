package com.iconpln.ticket.exception;

public class EventNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 8935940285688673136L;

	public EventNotFoundException(String message) {
		super(message);
	}
}
