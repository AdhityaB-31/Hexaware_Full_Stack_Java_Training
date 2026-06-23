package com.hexaware.fastx.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hexaware.fastx.dto.BookingDto;
import com.hexaware.fastx.dto.PassengerDto;
import com.hexaware.fastx.entity.Booking;
import com.hexaware.fastx.entity.Bus;
import com.hexaware.fastx.entity.Passenger;
import com.hexaware.fastx.entity.Payment;
import com.hexaware.fastx.entity.Seat;
import com.hexaware.fastx.entity.User;
import com.hexaware.fastx.enums.BookingStatus;
import com.hexaware.fastx.enums.Gender;
import com.hexaware.fastx.enums.PaymentStatus;
import com.hexaware.fastx.enums.SeatStatus;
import com.hexaware.fastx.exception.BookingException;
import com.hexaware.fastx.exception.BusNotFoundException;
import com.hexaware.fastx.exception.ResourceNotFoundException;
import com.hexaware.fastx.exception.SeatNotAvailableException;
import com.hexaware.fastx.repository.BookingRepository;
import com.hexaware.fastx.repository.BusRepository;
import com.hexaware.fastx.repository.PassengerRepository;
import com.hexaware.fastx.repository.PaymentRepository;
import com.hexaware.fastx.repository.SeatRepository;
import com.hexaware.fastx.repository.UserRepository;
import com.hexaware.fastx.service.BookingService;
import com.hexaware.fastx.service.SeatService;


@Service
@Transactional
public class BookingServiceImpl implements BookingService {

    // Logger using Log4j (SLF4J) - creates a logger for this class
    private static final Logger logger = LoggerFactory.getLogger(BookingServiceImpl.class);

    // Reservation timeout in minutes - seats are held for 30 minutes waiting for payment
    private static final int RESERVATION_TIMEOUT_MINUTES = 30;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BusRepository busRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private PassengerRepository passengerRepository;

    @Autowired
    private SeatService seatService;

    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    @Transactional
    public BookingDto createBooking(BookingDto bookingDto) {
        logger.info("Creating booking for User ID: {} on Bus ID: {}", bookingDto.getUserId(), bookingDto.getBusId());

        // Step 1: Find the user who is making the booking
        User user = userRepository.findById(bookingDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + bookingDto.getUserId()));

        // Step 2: Find the bus for this booking
        Bus bus = busRepository.findById(bookingDto.getBusId())
                .orElseThrow(() -> new BusNotFoundException("Bus not found with ID: " + bookingDto.getBusId()));

        // Step 3: Validate passengers and their seats
        int numberOfPassengers = bookingDto.getPassengers().size();
        logger.info("Number of passengers: {}", numberOfPassengers);

        // Validate all seats exist and belong to the same bus, and are available (not reserved or booked)
        for (PassengerDto pDto : bookingDto.getPassengers()) {
            Seat seat = seatRepository.findById(pDto.getSeatId())
                    .orElseThrow(() -> new ResourceNotFoundException("Seat not found with ID: " + pDto.getSeatId()));

            // Check seat belongs to the same bus
            if (!seat.getBus().getBusId().equals(bookingDto.getBusId())) {
                throw new BookingException("Seat " + seat.getSeatNumber() + " does not belong to Bus ID: " + bookingDto.getBusId());
            }

            // Check seat is available - reject if RESERVED (held by another user) or BOOKED (already confirmed)
            if (seat.getSeatStatus() == SeatStatus.RESERVED) {
                throw new SeatNotAvailableException("Seat " + seat.getSeatNumber() + " is currently reserved by another user. Please choose a different seat.");
            }
            if (seat.getSeatStatus() == SeatStatus.BOOKED) {
                throw new SeatNotAvailableException("Seat " + seat.getSeatNumber() + " is already booked");
            }
        }

        // Step 4: Check if enough available seats on the bus
        Long availableCount = seatRepository.countSeatsByBusIdAndStatus(bus.getBusId(), SeatStatus.AVAILABLE);
        if (availableCount < numberOfPassengers) {
            throw new SeatNotAvailableException("Not enough seats available. Requested: " + numberOfPassengers + ", Available: " + availableCount);
        }

        // Step 5: Auto-calculate the total amount (fare × number of passengers)
        Double totalAmount = bus.getFare() * numberOfPassengers;
        logger.info("Total amount calculated: {} (fare {} x {} passengers)", totalAmount, bus.getFare(), numberOfPassengers);

        // Step 6: Create the booking with auto-generated ID (user does NOT provide booking ID)
        Booking booking = new Booking();
        booking.setBookingDate(LocalDate.now());
        booking.setBookingStatus(BookingStatus.PENDING);
        booking.setTotalAmount(totalAmount);
        booking.setUser(user);
        booking.setBus(bus);

        // Step 7: Set reservation expiry - seats are RESERVED for 30 minutes
        // User must complete payment within this window, otherwise seats are released
        LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(RESERVATION_TIMEOUT_MINUTES);
        booking.setReservationExpiresAt(expiresAt);
        logger.info("Reservation expires at: {} (30-minute window)", expiresAt);

        Booking savedBooking = bookingRepository.save(booking);
        logger.info("Booking saved with auto-generated ID: {}. User will use this ID to complete payment.", savedBooking.getBookingId());

        // Step 8: Reserve seats (mark as RESERVED, NOT BOOKED yet)
        // Seats will change to BOOKED only after payment is confirmed
        List<Long> seatIds = new ArrayList<>();
        for (PassengerDto pDto : bookingDto.getPassengers()) {
            seatIds.add(pDto.getSeatId());
        }

        // Reserve all seats and link them to this booking
        seatService.reserveSeats(seatIds, savedBooking.getBookingId());

        // Step 9: Create passenger records for each passenger
        List<Passenger> passengers = new ArrayList<>();
        for (PassengerDto pDto : bookingDto.getPassengers()) {
            Passenger passenger = new Passenger();
            passenger.setName(pDto.getName());
            passenger.setAge(pDto.getAge());
            if (pDto.getGender() != null) {
                passenger.setGender(Gender.valueOf(pDto.getGender()));
            }
            Seat seat = seatRepository.findById(pDto.getSeatId())
                    .orElseThrow(() -> new ResourceNotFoundException("Seat not found with ID: " + pDto.getSeatId()));
            passenger.setSeat(seat);
            passenger.setBooking(savedBooking);

            // Save each passenger individually so IdGeneratorService sees it for next gap-fill lookup
            Passenger savedPassenger = passengerRepository.save(passenger);
            passengers.add(savedPassenger);
            logger.info("Passenger added: {} (Age: {}) -> Seat: {}", pDto.getName(), pDto.getAge(), seat.getSeatNumber());
        }
        savedBooking.setPassengers(passengers);

        // Step 10: Create pending payment record
        Payment payment = new Payment();
        payment.setAmount(totalAmount);
        payment.setPaymentStatus(PaymentStatus.PENDING);
        payment.setBooking(savedBooking);
        paymentRepository.save(payment);
        logger.info("Pending payment record created for Booking ID: {}", savedBooking.getBookingId());

        logger.info("Booking created successfully! Booking ID: {} | Status: PENDING | Seats: RESERVED for 30 mins | Complete payment to confirm.",
                savedBooking.getBookingId());
        return mapToDto(savedBooking);
    }

    @Override
    @Transactional
    public BookingDto confirmBooking(Long bookingId) {
        logger.info("Confirming booking with ID: {}", bookingId);

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with ID: " + bookingId));

        // Can only confirm a PENDING booking
        if (booking.getBookingStatus() != BookingStatus.PENDING) {
            logger.warn("Cannot confirm booking ID: {}. Current status: {}", bookingId, booking.getBookingStatus());
            throw new BookingException("Booking cannot be confirmed. Current status: " + booking.getBookingStatus());
        }

        // Check if reservation has expired
        if (booking.getReservationExpiresAt() != null && LocalDateTime.now().isAfter(booking.getReservationExpiresAt())) {
            logger.warn("Reservation expired for Booking ID: {}. Expiry was: {}", bookingId, booking.getReservationExpiresAt());
            // Auto-expire the booking and release seats
            expireBooking(booking);
            throw new BookingException("Reservation has expired. The 30-minute payment window has passed. Seats have been released. Please create a new booking.");
        }

        // Change booking status to CONFIRMED
        booking.setBookingStatus(BookingStatus.CONFIRMED);

        // Change seats from RESERVED -> BOOKED (now permanently booked)
        if (booking.getPassengers() != null && !booking.getPassengers().isEmpty()) {
            List<Long> seatIds = new ArrayList<>();
            for (Passenger p : booking.getPassengers()) {
                seatIds.add(p.getSeat().getSeatId());
            }
            seatService.confirmSeats(seatIds);
            logger.info("Seats confirmed (RESERVED -> BOOKED) for Booking ID: {}", bookingId);
        }

        Booking confirmed = bookingRepository.save(booking);
        logger.info("Booking confirmed successfully. Booking ID: {} | Seats are now permanently BOOKED.", confirmed.getBookingId());
        return mapToDto(confirmed);
    }

    @Override
    @Transactional
    public BookingDto cancelBooking(Long bookingId) {
        logger.info("Cancelling booking with ID: {}", bookingId);

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with ID: " + bookingId));

        // Cannot cancel an already cancelled or expired booking
        if (booking.getBookingStatus() == BookingStatus.CANCELLED) {
            logger.warn("Booking ID: {} is already cancelled", bookingId);
            throw new BookingException("Booking is already cancelled");
        }
        if (booking.getBookingStatus() == BookingStatus.EXPIRED) {
            logger.warn("Booking ID: {} is already expired", bookingId);
            throw new BookingException("Booking has already expired. Seats were auto-released.");
        }

        booking.setBookingStatus(BookingStatus.CANCELLED);

        // Release the seats when booking is cancelled (whether RESERVED or BOOKED)
        if (booking.getPassengers() != null && !booking.getPassengers().isEmpty()) {
            List<Long> seatIds = new ArrayList<>();
            for (Passenger p : booking.getPassengers()) {
                seatIds.add(p.getSeat().getSeatId());
            }
            seatService.releaseSeats(seatIds);
            logger.info("Released {} seats for cancelled Booking ID: {}", seatIds.size(), bookingId);
        }

        Booking cancelled = bookingRepository.save(booking);
        logger.info("Booking cancelled successfully. Booking ID: {}", cancelled.getBookingId());
        return mapToDto(cancelled);
    }

    @Override
    @Transactional(readOnly = true)
    public BookingDto getBookingById(Long bookingId) {
        logger.info("Fetching booking with ID: {}", bookingId);
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with ID: " + bookingId));
        return mapToDto(booking);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookingDto> getBookingsByUser(Long userId) {
        logger.info("Fetching bookings for User ID: {}", userId);
        List<Booking> bookings = bookingRepository.findBookingsByUserId(userId);
        List<BookingDto> bookingDtos = new ArrayList<>();

        for (Booking booking : bookings) {
            bookingDtos.add(mapToDto(booking));
        }

        logger.info("Found {} bookings for User ID: {}", bookingDtos.size(), userId);
        return bookingDtos;
    }

    @Override
    public Double calculateFare(Long busId, int numberOfSeats) {
        Bus bus = busRepository.findById(busId)
                .orElseThrow(() -> new BusNotFoundException("Bus not found with ID: " + busId));
        Double fare = bus.getFare() * numberOfSeats;
        logger.info("Fare calculated: {} for {} seats on Bus ID: {}", fare, numberOfSeats, busId);
        return fare;
    }

    /**
     * Expires a booking and releases its reserved seats.
     * Called when a user tries to confirm/pay after the 30-minute window has passed.
     */
    @Transactional
    public void expireBooking(Booking booking) {
        logger.info("Expiring Booking ID: {} - reservation window has passed", booking.getBookingId());
        booking.setBookingStatus(BookingStatus.EXPIRED);

        // Release all reserved seats back to AVAILABLE
        if (booking.getPassengers() != null && !booking.getPassengers().isEmpty()) {
            List<Long> seatIds = new ArrayList<>();
            for (Passenger p : booking.getPassengers()) {
                seatIds.add(p.getSeat().getSeatId());
            }
            seatService.releaseSeats(seatIds);
            logger.info("Released {} seats for expired Booking ID: {}", seatIds.size(), booking.getBookingId());
        }

        bookingRepository.save(booking);
    }

    private BookingDto mapToDto(Booking booking) {
        BookingDto dto = new BookingDto();
        dto.setBookingId(booking.getBookingId());
        dto.setBookingDate(booking.getBookingDate());
        dto.setBookingStatus(booking.getBookingStatus() != null ? booking.getBookingStatus().name() : null);
        dto.setReservationExpiresAt(booking.getReservationExpiresAt());
        dto.setTotalAmount(booking.getTotalAmount());
        dto.setUserId(booking.getUser() != null ? booking.getUser().getUserId() : null);
        dto.setBusId(booking.getBus() != null ? booking.getBus().getBusId() : null);

        // Map passengers if present
        if (booking.getPassengers() != null) {
            List<PassengerDto> passengerDtos = new ArrayList<>();
            for (Passenger p : booking.getPassengers()) {
                PassengerDto pDto = new PassengerDto();
                pDto.setPassengerId(p.getPassengerId());
                pDto.setName(p.getName());
                pDto.setAge(p.getAge());
                pDto.setGender(p.getGender() != null ? p.getGender().name() : null);
                pDto.setSeatId(p.getSeat() != null ? p.getSeat().getSeatId() : null);
                pDto.setBookingId(booking.getBookingId());
                passengerDtos.add(pDto);
            }
            dto.setPassengers(passengerDtos);
        }

        return dto;
    }
}
