package com.hexaware.hibernate.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

@Entity
@Table(name="passengers")
public class Passenger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int passengerId;

    private String passengerName;

    private int age;

    private String gender;

    

	@ManyToOne
    @JoinColumn(name="booking_id")
    private Booking booking;
    
    public Passenger() {}

	public Passenger(String passengerName, int age, String gender, Booking booking) {
		super();
		this.passengerName = passengerName;
		this.age = age;
		this.gender = gender;
		this.booking = booking;
	}

	public int getPassengerId() {
		return passengerId;
	}

	public void setPassengerId(int passengerId) {
		this.passengerId = passengerId;
	}

	public String getPassengerName() {
		return passengerName;
	}

	public void setPassengerName(String passengerName) {
		this.passengerName = passengerName;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Booking getBooking() {
		return booking;
	}

	public void setBooking(Booking booking) {
		this.booking = booking;
	}
	
	/*
	 * @Override public String toString() { return "Passenger [passengerId=" +
	 * passengerId + ", passengerName=" + passengerName + ", age=" + age +
	 * ", gender=" + gender + ", booking=" + booking + "]"; }
	 */
	
	@Override
	public String toString() {
	    return "Passenger [passengerId=" + passengerId +
	           ", passengerName=" + passengerName +
	           ", bookingId=" +
	           (booking != null ? booking.getBookingId() : null)
	           + "]";
	}

    
}