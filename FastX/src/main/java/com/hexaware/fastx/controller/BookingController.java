package com.hexaware.fastx.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hexaware.fastx.dto.BookingDto;

import com.hexaware.fastx.service.BookingService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/bookings")

public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping("/create")
    public ResponseEntity<BookingDto> createBooking(@Valid @RequestBody BookingDto bookingDto) {
        return new ResponseEntity<>(bookingService.createBooking(bookingDto), HttpStatus.CREATED);
    }

    @PutMapping("/{bookingId}/confirm")
    public ResponseEntity<BookingDto> confirmBooking(@PathVariable Long bookingId) {
        return new ResponseEntity<>(bookingService.confirmBooking(bookingId), HttpStatus.OK);
    }

    @PutMapping("/{bookingId}/cancel")
    public ResponseEntity<BookingDto> cancelBooking(@PathVariable Long bookingId) {
        return new ResponseEntity<>(bookingService.cancelBooking(bookingId), HttpStatus.OK);
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingDto> getBookingById(@PathVariable Long bookingId) {
        return new ResponseEntity<>(bookingService.getBookingById(bookingId), HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BookingDto>> getBookingsByUser(@PathVariable Long userId) {
        return new ResponseEntity<>(bookingService.getBookingsByUser(userId), HttpStatus.OK);
    }

    @GetMapping("/fare")
    public ResponseEntity<Double> calculateFare(@RequestParam Long busId, @RequestParam int numberOfSeats) {
        return new ResponseEntity<>(bookingService.calculateFare(busId, numberOfSeats), HttpStatus.OK);
    }
    
    @GetMapping("/count/{bookingId}")
    public ResponseEntity<Long> countPassengerByBookingId(@PathVariable Long bookingId){
    	return new ResponseEntity<>(bookingService.countPassengerByBookingId(bookingId),HttpStatus.OK);
    }
}
