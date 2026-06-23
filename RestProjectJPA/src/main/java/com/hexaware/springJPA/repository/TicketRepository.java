package com.hexaware.springJPA.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hexaware.springJPA.entity.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {

	public List<Ticket> findByPassengerPassengerId(int passengerId);

	public List<Ticket> findByBookingStatus(String bookingStatus);

	public List<Ticket> findByTravelClass(String travelClass);

	@Modifying
	@Query("update Ticket t set t.bookingStatus = ?2 where t.ticketId = ?1")
	public void updateBookingStatus(int ticketId, String bookingStatus);
}