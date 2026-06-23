package com.hexaware.springJPA.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "PassengerInfo")
@NamedQueries(@NamedQuery(name = "Passenger.getAllAgeLT", query = "select p from Passenger p where p.age < ?1 order by p.age asc"))
public class Passenger {

	@Id
	private int passengerId;

	private String passengerName;

	private int age;

	private String gender;

	@OneToMany(mappedBy = "passenger")
	@JsonIgnore
	private List<Ticket> tickets;

	@ManyToMany(mappedBy = "passengers")
	@JsonIgnore
	private List<Baggage> baggages;

	public Passenger() {
	}

	public Passenger(int passengerId, String passengerName, int age, String gender) {
		super();
		this.passengerId = passengerId;
		this.passengerName = passengerName;
		this.age = age;
		this.gender = gender;
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

	public List<Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(List<Ticket> tickets) {
		this.tickets = tickets;
	}

	public List<Baggage> getBaggages() {
		return baggages;
	}

	public void setBaggages(List<Baggage> baggages) {
		this.baggages = baggages;
	}

	@Override
	public String toString() {
		return "Passenger [passengerId=" + passengerId + ", passengerName=" + passengerName + ", age=" + age
				+ ", gender=" + gender + "]";
	}
}