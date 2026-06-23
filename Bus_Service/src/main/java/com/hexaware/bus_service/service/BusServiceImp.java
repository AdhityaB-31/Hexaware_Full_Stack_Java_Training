package com.hexaware.bus_service.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.hexaware.bus_service.dto.BusDTO;
import com.hexaware.bus_service.dto.BusPassengerVO;
import com.hexaware.bus_service.dto.PassengerDTO;
import com.hexaware.bus_service.entity.Bus;
import com.hexaware.bus_service.repository.BusRepository;

@Service
public class BusServiceImp implements BusService {
	
	@Autowired
	BusRepository repo;

	@Autowired
	RestTemplate restTemplate;

	@Override
	public Bus addBus(BusDTO busDTO) {
		Bus bus = new Bus();
		bus.setBusId(busDTO.getBusId());
		bus.setBusName(busDTO.getBusName());
		bus.setBusNumber(busDTO.getBusNumber());
		bus.setFare(busDTO.getFare());
		

		return repo.save(bus);
	}

	@Override
	public BusDTO getBusById(int busId) {
		Bus bus = repo.findByBusId(busId);

		BusDTO dto = new BusDTO();
		dto.setBusId(bus.getBusId());
		dto.setBusName(bus.getBusName());
		dto.setBusNumber(bus.getBusNumber());
		dto.setFare(bus.getFare());
		

		return dto;
	}

	@Override
	public List<Bus> getAllBus() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}
	
	@Override
	public BusPassengerVO getBusWithPassengers(int busId) {

	    Bus bus = repo.findByBusId(busId);

	    BusDTO busDTO = new BusDTO();
	    busDTO.setBusId(bus.getBusId());
	    busDTO.setBusName(bus.getBusName());
	    busDTO.setBusNumber(bus.getBusNumber());
	    busDTO.setFare(bus.getFare());

	    String url =
	            "http://localhost:8082/api/passengers/bus/" + busId;

	    PassengerDTO[] passengerArray =
	            restTemplate.getForObject(
	                    url,
	                    PassengerDTO[].class);

	    BusPassengerVO response =
	            new BusPassengerVO();
	    

	    response.setBus(busDTO);
	    response.setPassengers(Arrays.asList(passengerArray));

	    return response;
	}

	
}
