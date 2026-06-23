package com.hexaware.springJPA.service;

import java.util.List;

import com.hexaware.springJPA.entity.Bus;

public interface IBusService {

	public Bus addBus(Bus bus);

	public Bus updateBus(Bus bus);

	public Bus getBusById(int busId);

	public void deleteBusById(int busId);

	public List<Bus> getAllBus();

	public List<Bus> getBussByOriginAndDestination(String origin, String destination);

	public List<Bus> getBussBySeatsGreaterThan(int seats);
}