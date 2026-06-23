package com.hexaware.springJPA.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hexaware.springJPA.entity.Ticket;
import com.hexaware.springJPA.repository.TicketRepository;

import lombok.extern.log4j.Log4j2;

@Service
@Transactional
@Log4j2
public class TicketServiceImp implements ITicketService {

	@Autowired
	TicketRepository repo;

	@Override
	public Ticket addTicket(Ticket ticket) {
		log.info("Adding ticket: " + ticket.getTicketId());
		return repo.save(ticket);
	}

	@Override
	public Ticket updateTicket(Ticket ticket) {
		log.info("Updating ticket: " + ticket.getTicketId());
		return repo.save(ticket);
	}

	@Override
	public Ticket getTicketById(int ticketId) {
		log.info("Fetching ticket by id: " + ticketId);
		return repo.findById(ticketId).orElse(null);
	}

	@Override
	public void deleteTicketById(int ticketId) {
		log.warn("Deleting ticket with id: " + ticketId);
		repo.deleteById(ticketId);
	}

	@Override
	public List<Ticket> getAllTickets() {
		log.info("Fetching all tickets");
		return repo.findAll();
	}

	@Override
	public List<Ticket> getTicketsByPassengerId(int passengerId) {
		log.info("Fetching tickets for passenger: " + passengerId);
		return repo.findByPassengerPassengerId(passengerId);
	}

	@Override
	public List<Ticket> getTicketsByBookingStatus(String bookingStatus) {
		log.info("Fetching tickets by booking status: " + bookingStatus);
		return repo.findByBookingStatus(bookingStatus);
	}

	@Override
	public List<Ticket> getTicketsByTravelClass(String travelClass) {
		log.info("Fetching tickets by travel class: " + travelClass);
		return repo.findByTravelClass(travelClass);
	}

	@Override
	public void updateBookingStatus(int ticketId, String bookingStatus) {
		log.info("Updating booking status for ticket: " + ticketId);
		repo.updateBookingStatus(ticketId, bookingStatus);
	}
}