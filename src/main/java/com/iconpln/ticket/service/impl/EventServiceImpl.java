package com.iconpln.ticket.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iconpln.ticket.dto.EventDto;
import com.iconpln.ticket.dto.TicketDto;
import com.iconpln.ticket.model.Event;
import com.iconpln.ticket.model.Ticket;
import com.iconpln.ticket.model.request.SearchEventRequest;
import com.iconpln.ticket.repository.EventRepository;
import com.iconpln.ticket.service.EventService;

@Service
public class EventServiceImpl implements EventService {

	private EventRepository eventRepository;

	@Autowired
	public void setEventRepository(EventRepository eventRepository) {
		this.eventRepository = eventRepository;
	}

	private EntityManager entityManager;

	@Autowired
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	/**
	 * Searches for concerts based on the provided search criteria.
	 *
	 * @param searchConcert The search criteria for concerts.
	 * @return A list of concerts that match the search criteria.
	 */
	@Override
	public List<Event> searchEvents(
			@NotBlank(message = "searchConcert cannot be blank") SearchEventRequest searchConcert) {

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Event> criteriaQuery = criteriaBuilder.createQuery(Event.class);
		Root<Event> root = criteriaQuery.from(Event.class);
		Join<Event, Ticket> ticketJoin = root.join("tickets", JoinType.LEFT);
		List<Predicate> predicates = new ArrayList<>();

		// Parameters of search
		if (searchConcert.getLocation() != null && !searchConcert.getLocation().isEmpty()) {
			predicates.add(criteriaBuilder.like(root.get("location"), "%" + searchConcert.getLocation() + "%"));
		}

		if (searchConcert.getDate() != null) {
			predicates.add(criteriaBuilder.equal(root.get("eventDate"), searchConcert.getDate()));
		}

		if (searchConcert.getName() != null && !searchConcert.getName().isEmpty()) {
			predicates.add(criteriaBuilder.like(root.get("name"), "%" + searchConcert.getName() + "%"));
		}

		// Add predicate to filter availableSeat > 0
		Predicate finalPredicate = criteriaBuilder.greaterThan(ticketJoin.get("availableSeat"), 0);

		// Add filter data from parameter values into criteria builder using 'OR'
		if (!predicates.isEmpty()) {
			Predicate parametersPredicate = criteriaBuilder.or(predicates.toArray(new Predicate[0]));
			finalPredicate = criteriaBuilder.and(finalPredicate, parametersPredicate);

		}

		// Build the final query
		criteriaQuery.select(root).distinct(true).where(finalPredicate);

		// Execute the query
		return entityManager.createQuery(criteriaQuery).getResultList();
	}

	private Event toModel(EventDto eventDto) {
		Event event = new Event();
		List<Ticket> tickets = new ArrayList<>();
		event.setId(eventDto.getId());
		event.setEventDate(eventDto.getDate());
		event.setLocation(eventDto.getLocation());
		event.setName(eventDto.getName());
		for (TicketDto ticketDto : eventDto.getTickets()) {
			Ticket ticket = new Ticket();
			ticket.setId(ticketDto.getTicketId());
			ticket.setAvailableSeat(ticketDto.getAvailableSeat());
			ticket.setPrice(ticketDto.getPrice());
			ticket.setTicketType(ticketDto.getTicketType());
			ticket.setEvent(event);
			tickets.add(ticket);

		}
		event.setTickets(tickets);
		return event;
	}

	private EventDto toDto(Event event) {
		EventDto eventDto = new EventDto();
		List<TicketDto> ticketDtos = new ArrayList<>();
		eventDto.setId(event.getId());
		eventDto.setDate(event.getEventDate());
		eventDto.setLocation(event.getLocation());
		eventDto.setName(event.getName());
		for (Ticket ticket : event.getTickets()) {
			TicketDto ticketDto = new TicketDto();
			ticketDto.setTicketId(ticket.getId());
			ticketDto.setAvailableSeat(ticket.getAvailableSeat());
			ticketDto.setPrice(ticket.getPrice());
			ticketDto.setTicketType(ticket.getTicketType());
			ticketDtos.add(ticketDto);
		}
		eventDto.setTickets(ticketDtos);
		return eventDto;
	}

	@Override
	public EventDto getById(Long id) {
		Event event = eventRepository.findById(id).get();

		return toDto(event);
	}

	@Override
	public EventDto create(@Valid EventDto eventDto) {

		Event event = toModel(eventDto);
		event = eventRepository.save(event);
		return toDto(event);

	}

	@Override
	public EventDto update(Long id, @Valid EventDto eventDto) {

		Event event = toModel(eventDto);
		event = eventRepository.save(event);
		return toDto(event);

	}

	@Override
	public void delete(Long id) {
		eventRepository.deleteById(id);
	}
}
