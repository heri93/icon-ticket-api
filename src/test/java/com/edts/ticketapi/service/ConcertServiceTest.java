package com.edts.ticketapi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.iconpln.ticket.model.Event;
import com.iconpln.ticket.model.request.SearchEventRequest;
import com.iconpln.ticket.service.impl.EventServiceImpl;

@ExtendWith(MockitoExtension.class)
class ConcertServiceTest {

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private EventServiceImpl concertService;

    @SuppressWarnings("unchecked")
	@Test
    void testSearchConcerts() {
        // Initialize Mockito annotations
        MockitoAnnotations.openMocks(this);

        // Mock the CriteriaBuilder, CriteriaQuery, Root, Join, and TypedQuery
        CriteriaBuilder criteriaBuilder = mock(CriteriaBuilder.class);
        CriteriaQuery<Event> criteriaQuery = mock(CriteriaQuery.class);
        Root<Event> root = mock(Root.class);
        Join<Object, Object> ticketJoin = mock(Join.class);
        TypedQuery<Event> typedQuery = mock(TypedQuery.class);
        Predicate predicate = mock(Predicate.class);

        Path<Object> path = mock(Path.class);
        // Mock the all object to return the mocked objects
        when(root.get(any(String.class))).thenReturn(path);
        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery(Event.class)).thenReturn(criteriaQuery);
        when(criteriaQuery.from(Event.class)).thenReturn(root);
        when(root.join("tickets", JoinType.LEFT)).thenReturn(ticketJoin);
        when(ticketJoin.get(any(String.class))).thenReturn(path);
        when(entityManager.createQuery(any(CriteriaQuery.class))).thenReturn(typedQuery);
        when(criteriaBuilder.like(any(Expression.class), any(String.class))).thenReturn(predicate);
        when(criteriaBuilder.equal(any(Expression.class), any(Date.class))).thenReturn(predicate);
        when(criteriaBuilder.greaterThan(any(Expression.class), any(Integer.class))).thenReturn(predicate);
        when(criteriaBuilder.or(any(Predicate.class))).thenReturn(predicate);
        when(criteriaBuilder.and(any(Predicate.class),any(Predicate.class))).thenReturn(predicate);
        when(criteriaQuery.select(any(Root.class))).thenReturn(criteriaQuery);
        when(criteriaQuery.distinct(any(Boolean.class))).thenReturn(criteriaQuery);
        when(criteriaQuery.where(any(Predicate.class))).thenReturn(criteriaQuery);
        
        // Mock the Concert entity
        Event concert = new Event();
        concert.setId(1L);
        concert.setName("Test Concert");
        concert.setLocation("Test Location");
        concert.setEventDate(new Date());
        List<Event> expectedConcerts = new ArrayList<>();
        expectedConcerts.add(concert);

        // Mock the result of the query execution
        when(typedQuery.getResultList()).thenReturn(expectedConcerts);

        // Create a SearchConcertRequest with test values
        SearchEventRequest searchConcertRequest = new SearchEventRequest();
        searchConcertRequest.setLocation("Test Location");
        searchConcertRequest.setDate(new Date());

        // Call the method to be tested
        List<Event> resultConcerts = concertService.searchEvents(searchConcertRequest);

        // Verify that the query was constructed with the correct parameters
        verify(criteriaBuilder).like(any(Expression.class), any(String.class));
        verify(criteriaBuilder).equal(any(Expression.class), any(Date.class));
        verify(criteriaBuilder).greaterThan(any(Expression.class), any(Integer.class));
        verify(criteriaQuery).select(any(Root.class));
        verify(criteriaQuery).distinct(true);
        verify(criteriaQuery).where(any(Predicate.class));
        verify(typedQuery).getResultList();

        // Assert that the result matches the expectedConcerts
        assertEquals(expectedConcerts, resultConcerts);
    }	
}
