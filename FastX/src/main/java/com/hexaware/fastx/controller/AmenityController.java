package com.hexaware.fastx.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hexaware.fastx.dto.AmenityDto;

import com.hexaware.fastx.service.AmenityService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/amenities")

public class AmenityController {

    @Autowired
    private AmenityService amenityService;

    @PostMapping("/create")
    public ResponseEntity<AmenityDto> createAmenity(@Valid @RequestBody AmenityDto amenityDto) {
        return new ResponseEntity<>(amenityService.createAmenity(amenityDto), HttpStatus.CREATED);
    }

    @PutMapping("/update/{amenityId}")
    public ResponseEntity<AmenityDto> updateAmenity(@PathVariable Long amenityId,
            @Valid @RequestBody AmenityDto amenityDto) {
        return new ResponseEntity<>(amenityService.updateAmenity(amenityId, amenityDto), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{amenityId}")
    public ResponseEntity<Void> deleteAmenity(@PathVariable Long amenityId) {
        amenityService.deleteAmenity(amenityId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{amenityId}")
    public ResponseEntity<AmenityDto> getAmenityById(@PathVariable Long amenityId) {
        return new ResponseEntity<>(amenityService.getAmenityById(amenityId), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<AmenityDto>> getAllAmenities() {
        return new ResponseEntity<>(amenityService.getAllAmenities(), HttpStatus.OK);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<AmenityDto> getAmenityByName(@PathVariable String name) {
        return new ResponseEntity<>(amenityService.getAmenityByName(name), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<AmenityDto>> searchAmenitiesByName(@RequestParam String name) {
        return new ResponseEntity<>(amenityService.searchAmenitiesByName(name), HttpStatus.OK);
    }
}