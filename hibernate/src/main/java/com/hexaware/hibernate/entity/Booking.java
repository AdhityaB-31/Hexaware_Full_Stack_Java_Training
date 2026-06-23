package com.hexaware.hibernate.entity;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookingId;

    private BigDecimal totalFare;

    @OneToMany(mappedBy = "booking",
               cascade = CascadeType.ALL)
    private List<Passenger> passengers;
    
    

	

	public Booking(BigDecimal totalFare, List<Passenger> passengers) {
		super();
		this.totalFare = totalFare;
		this.passengers = passengers;
	}

	public Booking() {
		// TODO Auto-generated constructor stub
	}


	public int getBookingId() {
		return bookingId;
	}

	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}

	public BigDecimal getTotalFare() {
		return totalFare;
	}

	public void setTotalFare(int i) {
		this.totalFare = BigDecimal.valueOf(i);
	}

	public List<Passenger> getPassengers() {
		return passengers;
	}

	public void setPassengers(List<Passenger> passengers) {
		this.passengers = passengers;
	}
	
	@Override
	public String toString() {
		return "Booking [bookingId=" + bookingId + ", totalFare=" + totalFare + ", passengers=" + passengers + "]";
	}

    
}