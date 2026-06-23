package com.hexaware.fastx.service;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.hexaware.fastx.dto.BookingDto;
import com.hexaware.fastx.dto.BusDto;
import com.hexaware.fastx.dto.BusOperatorDto;
import com.hexaware.fastx.dto.PassengerDto;
import com.hexaware.fastx.dto.RoleDto;
import com.hexaware.fastx.dto.SeatDto;
import com.hexaware.fastx.dto.UserDto;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class BookingServiceTest {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private UserService userService;

    @Autowired
    private BusService busService;

    @Autowired
    private BusOperatorService busOperatorService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private SeatService seatService;

    // Creates sample data required for booking tests
    private Long[] createTestData() {

        // Create Role
        RoleDto role = new RoleDto();
        role.setRoleName("ROLE_USER");
        RoleDto savedRole = roleService.createRole(role);

        // Create User
        UserDto user = new UserDto();
        user.setFullName("Adhitya");
        user.setEmail("adhitya@fastx.com");
        user.setPassword("password123");
        user.setPhoneNumber("9876543210");
        user.setGender("MALE");
        user.setAddress("Puducherry");
        user.setIsActive(true);
        user.setRoleId(savedRole.getRoleId());

        UserDto savedUser = userService.createUser(user);

        // Create Bus Operator
        BusOperatorDto operator = new BusOperatorDto();
        operator.setCompanyName("FastX Travels");
        operator.setEmail("operator@fastx.com");
        operator.setPassword("operator123");
        operator.setPhoneNumber("9876543211");
        operator.setAddress("Puducherry");

        BusOperatorDto savedOperator = busOperatorService.createOperator(operator);

        // Create Bus
        BusDto bus = new BusDto();
        bus.setBusName("FastX Express");
        bus.setBusNumber("TN01FX1001");
        bus.setBusType("AC_SLEEPER");
        bus.setOrigin("Puducherry");
        bus.setDestination("Chennai");
        bus.setJourneyDate(LocalDate.of(2026, 12, 1));
        bus.setDepartureTime(LocalTime.of(21, 0));
        bus.setArrivalTime(LocalTime.of(5, 0));
        bus.setFare(500.0);
        bus.setTotalSeats(40);
        bus.setOperatorId(savedOperator.getOperatorId());

        BusDto savedBus = busService.addBus(bus);

        return new Long[] {
                savedUser.getUserId(),
                savedBus.getBusId()
        };
    }

    // Creates a booking with one passenger
    private BookingDto createBookingDto(Long userId, Long busId) {

        List<SeatDto> availableSeats = seatService.getAvailableSeats(busId);

        PassengerDto passenger = new PassengerDto();
        passenger.setName("Buddy");
        passenger.setAge(21);
        passenger.setGender("MALE");
        passenger.setSeatId(
                availableSeats.get(0).getSeatId());

        List<PassengerDto> passengers = new ArrayList<>();
        passengers.add(passenger);

        BookingDto booking = new BookingDto();
        booking.setUserId(userId);
        booking.setBusId(busId);
        booking.setPassengers(passengers);

        return booking;
    }

    @Test
    void testCreateBooking() {

        Long[] ids = createTestData();

        BookingDto booking = createBookingDto(ids[0], ids[1]);

        BookingDto result = bookingService.createBooking(booking);

        assertNotNull(result);
        assertNotNull(result.getBookingId());

        assertEquals(
                "PENDING",
                result.getBookingStatus());

        assertEquals(
                500.0,
                result.getTotalAmount());

        assertEquals(
                1,
                result.getPassengers().size());

        assertNotNull(
                result.getReservationExpiresAt());
    }

    @Test
    void testGetBookingById() {

        Long[] ids = createTestData();

        BookingDto savedBooking = bookingService.createBooking(
                createBookingDto(ids[0], ids[1]));

        BookingDto result = bookingService.getBookingById(
                savedBooking.getBookingId());

        assertNotNull(result);

        assertEquals(
                savedBooking.getBookingId(),
                result.getBookingId());

        assertNotNull(
                result.getReservationExpiresAt());
    }

    @Test
    void testGetBookingsByUser() {

        Long[] ids = createTestData();

        bookingService.createBooking(
                createBookingDto(ids[0], ids[1]));

        List<BookingDto> bookings = bookingService.getBookingsByUser(ids[0]);

        assertNotNull(bookings);

        assertTrue(
                bookings.size() > 0);
    }

    @Test
    void testCalculateFare() {

        Long[] ids = createTestData();

        Double fare = bookingService.calculateFare(
                ids[1],
                2);

        assertEquals(
                1000.0,
                fare);
    }
    @Test
    void testConfirmBooking() {

        Long[] ids = createTestData();

        BookingDto savedBooking = bookingService.createBooking(
                createBookingDto(ids[0], ids[1]));

        BookingDto confirmedBooking = bookingService.confirmBooking(savedBooking.getBookingId());

        assertNotNull(confirmedBooking);
        assertEquals("CONFIRMED", confirmedBooking.getBookingStatus());
    }

    @Test
    void testCancelBooking() {

        Long[] ids = createTestData();

        BookingDto savedBooking = bookingService.createBooking(
                createBookingDto(ids[0], ids[1]));

        BookingDto cancelledBooking = bookingService.cancelBooking(savedBooking.getBookingId());

        assertNotNull(cancelledBooking);
        assertEquals("CANCELLED", cancelledBooking.getBookingStatus());
    }
}