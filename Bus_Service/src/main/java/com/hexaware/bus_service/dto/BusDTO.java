package com.hexaware.bus_service.dto;

public class BusDTO {
	private int busId;

    private String busName;

    private String busNumber;

    private double fare;
    
    public BusDTO() {}

	public BusDTO(int busId, String busName, String busNumber, double fare) {
		super();
		this.busId = busId;
		this.busName = busName;
		this.busNumber = busNumber;
		this.fare = fare;
	}

	public int getBusId() {
		return busId;
	}

	public void setBusId(int busId) {
		this.busId = busId;
	}

	public String getBusName() {
		return busName;
	}

	public void setBusName(String busName) {
		this.busName = busName;
	}

	public String getBusNumber() {
		return busNumber;
	}

	public void setBusNumber(String busNumber) {
		this.busNumber = busNumber;
	}

	public double getFare() {
		return fare;
	}

	public void setFare(double fare) {
		this.fare = fare;
	}

	@Override
	public String toString() {
		return "BusDTO [busId=" + busId + ", busName=" + busName + ", busNumber=" + busNumber + ", fare=" + fare + "]";
	}
    
    
}
