package com.hexaware.bus_service.service;

import java.util.List;

import com.hexaware.bus_service.dto.BusDTO;
import com.hexaware.bus_service.dto.BusPassengerVO;
import com.hexaware.bus_service.entity.Bus;

public interface BusService {

	public Bus addBus(BusDTO busDTO);

	public BusDTO getBusById(int busId);

	public List<Bus> getAllBus();
	
	BusPassengerVO getBusWithPassengers(int busId);

//	public   CustomerProductVO   getCustomerAndProductById(int customerId);

//	public void updateProductByCustomer(Product product);

}