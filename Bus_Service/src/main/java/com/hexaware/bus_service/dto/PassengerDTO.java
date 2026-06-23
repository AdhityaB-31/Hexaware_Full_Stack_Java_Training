package com.hexaware.bus_service.dto;

import lombok.Data;

@Data
public class PassengerDTO {

    private int passengerId;
    private String name;
    private int age;
    private String gender;
    private int busId;
}