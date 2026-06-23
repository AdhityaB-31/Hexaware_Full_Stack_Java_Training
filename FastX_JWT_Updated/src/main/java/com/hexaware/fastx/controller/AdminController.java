package com.hexaware.fastx.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.hexaware.fastx.dto.BookingDto;
import com.hexaware.fastx.dto.BusOperatorDto;
import com.hexaware.fastx.dto.UserDto;
import com.hexaware.fastx.service.AdminService;

// -----------------------------------------------------------
// AdminController  –  ALL endpoints are ADMIN-only
//
// @PreAuthorize("hasRole('ADMIN')") on the class means every
// method inside requires the caller to have the ADMIN role.
// If a non-admin tries to call any endpoint → 403 Forbidden.
// -----------------------------------------------------------
@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")   // <-- class-level: protects ALL methods below
public class AdminController {

    @Autowired
    private AdminService adminService;

    // View summary statistics (total users, bookings, revenue etc.)
    @GetMapping("/dashboard")
    public ResponseEntity<Map<String, Object>> getDashboardStatistics() {
        return new ResponseEntity<>(adminService.getDashboardStatistics(), HttpStatus.OK);
    }

    // View all users in the system
    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> manageUsers() {
        return new ResponseEntity<>(adminService.manageUsers(), HttpStatus.OK);
    }

    // View all bus operators in the system
    @GetMapping("/operators")
    public ResponseEntity<List<BusOperatorDto>> manageOperators() {
        return new ResponseEntity<>(adminService.manageOperators(), HttpStatus.OK);
    }

    // View all bookings across all users
    @GetMapping("/bookings")
    public ResponseEntity<List<BookingDto>> manageBookings() {
        return new ResponseEntity<>(adminService.manageBookings(), HttpStatus.OK);
    }
}
