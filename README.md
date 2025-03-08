# Event Ticket Reservation API

## Overview

The Event Ticket Reservation API is a simple service for managing event ticket reservations. It provides endpoints for searching available events and booking tickets.

### Database Desain 
- `Event` have many tickets, because in one Event have some ticket type. Such as VVIP, VIP, Festival, etc. So the relationship between the `event` table and `ticket` is 1 to many.
- `Booking` table have field of customer data. There are customer name and phone number.
- The `booking` table has a relationship to the `ticket` table, because one type of ticket can be purchased by several customers according to seat availability or stock.

#### Event Table
- `event_name` : Name of event
- `location` : Lokation of event
- `event_date` : Date of event

#### Ticket Table
- `event_id` : ID of event table
- `ticket_type` : Type of ticket example VVIP, VIP, Festival, etc
- `price` : Price of ticket
- `available_seat` : number of available tickets

#### Booking Table
- `ticket_id` : ID of ticket table
- `customer_name` : Customer name
- `phone_number` : Phone number of customer

## Tech Stack

- Java (min: JDK 8)
- Spring Boot
- Maven
- Hibernate JPA
- PostgreSQL (min: 16)

## Getting Started

### Prerequisites

- Java Development Kit (JDK) 8 or higher
- Maven
- PostgreSQL database (min: 16)
- Postman (min 10 or higher)

### Setup

1. Clone the repository:

   ```bash
   git clone https://github.com/heri93/ticket-api.git
2. Connfigure the PostgreSQL database by updating the application.properties file with your database details.
   ```path
   /ticket-api/src/main/resources/application.properties
4. Build and run the project (By default, the API can be accessible at `http://localhost:8080`) :
   ```bash
   cd event-ticket-reservation-api
   mvn spring-boot:run

5. Restore the backup file `ticket-api/db-file/ticket-event-db.tar`

## API Endpoints
### Search Available Event
- Endpoint: `/event/search`
- Method: `POST`
- Request body: JSON object
  - `location` (optional): Location of the event
  - `date` (optional): Date of the event (format: YYYY-MM-DD)
  - `name` (optional): Name of the event
  Example JSON Request
   ```json
   {
    "location":"Jakar" ,
    "name":"Seminar",
    "date":"2025-03-08" 
   }
- Response:
`200 OK` with a JSON array of available events
`400 Bad Request` for invalid requests

### Book a Ticket
- Endpoint: `/bookings/book`
- Method: `POST`
- Request body: JSON object
  - `customerName` (mandatory): Customer Name that booking ticket
  - `phoneNumber` (mandatory): Phone Number of customer that booking ticket
  - `ticketId` (mandatory): Ticket ID
  Example JSON Request
   ```json
   {
    "customerName": "Charless",
    "phoneNumber": "081828108089809",
    "ticketId": "10"
   }
- Response:
`200 OK` with a JSON array of available events
`400 Bad Request` for invalid requests

## Testing
### Unit Test
- Unit tests are available for Event Service and Booking Service. Run the following command to execute the tests:
   ```bash
    mvn test
    
### API Test
Import the postman files at `ticket-api/postman`
- Icon PLN - Ticket.postman_collection-V2.1.json
  or
- Icon PLN - Ticket.postman_collection-V2.0.json
- All of request scenarios are already to test
- Access swagger documentation : `http://localhost:8080/swagger-ui/index.html`
