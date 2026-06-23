package com.hexaware.springboot.rest.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.springboot.rest.entity.Passenger;

@RestController
@RequestMapping("api/passengers")
public class PassengerRestController {
	
	
	static List<Passenger> list = new ArrayList<Passenger>();

	static {

		list.add(new Passenger(101, "Adhitya",21,"MALE"));
		list.add(new Passenger(102, "Adhithyan",21,"MALE"));
		list.add(new Passenger(103, "Sriram",21,"MALE"));
		list.add(new Passenger(104, "Sree",21,"FEMALE"));
		

	}

	@GetMapping("/hello")
	public String helloEmployee() {
		return "Hello Passengers";
	}
	
	@PostMapping(value="/add",consumes="application/json")
	public Passenger addPassenger(@RequestParam Passenger pr) {
		list.add(pr);
		return pr;
	}
	
	@PutMapping(value = "/update", consumes = "application/json")
	public Passenger updateEmployee(@RequestBody Passenger pr) {

		list.add(pr);

		return pr;

	}

	@GetMapping("/getall")
	public List<Passenger> getAll() {

		return list;

	}

	@GetMapping("/getbyid/{pid}")
	public List<Passenger> getById(@PathVariable int pid) {

		return list.stream().filter((pr) -> {
			return pr.getPassengerId() == pid;
		}).toList();

	}

	@DeleteMapping("/deletebyid/{eid}")
	public String deleteById(@PathVariable int pid) {

		Iterator<Passenger> it = list.iterator();

		int i = 0;

		while (it.hasNext()) {

			Passenger pr = it.next();

			if (pr.getPassengerId() == pid) {

				list.remove(i);

				break;

			}

			i++;

		}
	
		return "record deleted...";
	
	}
}
