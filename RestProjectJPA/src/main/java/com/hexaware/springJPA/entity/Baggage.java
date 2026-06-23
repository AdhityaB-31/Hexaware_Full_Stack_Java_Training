package com.hexaware.springJPA.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "BaggageInfo")
public class Baggage {

	@Id
	private int baggageId;

	private double weightKg;

	private String baggageType;

	private String tagNumber;

	@ManyToMany
	@JoinTable(name = "passenger_baggage", joinColumns = @JoinColumn(name = "baggage_id"), inverseJoinColumns = @JoinColumn(name = "passenger_id"))
	@JsonIgnore
	private List<Passenger> passengers;

	public Baggage() {
	}

	public Baggage(int baggageId, double weightKg, String baggageType, String tagNumber) {
		super();
		this.baggageId = baggageId;
		this.weightKg = weightKg;
		this.baggageType = baggageType;
		this.tagNumber = tagNumber;
	}

	public int getBaggageId() {
		return baggageId;
	}

	public void setBaggageId(int baggageId) {
		this.baggageId = baggageId;
	}

	public double getWeightKg() {
		return weightKg;
	}

	public void setWeightKg(double weightKg) {
		this.weightKg = weightKg;
	}

	public String getBaggageType() {
		return baggageType;
	}

	public void setBaggageType(String baggageType) {
		this.baggageType = baggageType;
	}

	public String getTagNumber() {
		return tagNumber;
	}

	public void setTagNumber(String tagNumber) {
		this.tagNumber = tagNumber;
	}

	public List<Passenger> getPassengers() {
		return passengers;
	}

	public void setPassengers(List<Passenger> passengers) {
		this.passengers = passengers;
	}

	@Override
	public String toString() {
		return "Baggage [baggageId=" + baggageId + ", weightKg=" + weightKg + ", baggageType=" + baggageType
				+ ", tagNumber=" + tagNumber + "]";
	}
}