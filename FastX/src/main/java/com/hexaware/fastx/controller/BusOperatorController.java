package com.hexaware.fastx.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hexaware.fastx.dto.BusOperatorDto;


import com.hexaware.fastx.service.BusOperatorService;

import jakarta.validation.Valid;
@RestController
@RequestMapping("/api/operators")

public class BusOperatorController {

    @Autowired
    private BusOperatorService busOperatorService;

    @PostMapping("/create")public ResponseEntity<BusOperatorDto> createOperator(@Valid @RequestBody BusOperatorDto operatorDto) {
        return new ResponseEntity<>(busOperatorService.createOperator(operatorDto), HttpStatus.CREATED);
    }

    @PutMapping("/update/{operatorId}")
    public ResponseEntity<BusOperatorDto> updateOperator(@PathVariable Long operatorId, @Valid @RequestBody BusOperatorDto operatorDto) {
        return new ResponseEntity<>(busOperatorService.updateOperator(operatorId, operatorDto), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{operatorId}")
    public ResponseEntity<Void> deleteOperator(@PathVariable Long operatorId) {
        busOperatorService.deleteOperator(operatorId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{operatorId}")
    public ResponseEntity<BusOperatorDto> getOperatorById(@PathVariable Long operatorId) {
        return new ResponseEntity<>(busOperatorService.getOperatorById(operatorId), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<BusOperatorDto>> getAllOperators() {
        return new ResponseEntity<>(busOperatorService.getAllOperators(), HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<BusOperatorDto> getOperatorByEmail(@PathVariable String email) {
        return new ResponseEntity<>(busOperatorService.getOperatorByEmail(email), HttpStatus.OK);
    }

    @GetMapping("/active")
    public ResponseEntity<List<BusOperatorDto>> getActiveOperators() {
        return new ResponseEntity<>(busOperatorService.getActiveOperators(), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<BusOperatorDto>> searchOperatorsByName(@RequestParam String name) {
        return new ResponseEntity<>(busOperatorService.searchOperatorsByName(name), HttpStatus.OK);
    }
}