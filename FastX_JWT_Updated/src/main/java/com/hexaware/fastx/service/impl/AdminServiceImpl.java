package com.hexaware.fastx.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hexaware.fastx.dto.BookingDto;
import com.hexaware.fastx.dto.BusOperatorDto;
import com.hexaware.fastx.dto.UserDto;
import com.hexaware.fastx.entity.Booking;
import com.hexaware.fastx.entity.BusOperator;
import com.hexaware.fastx.entity.User;
import com.hexaware.fastx.enums.BookingStatus;
import com.hexaware.fastx.repository.BookingRepository;
import com.hexaware.fastx.repository.BusOperatorRepository;
import com.hexaware.fastx.repository.UserRepository;
import com.hexaware.fastx.service.AdminService;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {

    private static final Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BusOperatorRepository busOperatorRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Override
    public Map<String, Object> getDashboardStatistics() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalUsers", userRepository.count());
        stats.put("totalOperators", busOperatorRepository.count());
        stats.put("totalBookings", bookingRepository.count());
        stats.put("confirmedBookings", bookingRepository.countByBookingStatus(BookingStatus.CONFIRMED));
        stats.put("cancelledBookings", bookingRepository.countByBookingStatus(BookingStatus.CANCELLED));
        stats.put("pendingBookings", bookingRepository.countByBookingStatus(BookingStatus.PENDING));
        stats.put("popularRoutes", bookingRepository.findPopularRoutes());
        logger.info("Admin dashboard statistics retrieved");
        return stats;
    }

    @Override
    public List<UserDto> manageUsers() {
        List<User> users = userRepository.findAll();
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : users) {
            UserDto dto = new UserDto();
            dto.setUserId(user.getUserId());
            dto.setFullName(user.getFullName());
            dto.setEmail(user.getEmail());
            dto.setPhoneNumber(user.getPhoneNumber());
            dto.setGender(user.getGender() != null ? user.getGender().name() : null);
            dto.setAddress(user.getAddress());
            dto.setIsActive(user.getIsActive());
            dto.setRoleId(user.getRole() != null ? user.getRole().getRoleId() : null);
            userDtos.add(dto);
        }
        return userDtos;
    }

    @Override
    public List<BusOperatorDto> manageOperators() {
        List<BusOperator> operators = busOperatorRepository.findAll();
        List<BusOperatorDto> operatorDtos = new ArrayList<>();
        for (BusOperator operator : operators) {
            BusOperatorDto dto = new BusOperatorDto();
            dto.setOperatorId(operator.getOperatorId());
            dto.setCompanyName(operator.getCompanyName());
            dto.setEmail(operator.getEmail());
            dto.setPhoneNumber(operator.getPhoneNumber());
            dto.setAddress(operator.getAddress());
            dto.setIsActive(operator.getIsActive());
            operatorDtos.add(dto);
        }
        return operatorDtos;
    }

    @Override
    public List<BookingDto> manageBookings() {
        List<Booking> bookings = bookingRepository.findAll();
        List<BookingDto> bookingDtos = new ArrayList<>();
        for (Booking booking : bookings) {
            BookingDto dto = new BookingDto();
            dto.setBookingId(booking.getBookingId());
            dto.setBookingDate(booking.getBookingDate());
            dto.setBookingStatus(booking.getBookingStatus() != null ? booking.getBookingStatus().name() : null);
            dto.setTotalAmount(booking.getTotalAmount());
            dto.setUserId(booking.getUser() != null ? booking.getUser().getUserId() : null);
            dto.setBusId(booking.getBus() != null ? booking.getBus().getBusId() : null);
            bookingDtos.add(dto);
        }
        return bookingDtos;
    }
}
