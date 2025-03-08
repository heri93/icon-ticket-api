package com.iconpln.ticket.dto;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class EventDto {

	private Long id;


	@NotBlank(message = "Name cannot be blank")
	@Size(max = 100, message = "Name must not exceed 100 characters")
	private String name;
	@NotBlank(message = "Name cannot be blank")
	@Size(max = 100, message = "Name must not exceed 200 characters")
	private String location;
	@NotNull
	private Date date;
	@NotEmpty
	private List<TicketDto> tickets;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public List<TicketDto> getTickets() {
		return tickets;
	}

	public void setTickets(List<TicketDto> tickets) {
		this.tickets = tickets;
	}

}
