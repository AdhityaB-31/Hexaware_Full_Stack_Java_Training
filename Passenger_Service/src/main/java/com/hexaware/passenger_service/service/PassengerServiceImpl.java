package com.hexaware.passenger_service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.passenger_service.dto.PassengerDTO;
import com.hexaware.passenger_service.entity.Passenger;
import com.hexaware.passenger_service.repository.PassengerRepository;

@Service
public class PassengerServiceImpl implements PassengerService {

    @Autowired
    private PassengerRepository passengerRepository;

    @Override
    public Passenger addPassenger(PassengerDTO passengerDTO) {

        Passenger passenger = new Passenger();

        passenger.setName(passengerDTO.getName());
        passenger.setAge(passengerDTO.getAge());
        passenger.setGender(passengerDTO.getGender());
        passenger.setBusId(passengerDTO.getBusId());

        return passengerRepository.save(passenger);
    }

    @Override
    public Passenger updatePassenger(PassengerDTO passengerDTO) {

        Passenger passenger = passengerRepository
                .findById(passengerDTO.getPassengerId())
                .orElseThrow(() -> new RuntimeException("Passenger Not Found"));

        passenger.setName(passengerDTO.getName());
        passenger.setAge(passengerDTO.getAge());
        passenger.setGender(passengerDTO.getGender());
        passenger.setBusId(passengerDTO.getBusId());

        return passengerRepository.save(passenger);
    }

    @Override
    public List<Passenger> getAll() {

        return passengerRepository.findAll();
    }

    @Override
    public List<Passenger> findByBusId(int busId) {

        return passengerRepository.findByBusId(busId);
    }
}