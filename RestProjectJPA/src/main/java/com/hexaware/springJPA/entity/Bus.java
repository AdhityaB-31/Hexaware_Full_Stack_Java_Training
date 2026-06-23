package com.hexaware.springJPA.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "BusInfo")
public class Bus {

	@Id
	private int busId;

	private String busNumber;

	private String origin;

	private String destination;

	private String departureTime;

	private int totalSeats;

	@OneToOne(mappedBy = "bus", cascade = CascadeType.ALL)
	@JsonIgnore
	private Ticket ticket;
	
	public Bus() {}
	
	public Bus(int busId, String busNumber, String origin, String destination, String departureTime, int totalSeats,
			Ticket ticket) {
		super();
		this.busId = busId;
		this.busNumber = busNumber;
		this.origin = origin;
		this.destination = destination;
		this.departureTime = departureTime;
		this.totalSeats = totalSeats;
		this.ticket = ticket;
	}

	public int getBusId() {
		return busId;
	}

	public void setBusId(int busId) {
		this.busId = busId;
	}

	public String getBusNumber() {
		return busNumber;
	}

	public void setBusNumber(String busNumber) {
		this.busNumber = busNumber;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}

	public int getTotalSeats() {
		return totalSeats;
	}

	public void setTotalSeats(int totalSeats) {
		this.totalSeats = totalSeats;
	}

	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}

	

	@Override
	public String toString() {
		return "Bus [busId=" + busId + ", busNumber=" + busNumber + ", origin=" + origin + ", destination="
				+ destination + ", departureTime=" + departureTime + ", totalSeats=" + totalSeats + "]";
	}

	
}