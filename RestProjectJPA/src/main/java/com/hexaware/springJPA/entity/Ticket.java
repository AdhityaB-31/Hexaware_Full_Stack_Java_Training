package com.hexaware.springJPA.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "TicketInfo")
public class Ticket {

	@Id
	private int ticketId;

	private String seatNumber;

	private String travelClass;

	private double fare;

	private String bookingStatus;

	@OneToOne
	@JoinColumn(name = "bus_id")
	private Bus bus;

	@ManyToOne
	@JoinColumn(name = "passenger_id")
	private Passenger passenger;

	public Ticket() {
	}

	public Ticket(int ticketId, String seatNumber, String travelClass, double fare, String bookingStatus,
			Bus bus, Passenger passenger) {
		super();
		this.ticketId = ticketId;
		this.seatNumber = seatNumber;
		this.travelClass = travelClass;
		this.fare = fare;
		this.bookingStatus = bookingStatus;
		this.bus = bus;
		this.passenger = passenger;
	}

	public int getTicketId() {
		return ticketId;
	}

	public void setTicketId(int ticketId) {
		this.ticketId = ticketId;
	}

	public String getSeatNumber() {
		return seatNumber;
	}

	public void setSeatNumber(String seatNumber) {
		this.seatNumber = seatNumber;
	}

	public String getTravelClass() {
		return travelClass;
	}

	public void setTravelClass(String travelClass) {
		this.travelClass = travelClass;
	}

	public double getFare() {
		return fare;
	}

	public void setFare(double fare) {
		this.fare = fare;
	}

	public String getBookingStatus() {
		return bookingStatus;
	}

	public void setBookingStatus(String bookingStatus) {
		this.bookingStatus = bookingStatus;
	}

	public Bus getBus() {
		return bus;
	}

	public void setBus(Bus bus) {
		this.bus = bus;
	}

	public Passenger getPassenger() {
		return passenger;
	}

	public void setPassenger(Passenger passenger) {
		this.passenger = passenger;
	}

	@Override
	public String toString() {
		return "Ticket [ticketId=" + ticketId + ", seatNumber=" + seatNumber + ", travelClass=" + travelClass
				+ ", fare=" + fare + ", bookingStatus=" + bookingStatus + "]";
	}
}