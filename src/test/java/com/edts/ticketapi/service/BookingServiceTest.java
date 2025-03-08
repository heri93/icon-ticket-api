package com.edts.ticketapi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.iconpln.ticket.exception.EventNotFoundException;
import com.iconpln.ticket.exception.TicketsNotAvailableException;
import com.iconpln.ticket.model.Booking;
import com.iconpln.ticket.model.Event;
import com.iconpln.ticket.model.Ticket;
import com.iconpln.ticket.model.request.BookingRequest;
import com.iconpln.ticket.repository.BookingRepository;
import com.iconpln.ticket.repository.TicketRepository;
import com.iconpln.ticket.service.impl.BookingServiceImpl;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class BookingServiceTest {

	@Mock
	private BookingRepository bookingRepository;

	@Mock
	private TicketRepository ticketRepository;

	@InjectMocks
	private BookingServiceImpl bookingService;

	private static final String PHONE_NUMBER = "000000";
	private static final String CUSTOMER_NAME = "Test customer name";

	@Test
	void bookTicket_Valid_Available() {
		// Arrange
		Integer availableTicket = 100;
		Ticket ticket = new Ticket();
		ticket.setAvailableSeat(availableTicket);
		ticket.setId(new Long(1));
		ticket.setPrice(new BigDecimal("1000"));
		ticket.setTicketType("VIP");

		Booking expectedBooking = new Booking();
		expectedBooking.setPhoneNumber(PHONE_NUMBER);
		expectedBooking.setCustomerName(CUSTOMER_NAME);
		expectedBooking.setTicket(ticket);
		expectedBooking.setId(new Long(1));

		when(ticketRepository.findById(anyLong())).thenReturn(Optional.of(ticket));
		when(bookingRepository.save(any())).thenReturn(expectedBooking);

		// Create a BookingRequest with test values
		BookingRequest bookingRequest = new BookingRequest();
		bookingRequest.setCustomerName(CUSTOMER_NAME);
		bookingRequest.setPhoneNumber(PHONE_NUMBER);
		bookingRequest.setTicketId(new Long("1"));

		// Act
		Booking bookingResult = bookingService.bookTicket(bookingRequest);

		// Assert result and expected Booking object
		assertNotNull(bookingResult);
		assertEquals(expectedBooking.getTicket(), bookingResult.getTicket());

		// Assert logic available ticket if success
		assertEquals(expectedBooking.getTicket().getAvailableSeat(), availableTicket - 1);

		// Verify that save method is called
		verify(ticketRepository, times(1)).save(any());
		verify(bookingRepository, times(1)).save(any());
	}

	@Test
	void bookTicket_Valid_Unavailable() {
		// Arrange
		Integer availableTicket = 0;
		Ticket ticket = new Ticket();
		ticket.setAvailableSeat(availableTicket);
		ticket.setId(new Long(1));
		ticket.setPrice(new BigDecimal("1000"));
		ticket.setTicketType("VIP");
		
		Event concert = new Event();
		concert.setEventDate(new Date());
		concert.setName("Concert nam test");
		concert.setLocation("Concert location test");
		ticket.setEvent(concert);

		Booking expectedBooking = new Booking();
		expectedBooking.setPhoneNumber(PHONE_NUMBER);
		expectedBooking.setCustomerName(CUSTOMER_NAME);
		expectedBooking.setTicket(ticket);
		expectedBooking.setId(new Long(1));

		when(ticketRepository.findById(anyLong())).thenReturn(Optional.of(ticket));

		// Create a BookingRequest with test values
		BookingRequest bookingRequest = new BookingRequest();
		bookingRequest.setCustomerName(CUSTOMER_NAME);
		bookingRequest.setPhoneNumber(PHONE_NUMBER);
		bookingRequest.setTicketId(new Long("1"));

		// Act
		TicketsNotAvailableException thrown = assertThrows(TicketsNotAvailableException.class,
				() -> bookingService.bookTicket(bookingRequest), "Expected bookTicket() to throw, but it didn't");

		assertTrue(thrown.getMessage().equals("Tickets not available for event " + ticket.getEvent().getName()
				+ " with ticket ID: " + bookingRequest.getTicketId()));

		// Verify that save method is called
		verify(ticketRepository, times(0)).save(any());
		verify(bookingRepository, times(0)).save(any());
	}

	@Test
	void bookTicket_Invalid() {
		// Arrange
		Integer availableTicket = 100;
		Ticket ticket = new Ticket();
		ticket.setAvailableSeat(availableTicket);
		ticket.setId(new Long(1));
		ticket.setPrice(new BigDecimal("1000"));
		ticket.setTicketType("VIP");

		Booking expectedBooking = new Booking();
		expectedBooking.setPhoneNumber(PHONE_NUMBER);
		expectedBooking.setCustomerName(CUSTOMER_NAME);
		expectedBooking.setTicket(ticket);
		expectedBooking.setId(new Long(1));

		when(ticketRepository.findById(anyLong())).thenReturn(Optional.empty());

		// Create a BookingRequest with test values
		BookingRequest bookingRequest = new BookingRequest();
		bookingRequest.setCustomerName(CUSTOMER_NAME);
		bookingRequest.setPhoneNumber(PHONE_NUMBER);
		bookingRequest.setTicketId(new Long("1"));

		// Act
		EventNotFoundException thrown = assertThrows(EventNotFoundException.class,
				() -> bookingService.bookTicket(bookingRequest), "Expected bookTicket() to throw, but it didn't");

		assertTrue(thrown.getMessage().equals("Ticket not found with ticket ID: " + bookingRequest.getTicketId()));

		// Verify that save method is called
		verify(ticketRepository, times(0)).save(any());
		verify(bookingRepository, times(0)).save(any());
	}

}
