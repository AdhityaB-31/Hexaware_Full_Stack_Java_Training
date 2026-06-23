package com.hexaware.fastx.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.fastx.dto.BusDto;
import com.hexaware.fastx.dto.SeatDto;
import com.hexaware.fastx.enums.BusType;
import com.hexaware.fastx.service.BusService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/buses")
public class BusController {

    @Autowired
    private BusService busService;

    @PostMapping("/create")
    public ResponseEntity<BusDto> addBus(@Valid @RequestBody BusDto busDto) {
        return new ResponseEntity<>(busService.addBus(busDto), HttpStatus.CREATED);
    }

    @PutMapping("/update/{busId}")
    public ResponseEntity<BusDto> updateBus(@PathVariable Long busId, @Valid @RequestBody BusDto busDto) {
        return new ResponseEntity<>(busService.updateBus(busId, busDto), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{busId}")
    public ResponseEntity<Void> deleteBus(@PathVariable Long busId) {
        busService.deleteBus(busId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/search")
    public ResponseEntity<List<BusDto>> searchBus(
            @RequestParam String origin,
            @RequestParam String destination,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate journeyDate) {
        return new ResponseEntity<>(busService.searchBus(origin, destination, journeyDate), HttpStatus.OK);
    }

    @GetMapping("/{busId}")
    public ResponseEntity<BusDto> getBusDetails(@PathVariable Long busId) {
        return new ResponseEntity<>(busService.getBusDetails(busId), HttpStatus.OK);
    }

    @GetMapping("/{busId}/available-seats")
    public ResponseEntity<List<SeatDto>> getAvailableSeats(@PathVariable Long busId) {
        return new ResponseEntity<>(busService.getAvailableSeats(busId), HttpStatus.OK);
    }

    @GetMapping("/operator/{operatorId}")
    public ResponseEntity<List<BusDto>> getBusesByOperator(@PathVariable Long operatorId) {
        return new ResponseEntity<>(busService.getBusesByOperator(operatorId), HttpStatus.OK);
    }

    @GetMapping("/type")
    public ResponseEntity<List<BusDto>> getBusesByType(@RequestParam BusType busType) {
        return new ResponseEntity<>(busService.getBusesByType(busType), HttpStatus.OK);
    }

    @GetMapping("/fare")
    public ResponseEntity<List<BusDto>> getBusesByFare(@RequestParam Double min, @RequestParam Double max) {
        return new ResponseEntity<>(busService.getBusesByFare(min, max), HttpStatus.OK);
    }

    @GetMapping("/search/name")
    public ResponseEntity<List<BusDto>> searchBusesByName(@RequestParam String name) {
        return new ResponseEntity<>(busService.searchBusesByName(name), HttpStatus.OK);
    }

    @GetMapping("/cheapest")
    public ResponseEntity<List<BusDto>> getCheapestBuses() {
        return new ResponseEntity<>(busService.getCheapestBuses(), HttpStatus.OK);
    }
}