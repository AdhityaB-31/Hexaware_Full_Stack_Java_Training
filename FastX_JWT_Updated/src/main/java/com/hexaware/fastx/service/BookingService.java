package com.hexaware.fastx.service;

import java.util.List;

import com.hexaware.fastx.dto.BookingDto;

public interface BookingService {
    BookingDto createBooking(BookingDto bookingDto);
    BookingDto confirmBooking(Long bookingId);
    BookingDto cancelBooking(Long bookingId);
    BookingDto getBookingById(Long bookingId);
    List<BookingDto> getBookingsByUser(Long userId);
    Double calculateFare(Long busId, int numberOfSeats);
}
