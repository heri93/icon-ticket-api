package com.iconpln.ticket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iconpln.ticket.model.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Long>{

}
