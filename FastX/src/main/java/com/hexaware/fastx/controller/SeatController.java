package com.hexaware.fastx.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.hexaware.fastx.dto.SeatDto;
import com.hexaware.fastx.service.SeatService;

@RestController
@RequestMapping("/api/seats")

public class SeatController {

    @Autowired
    private SeatService seatService;

    @PostMapping("/reserve/{bookingId}")
    public ResponseEntity<String> reserveSeats(@RequestBody List<Long> seatIds, @PathVariable Long bookingId) {
        seatService.reserveSeats(seatIds, bookingId);
        return new ResponseEntity<>("Seats reserved successfully for Booking ID: " + bookingId, HttpStatus.OK);
    }

    @PostMapping("/release")
    public ResponseEntity<String> releaseSeats(@RequestBody List<Long> seatIds) {
        seatService.releaseSeats(seatIds);
        return new ResponseEntity<>("Seats released successfully", HttpStatus.OK);
    }

    @GetMapping("/bus/{busId}")
    public ResponseEntity<List<SeatDto>> getSeatsByBus(@PathVariable Long busId) {
        return new ResponseEntity<>(seatService.getSeatsByBus(busId), HttpStatus.OK);
    }

    @GetMapping("/bus/{busId}/available")
    public ResponseEntity<List<SeatDto>> getAvailableSeats(@PathVariable Long busId) {
        return new ResponseEntity<>(seatService.getAvailableSeats(busId), HttpStatus.OK);
    }
}
