package com.iconpln.ticket;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.iconpln.ticket.dto.EventDto;
import com.iconpln.ticket.dto.TicketDto;
import com.iconpln.ticket.model.request.BookingRequest;
import com.iconpln.ticket.service.BookingService;
import com.iconpln.ticket.service.EventService;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(EventService eventService, BookingService bookingService) {
        return args -> {
            // Tambahkan data Event
        	Date eventDate = new Date();
        	
        	TicketDto ticketSeminar1 = new TicketDto();
        	ticketSeminar1.setAvailableSeat(300);
        	ticketSeminar1.setPrice(new BigDecimal(250000));
        	ticketSeminar1.setTicketType("Standart");

        	TicketDto ticketSeminar2 = new TicketDto();
        	ticketSeminar2.setAvailableSeat(0);
        	ticketSeminar2.setPrice(new BigDecimal(350000));
        	ticketSeminar2.setTicketType("VIP");
        	
        	
        	List<TicketDto> ticketSeminar = new ArrayList<>();
        	ticketSeminar.add(ticketSeminar1);
        	ticketSeminar.add(ticketSeminar2);
            EventDto eventSeminar = new EventDto();
            eventSeminar.setDate(eventDate);
            eventSeminar.setLocation("Mangga Ballroom (Hotel Durian)");
            eventSeminar.setName("Seminar Sehat");
            eventSeminar.setTickets(ticketSeminar);
            

        	TicketDto ticketWorkshop1 = new TicketDto();
        	ticketWorkshop1.setAvailableSeat(100);
        	ticketWorkshop1.setPrice(new BigDecimal(250000));
        	ticketWorkshop1.setTicketType("Standart");

        	TicketDto ticketWorkshop2 = new TicketDto();
        	ticketWorkshop2.setAvailableSeat(50);
        	ticketWorkshop2.setPrice(new BigDecimal(350000));
        	ticketWorkshop2.setTicketType("Private");
        	
        	
        	List<TicketDto> ticketWorkshop = new ArrayList<>();
        	ticketWorkshop.add(ticketWorkshop1);
        	ticketWorkshop.add(ticketWorkshop2);
            EventDto eventWorkshop = new EventDto();
            eventWorkshop.setDate(eventDate);
            eventWorkshop.setLocation("HJC Ballroom (Hotel Jakarta Center)");
            eventWorkshop.setName("Workshop - Coding beginer");
            eventWorkshop.setTickets(ticketWorkshop);
            
            eventSeminar = eventService.create(eventSeminar);
            eventWorkshop = eventService.create(eventWorkshop);

//            // Tambahkan data Booking
            BookingRequest booking1 = new BookingRequest();
            booking1.setCustomerName("Heri");
            booking1.setTicketId(eventSeminar.getTickets().get(0).getTicketId());
            booking1.setPhoneNumber("081123232111");

            BookingRequest booking2 = new BookingRequest();
            booking2.setCustomerName("Diana");
            booking2.setTicketId(eventWorkshop.getTickets().get(0).getTicketId());
            booking2.setPhoneNumber("081123232111");

            bookingService.bookTicket(booking1);
            bookingService.bookTicket(booking2);
        };
    }

}
