package com.hexaware.passenger_service.dto;

public class PassengerDTO {

	private int passengerId;

    private String name;

    private int age;

    private String gender;

    private int busId;
    
    

	public PassengerDTO() {
		super();
	}



	public PassengerDTO(int passengerId, String name, int age, String gender, int busId) {
		super();
		this.passengerId = passengerId;
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.busId = busId;
	}



	public int getPassengerId() {
		return passengerId;
	}



	public void setPassengerId(int passengerId) {
		this.passengerId = passengerId;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
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



	public int getBusId() {
		return busId;
	}



	public void setBusId(int busId) {
		this.busId = busId;
	}



	@Override
	public String toString() {
		return "PassengerDTO [passengerId=" + passengerId + ", name=" + name + ", age=" + age + ", gender=" + gender
				+ ", busId=" + busId + "]";
	}
	
}
