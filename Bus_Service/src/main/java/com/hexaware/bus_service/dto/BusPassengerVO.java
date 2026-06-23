package com.hexaware.bus_service.dto;

import java.util.List;

import lombok.Data;

@Data
public class BusPassengerVO {

    private BusDTO bus;
    private List<PassengerDTO> passengers;
}