package com.iconpln.ticket.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class TicketDto {

	private Long ticketId;

	@NotBlank(message = "Ticket Type cannot be blank")
	@Size(max = 100, message = "Ticket Type must not exceed 100 characters")
	private String ticketType;

	@NotNull(message = "Price cannot be blank")
	private BigDecimal price;
	@NotNull(message = "Available Seat cannot be blank")
	private Integer availableSeat;
	
	public Long getTicketId() {
		return ticketId;
	}
	public void setTicketId(Long ticketId) {
		this.ticketId = ticketId;
	}
	public String getTicketType() {
		return ticketType;
	}
	public void setTicketType(String ticketType) {
		this.ticketType = ticketType;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public Integer getAvailableSeat() {
		return availableSeat;
	}
	public void setAvailableSeat(Integer availableSeat) {
		this.availableSeat = availableSeat;
	}
	
	
}
