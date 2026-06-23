package com.hexaware.MySpring.Entity;

import java.math.BigDecimal;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component
@Scope("prototype")
public class Passenger {

    
    private int passengerId;

    private String passengerName;

    private int age;

    private String gender;

    

    
    public Passenger() {}

	public Passenger(String passengerName, int age, String gender) {
		super();
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


	
	/*
	 * @Override public String toString() { return "Passenger [passengerId=" +
	 * passengerId + ", passengerName=" + passengerName + ", age=" + age +
	 * ", gender=" + gender + ", booking=" + booking + "]"; }
	 */
	
	@Override
	public String toString() {
	    return "Passenger [passengerId=" + passengerId +
	           ", passengerName=" + passengerName +
	           ", bookingId="
	           + "]";
	}

    
}
