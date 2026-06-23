package com.hexaware.springJPA.service;

import java.util.List;

import com.hexaware.springJPA.entity.Ticket;

public interface ITicketService {

	public Ticket addTicket(Ticket ticket);

	public Ticket updateTicket(Ticket ticket);

	public Ticket getTicketById(int ticketId);

	public void deleteTicketById(int ticketId);

	public List<Ticket> getAllTickets();

	public List<Ticket> getTicketsByPassengerId(int passengerId);

	public List<Ticket> getTicketsByBookingStatus(String bookingStatus);

	public List<Ticket> getTicketsByTravelClass(String travelClass);

	public void updateBookingStatus(int ticketId, String bookingStatus);
}