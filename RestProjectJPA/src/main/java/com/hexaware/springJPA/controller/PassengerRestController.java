package com.hexaware.springJPA.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.springJPA.PassengerDTO;
import com.hexaware.springJPA.entity.Passenger;
import com.hexaware.springJPA.service.IPassengerService;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("app/passenger")
@Log4j2
public class PassengerRestController {

	@Autowired
	IPassengerService service;

	@PostMapping("/add")
	public Passenger createPassenger(@RequestBody PassengerDTO dto) {
		log.info("Passenger Added successfully");
		return service.addPassenger(dto);

	}

	@PutMapping("/update")
	public Passenger updatePassenger(@RequestBody PassengerDTO dto) {
		log.info("Passenger Updated successfully...");
		return service.updatePassenger(dto);

	}

	@GetMapping("/getbyeid/{passengerId}")
	public PassengerDTO getEmployeeByEid(@PathVariable int passengerId) {

		log.debug("Passenger is recovered by Passenger ID");

		return service.getByPassengerid(passengerId);

	}

	@GetMapping("/getall")
	public List<Passenger> getAll() {

		log.info("All data are fetched successfully...");
		return service.getAllPassenger();

	}

	@DeleteMapping("/deletebypassengerid/{passengerId}")
	public ResponseEntity<String> deleteByPassengerId(@PathVariable int passengerId) {

		log.warn("Passenger Record is ready to delete.....");

		service.deleteByPassengerid(passengerId);

		return new ResponseEntity<String>("Record Deleted successfully", HttpStatus.ACCEPTED);

	}

	@GetMapping("/getbyname/{passengerName}")
	public Passenger getByName(@PathVariable String passengerName) {
		log.debug("Passenger is recovered by Passenger Name");
		return service.findByPassengerName(passengerName);
	}

	@GetMapping("/getbyage/{age}")
	public List<Passenger> getByAge(@PathVariable int age) {
		log.debug("Passenger is recovered by Passenger Age greater than");
		return service.findByAgeGreaterThan(age);
	}

	@GetMapping("/getbynamelike/{passengerName}")
	public List<Passenger> getByNameLike(@PathVariable String passengerName) {
		log.debug("Passenger is recovered by Passenger name using containing framer method");
		return service.findByPassengerNameLike(passengerName);
	}

	@GetMapping("/sortbyage/{age}")
	public List<Passenger> getSortByAge(@PathVariable int age) {
		log.info("Passenger is recovered by Passenger AGE and also sorted by age");
		return service.getAllSortedByAge(age);
	}

	@PutMapping("/updateAge/{passengerId}/{age}")
	public String updateAge(@PathVariable int passengerId, @PathVariable int age) {
		log.info("Passenger record is updated passenger's age");
		service.updatePassengerAge(passengerId, age);
		return "Record updated...";
	}

	@GetMapping("/getallagelt/{age}")
	public List<Passenger> getAllAgeLT(@PathVariable int age) {
		return service.getAllAgeLT(age);
	}

	@PutMapping("/updateName/{passengerId}/{passengerName}")
	public String updateName(@PathVariable int passengerId, @PathVariable String passengerName) {
		log.info("Update Request is Send to Service Layer...");
		service.updateName(passengerId, passengerName);
		return "Record Modefied...";
	}

	@GetMapping("/findbyidgt/{passengerId}")
	public List<Passenger> findByPassengerIdGreaterThan(@PathVariable int passengerId) {
		return service.findByPassengerIdGreaterThan(passengerId);
	}

	@GetMapping("/findbyidbet/{initial}/{fin}")
	public List<Passenger> findByPassengerIdBetween(@PathVariable int initial, @PathVariable int fin) {
		log.info("Passenger is recovered by Passenger ID between initial and final value");
		return service.findByPassengerIdBetween(initial, fin);
	}

}
