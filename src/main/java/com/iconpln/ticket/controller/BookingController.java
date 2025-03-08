package com.iconpln.ticket.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iconpln.ticket.model.Booking;
import com.iconpln.ticket.model.request.BookingRequest;
import com.iconpln.ticket.service.BookingService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/bookings")
@Validated
public class BookingController {

	private BookingService bookingService;

	@Autowired
	public void setBookingService(BookingService bookingService) {
		this.bookingService = bookingService;
	}
	
	@PostMapping("/book")
	@Operation(summary = "Create Booking for Event")
	public ResponseEntity<Booking> bookEvent(
			@RequestBody @Valid BookingRequest bookingRequest) {
		return new ResponseEntity<>(bookingService.bookTicket(bookingRequest),  HttpStatus.OK);
	}

	@GetMapping
	@Operation(summary = "Get a list of Booking")
	public ResponseEntity<List<Booking>> getAllBooking() {

		List<Booking> bookings = bookingService.getAllBookings();
			return new ResponseEntity<>(bookings, HttpStatus.OK);
	}
}
