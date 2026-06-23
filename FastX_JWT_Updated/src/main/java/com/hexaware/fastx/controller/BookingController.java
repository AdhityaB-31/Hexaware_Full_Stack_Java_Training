package com.hexaware.fastx.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.hexaware.fastx.dto.BookingDto;
import com.hexaware.fastx.service.BookingService;

import jakarta.validation.Valid;

// -----------------------------------------------------------
// BookingController  –  role-based access
//
// USER        → create booking, cancel own booking, view own bookings
// BUS_OPERATOR → view bookings for their buses
// ADMIN       → view all bookings
// -----------------------------------------------------------
@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    // USER: create a new booking
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/create")
    public ResponseEntity<BookingDto> createBooking(@Valid @RequestBody BookingDto bookingDto) {
        return new ResponseEntity<>(bookingService.createBooking(bookingDto), HttpStatus.CREATED);
    }

    // USER or ADMIN: confirm a booking
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PutMapping("/{bookingId}/confirm")
    public ResponseEntity<BookingDto> confirmBooking(@PathVariable Long bookingId) {
        return new ResponseEntity<>(bookingService.confirmBooking(bookingId), HttpStatus.OK);
    }

    // USER: cancel their own booking
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PutMapping("/{bookingId}/cancel")
    public ResponseEntity<BookingDto> cancelBooking(@PathVariable Long bookingId) {
        return new ResponseEntity<>(bookingService.cancelBooking(bookingId), HttpStatus.OK);
    }

    // USER, BUS_OPERATOR or ADMIN: view a specific booking
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingDto> getBookingById(@PathVariable Long bookingId) {
        return new ResponseEntity<>(bookingService.getBookingById(bookingId), HttpStatus.OK);
    }

    // USER or ADMIN: view all bookings for a specific user
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BookingDto>> getBookingsByUser(@PathVariable Long userId) {
        return new ResponseEntity<>(bookingService.getBookingsByUser(userId), HttpStatus.OK);
    }

    // Any logged-in user can calculate the fare before booking
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/fare")
    public ResponseEntity<Double> calculateFare(@RequestParam Long busId, @RequestParam int numberOfSeats) {
        return new ResponseEntity<>(bookingService.calculateFare(busId, numberOfSeats), HttpStatus.OK);
    }
}
