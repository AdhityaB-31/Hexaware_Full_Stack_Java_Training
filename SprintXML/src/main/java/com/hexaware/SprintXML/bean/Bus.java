package com.hexaware.SprintXML.bean;

public class Bus {

    private int busId;
    private int operatorId;
    private String busName;
    private String busNumber;
    private String busType;          // SLEEPER_AC | SLEEPER_NON_AC | SEAT_AC | SEAT_NON_AC

    // Sleeper seat counts
    private int lowerBerthCount;
    private int upperBerthCount;

    // Seater seat counts
    private int windowSeatCount;
    private int aisleSeatCount;
    private int middleSeatCount;

    // Auto-calculated in DB (GENERATED ALWAYS AS)
    private int totalSeats;

    private String registrationNumber;
    private String busModel;
    private int manufacturingYear;
    private boolean isActive;

    // ── Constructors ──────────────────────────────────────────
    public Bus() {}

    public Bus(int busId, int operatorId, String busName, String busNumber, String busType) {
        this.busId      = busId;
        this.operatorId = operatorId;
        this.busName    = busName;
        this.busNumber  = busNumber;
        this.busType    = busType;
        this.isActive   = true;
    }

    // ── Getters and Setters ───────────────────────────────────
    public int getBusId() { return busId; }
    public void setBusId(int busId) { this.busId = busId; }

    public int getOperatorId() { return operatorId; }
    public void setOperatorId(int operatorId) { this.operatorId = operatorId; }

    public String getBusName() { return busName; }
    public void setBusName(String busName) { this.busName = busName; }

    public String getBusNumber() { return busNumber; }
    public void setBusNumber(String busNumber) { this.busNumber = busNumber; }

    public String getBusType() { return busType; }
    public void setBusType(String busType) { this.busType = busType; }

    public int getLowerBerthCount() { return lowerBerthCount; }
    public void setLowerBerthCount(int lowerBerthCount) { this.lowerBerthCount = lowerBerthCount; }

    public int getUpperBerthCount() { return upperBerthCount; }
    public void setUpperBerthCount(int upperBerthCount) { this.upperBerthCount = upperBerthCount; }

    public int getWindowSeatCount() { return windowSeatCount; }
    public void setWindowSeatCount(int windowSeatCount) { this.windowSeatCount = windowSeatCount; }

    public int getAisleSeatCount() { return aisleSeatCount; }
    public void setAisleSeatCount(int aisleSeatCount) { this.aisleSeatCount = aisleSeatCount; }

    public int getMiddleSeatCount() { return middleSeatCount; }
    public void setMiddleSeatCount(int middleSeatCount) { this.middleSeatCount = middleSeatCount; }

    public int getTotalSeats() { return totalSeats; }
    public void setTotalSeats(int totalSeats) { this.totalSeats = totalSeats; }

    public String getRegistrationNumber() { return registrationNumber; }
    public void setRegistrationNumber(String registrationNumber) { this.registrationNumber = registrationNumber; }

    public String getBusModel() { return busModel; }
    public void setBusModel(String busModel) { this.busModel = busModel; }

    public int getManufacturingYear() { return manufacturingYear; }
    public void setManufacturingYear(int manufacturingYear) { this.manufacturingYear = manufacturingYear; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean isActive) { this.isActive = isActive; }

    @Override
    public String toString() {
        return "Bus{busId=" + busId + ", busNumber='" + busNumber +
               "', busType='" + busType + "', totalSeats=" + totalSeats + "}";
    }
}

