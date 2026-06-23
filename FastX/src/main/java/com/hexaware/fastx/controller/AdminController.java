package com.hexaware.fastx.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.hexaware.fastx.dto.BookingDto;
import com.hexaware.fastx.dto.BusOperatorDto;
import com.hexaware.fastx.dto.UserDto;
import com.hexaware.fastx.service.AdminService;

@RestController
@RequestMapping("/api/admin")

public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/dashboard")
    public ResponseEntity<Map<String, Object>> getDashboardStatistics() {
        return new ResponseEntity<>(adminService.getDashboardStatistics(), HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> manageUsers() {
        return new ResponseEntity<>(adminService.manageUsers(), HttpStatus.OK);
    }

    @GetMapping("/operators")
    public ResponseEntity<List<BusOperatorDto>> manageOperators() {
        return new ResponseEntity<>(adminService.manageOperators(), HttpStatus.OK);
    }

    @GetMapping("/bookings")
    public ResponseEntity<List<BookingDto>> manageBookings() {
        return new ResponseEntity<>(adminService.manageBookings(), HttpStatus.OK);
    }
}
