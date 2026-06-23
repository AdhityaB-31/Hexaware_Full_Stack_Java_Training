package com.hexaware.fastx.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.hexaware.fastx.dto.SeatDto;
import com.hexaware.fastx.service.SeatService;

// -----------------------------------------------------------
// SeatController  –  role-based access
//
// USER         → reserve seats (during booking)
// BUS_OPERATOR → release seats, view all seats for their bus
// ADMIN        → full access
// Any logged-in user → view available seats
// -----------------------------------------------------------
@RestController
@RequestMapping("/api/seats")
public class SeatController {

    @Autowired
    private SeatService seatService;

    // USER: reserve specific seats for a booking
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping("/reserve/{bookingId}")
    public ResponseEntity<String> reserveSeats(@RequestBody List<Long> seatIds,
                                                @PathVariable Long bookingId) {
        seatService.reserveSeats(seatIds, bookingId);
        return new ResponseEntity<>("Seats reserved successfully for Booking ID: " + bookingId, HttpStatus.OK);
    }

    // BUS_OPERATOR or ADMIN: release seats back to available
    @PreAuthorize("hasAnyRole('BUS_OPERATOR', 'ADMIN')")
    @PostMapping("/release")
    public ResponseEntity<String> releaseSeats(@RequestBody List<Long> seatIds) {
        seatService.releaseSeats(seatIds);
        return new ResponseEntity<>("Seats released successfully", HttpStatus.OK);
    }

    // Any logged-in user: view all seats for a bus
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/bus/{busId}")
    public ResponseEntity<List<SeatDto>> getSeatsByBus(@PathVariable Long busId) {
        return new ResponseEntity<>(seatService.getSeatsByBus(busId), HttpStatus.OK);
    }

    // Any logged-in user: view only available seats for a bus
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/bus/{busId}/available")
    public ResponseEntity<List<SeatDto>> getAvailableSeats(@PathVariable Long busId) {
        return new ResponseEntity<>(seatService.getAvailableSeats(busId), HttpStatus.OK);
    }
}
