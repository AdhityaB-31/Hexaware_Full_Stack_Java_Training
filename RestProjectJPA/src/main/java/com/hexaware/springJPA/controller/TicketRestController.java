package com.hexaware.springJPA.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.springJPA.entity.Ticket;
import com.hexaware.springJPA.service.ITicketService;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("app/ticket")
@Log4j2
public class TicketRestController {

	@Autowired
	ITicketService service;

	@PostMapping("/add")
	public Ticket addTicket(@RequestBody Ticket ticket) {
		log.info("Ticket Added successfully");
		return service.addTicket(ticket);
	}

	@PutMapping("/update")
	public Ticket updateTicket(@RequestBody Ticket ticket) {
		log.info("Ticket Updated successfully");
		return service.updateTicket(ticket);
	}

	@GetMapping("/getbyid/{ticketId}")
	public Ticket getTicketById(@PathVariable int ticketId) {
		log.debug("Fetching ticket by id: " + ticketId);
		return service.getTicketById(ticketId);
	}

	@GetMapping("/getall")
	public List<Ticket> getAllTickets() {
		log.info("Fetching all tickets");
		return service.getAllTickets();
	}

	@DeleteMapping("/deletebyid/{ticketId}")
	public ResponseEntity<String> deleteTicketById(@PathVariable int ticketId) {
		log.warn("Ticket Record is ready to delete: " + ticketId);
		service.deleteTicketById(ticketId);
		return new ResponseEntity<String>("Ticket Record Deleted Successfully", HttpStatus.ACCEPTED);
	}

	@GetMapping("/getbypassenger/{passengerId}")
	public List<Ticket> getTicketsByPassengerId(@PathVariable int passengerId) {
		log.debug("Fetching tickets for passenger: " + passengerId);
		return service.getTicketsByPassengerId(passengerId);
	}

	@GetMapping("/getbystatus/{bookingStatus}")
	public List<Ticket> getTicketsByBookingStatus(@PathVariable String bookingStatus) {
		log.debug("Fetching tickets by status: " + bookingStatus);
		return service.getTicketsByBookingStatus(bookingStatus);
	}

	@GetMapping("/getbyclass/{travelClass}")
	public List<Ticket> getTicketsByTravelClass(@PathVariable String travelClass) {
		log.debug("Fetching tickets by travel class: " + travelClass);
		return service.getTicketsByTravelClass(travelClass);
	}

	@PutMapping("/updatestatus/{ticketId}/{bookingStatus}")
	public String updateBookingStatus(@PathVariable int ticketId, @PathVariable String bookingStatus) {
		log.info("Updating booking status for ticket: " + ticketId);
		service.updateBookingStatus(ticketId, bookingStatus);
		return "Booking Status Updated Successfully";
	}
}