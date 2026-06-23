package com.hexaware.fastx.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.hexaware.fastx.dto.BusDto;
import com.hexaware.fastx.dto.SeatDto;
import com.hexaware.fastx.enums.BusType;
import com.hexaware.fastx.service.BusService;

import jakarta.validation.Valid;

// -----------------------------------------------------------
// BusController  –  role-based access per endpoint
//
// PUBLIC (no token)  → search, filter, cheapest buses
// BUS_OPERATOR only  → add, update, delete buses
// Any logged-in user → view bus details, available seats
// -----------------------------------------------------------
@RestController
@RequestMapping("/api/buses")
public class BusController {

    @Autowired
    private BusService busService;

    // ── OPERATOR-ONLY actions ────────────────────────────────

    // Only a bus operator can add a new bus
    @PreAuthorize("hasRole('BUS_OPERATOR')")
    @PostMapping("/create")
    public ResponseEntity<BusDto> addBus(@Valid @RequestBody BusDto busDto) {
        return new ResponseEntity<>(busService.addBus(busDto), HttpStatus.CREATED);
    }

    // Only a bus operator can update bus details
    @PreAuthorize("hasRole('BUS_OPERATOR')")
    @PutMapping("/update/{busId}")
    public ResponseEntity<BusDto> updateBus(@PathVariable Long busId, @Valid @RequestBody BusDto busDto) {
        return new ResponseEntity<>(busService.updateBus(busId, busDto), HttpStatus.OK);
    }

    // Only a bus operator can remove a bus
    @PreAuthorize("hasRole('BUS_OPERATOR')")
    @DeleteMapping("/delete/{busId}")
    public ResponseEntity<Void> deleteBus(@PathVariable Long busId) {
        busService.deleteBus(busId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // ── PUBLIC actions (no token needed) ────────────────────

    // Anyone can search for buses
    @GetMapping("/search")
    public ResponseEntity<List<BusDto>> searchBus(
            @RequestParam String origin,
            @RequestParam String destination,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate journeyDate) {
        return new ResponseEntity<>(busService.searchBus(origin, destination, journeyDate), HttpStatus.OK);
    }

    // Anyone can filter buses by type
    @GetMapping("/type")
    public ResponseEntity<List<BusDto>> getBusesByType(@RequestParam BusType busType) {
        return new ResponseEntity<>(busService.getBusesByType(busType), HttpStatus.OK);
    }

    // Anyone can filter buses by fare range
    @GetMapping("/fare")
    public ResponseEntity<List<BusDto>> getBusesByFare(@RequestParam Double min, @RequestParam Double max) {
        return new ResponseEntity<>(busService.getBusesByFare(min, max), HttpStatus.OK);
    }

    // Anyone can see the cheapest buses
    @GetMapping("/cheapest")
    public ResponseEntity<List<BusDto>> getCheapestBuses() {
        return new ResponseEntity<>(busService.getCheapestBuses(), HttpStatus.OK);
    }

    // ── AUTHENTICATED actions (any logged-in role) ───────────

    // Any logged-in user can view bus details by ID
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{busId}")
    public ResponseEntity<BusDto> getBusDetails(@PathVariable Long busId) {
        return new ResponseEntity<>(busService.getBusDetails(busId), HttpStatus.OK);
    }

    // Any logged-in user can view available seats for a bus
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{busId}/available-seats")
    public ResponseEntity<List<SeatDto>> getAvailableSeats(@PathVariable Long busId) {
        return new ResponseEntity<>(busService.getAvailableSeats(busId), HttpStatus.OK);
    }

    // Any logged-in user can view buses by operator
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/operator/{operatorId}")
    public ResponseEntity<List<BusDto>> getBusesByOperator(@PathVariable Long operatorId) {
        return new ResponseEntity<>(busService.getBusesByOperator(operatorId), HttpStatus.OK);
    }

    // Any logged-in user can search buses by name
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/search/name")
    public ResponseEntity<List<BusDto>> searchBusesByName(@RequestParam String name) {
        return new ResponseEntity<>(busService.searchBusesByName(name), HttpStatus.OK);
    }
}
