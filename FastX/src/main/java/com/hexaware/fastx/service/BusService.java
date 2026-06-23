package com.hexaware.fastx.service;

import java.time.LocalDate;
import java.util.List;

import com.hexaware.fastx.dto.BusDto;
import com.hexaware.fastx.dto.SeatDto;

public interface BusService {
    BusDto addBus(BusDto busDto);
    BusDto updateBus(Long busId, BusDto busDto);
    void deleteBus(Long busId);
    List<BusDto> searchBus(String origin, String destination, LocalDate journeyDate);
    BusDto getBusDetails(Long busId);
    List<SeatDto> getAvailableSeats(Long busId);
    List<BusDto> getBusesByOperator(Long operatorId);
    List<BusDto> getBusesByType(com.hexaware.fastx.enums.BusType busType);
    List<BusDto> getBusesByFare(Double minFare, Double maxFare);
    List<BusDto> searchBusesByName(String name);
    List<BusDto> getCheapestBuses();
}
