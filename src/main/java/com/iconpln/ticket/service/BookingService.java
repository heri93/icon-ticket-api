package com.iconpln.ticket.service;

import java.util.List;

import com.iconpln.ticket.model.Booking;
import com.iconpln.ticket.model.request.BookingRequest;

public interface BookingService {

	Booking bookTicket(BookingRequest bookingRequest);

	List<Booking> getAllBookings();

}
