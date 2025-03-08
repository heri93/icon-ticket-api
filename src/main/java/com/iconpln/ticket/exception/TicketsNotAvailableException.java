package com.iconpln.ticket.exception;

public class TicketsNotAvailableException extends RuntimeException{

	
	private static final long serialVersionUID = 6878758311199670868L;

	public TicketsNotAvailableException(String message) {
		super(message);
	}
}
