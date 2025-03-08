package com.iconpln.ticket.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.validation.annotation.Validated;

@Validated
public class BookingRequest {

	@NotBlank(message = "Customer name cannot be blank")
	@Size(max = 60, message = "Customer name is maximum 60 characters ")
	private String customerName;

	@NotBlank(message = "Phone number cannot be blank")
	@Size(min = 3, max = 15, message = "Phone number is minimum 3 digit and maximum 15 digit ")
	private String phoneNumber;

	@NotNull(message = "Ticket ID name cannot be blank")
	private Long ticketId;

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Long getTicketId() {
		return ticketId;
	}

	public void setTicketId(Long ticketId) {
		this.ticketId = ticketId;
	}

}
