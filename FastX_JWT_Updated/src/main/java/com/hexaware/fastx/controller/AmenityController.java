package com.hexaware.fastx.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.hexaware.fastx.dto.AmenityDto;
import com.hexaware.fastx.service.AmenityService;

import jakarta.validation.Valid;

// -----------------------------------------------------------
// AmenityController  –  role-based access
//
// BUS_OPERATOR / ADMIN → create, update, delete amenities
// Any logged-in user   → view amenities
// -----------------------------------------------------------
@RestController
@RequestMapping("/api/amenities")
public class AmenityController {

    @Autowired
    private AmenityService amenityService;

    // BUS_OPERATOR or ADMIN: add a new amenity (e.g., WiFi, AC)
    @PreAuthorize("hasAnyRole('BUS_OPERATOR', 'ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<AmenityDto> createAmenity(@Valid @RequestBody AmenityDto amenityDto) {
        return new ResponseEntity<>(amenityService.createAmenity(amenityDto), HttpStatus.CREATED);
    }

    // BUS_OPERATOR or ADMIN: update an existing amenity
    @PreAuthorize("hasAnyRole('BUS_OPERATOR', 'ADMIN')")
    @PutMapping("/update/{amenityId}")
    public ResponseEntity<AmenityDto> updateAmenity(@PathVariable Long amenityId,
                                                     @Valid @RequestBody AmenityDto amenityDto) {
        return new ResponseEntity<>(amenityService.updateAmenity(amenityId, amenityDto), HttpStatus.OK);
    }

    // ADMIN-only: delete an amenity
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{amenityId}")
    public ResponseEntity<Void> deleteAmenity(@PathVariable Long amenityId) {
        amenityService.deleteAmenity(amenityId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Any logged-in user: view a specific amenity
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{amenityId}")
    public ResponseEntity<AmenityDto> getAmenityById(@PathVariable Long amenityId) {
        return new ResponseEntity<>(amenityService.getAmenityById(amenityId), HttpStatus.OK);
    }

    // Any logged-in user: view all amenities
    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<List<AmenityDto>> getAllAmenities() {
        return new ResponseEntity<>(amenityService.getAllAmenities(), HttpStatus.OK);
    }

    // Any logged-in user: find amenity by exact name
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/name/{name}")
    public ResponseEntity<AmenityDto> getAmenityByName(@PathVariable String name) {
        return new ResponseEntity<>(amenityService.getAmenityByName(name), HttpStatus.OK);
    }

    // Any logged-in user: search amenities by partial name
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/search")
    public ResponseEntity<List<AmenityDto>> searchAmenitiesByName(@RequestParam String name) {
        return new ResponseEntity<>(amenityService.searchAmenitiesByName(name), HttpStatus.OK);
    }
}
