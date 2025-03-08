package com.iconpln.ticket.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iconpln.ticket.dto.EventDto;
import com.iconpln.ticket.model.Event;
import com.iconpln.ticket.model.request.SearchEventRequest;
import com.iconpln.ticket.service.EventService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/event")
@Validated
public class EventController {

	private EventService eventService;

	@Autowired
	public void setEventService(EventService eventService) {
		this.eventService = eventService;
	}
	@PostMapping("/search")
	@Operation(summary = "Search event")
	public 	ResponseEntity<List<Event>> search(
			@RequestBody @NotNull(message = "searchEvent cannot be null") SearchEventRequest searchEvent) {
		return new ResponseEntity<>(eventService.searchEvents(searchEvent),  HttpStatus.OK);
	}
	

	@GetMapping("/{id}")
    @Operation(summary = "Get one data by ID of Event")
    public ResponseEntity<EventDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(eventService.getById(id));
    }

	@PostMapping
    @Operation(summary = "Create a Event")
    public ResponseEntity<EventDto> create(@RequestBody @Valid EventDto eventDto) {
        return ResponseEntity.ok(eventService.create(eventDto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update one data by ID of Event")
    public ResponseEntity<EventDto> update(@PathVariable Long id, @RequestBody @Valid EventDto  eventDto) {
        return ResponseEntity.ok(eventService.update(id, eventDto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete one data by ID of Event")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        eventService.delete(id);
        return ResponseEntity.ok("Success!");
    }
}
