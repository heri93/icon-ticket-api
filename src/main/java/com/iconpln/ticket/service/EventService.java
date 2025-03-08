package com.iconpln.ticket.service;

import java.util.List;

import javax.validation.Valid;

import com.iconpln.ticket.dto.EventDto;
import com.iconpln.ticket.model.Event;
import com.iconpln.ticket.model.request.SearchEventRequest;

public interface EventService {

	List<Event> searchEvents(SearchEventRequest searchConcert);

	EventDto getById(Long id);

	EventDto create(@Valid EventDto eventDto);

	EventDto update(Long id, @Valid EventDto eventDto);

	void delete(Long id);

}
