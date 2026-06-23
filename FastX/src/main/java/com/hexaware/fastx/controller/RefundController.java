package com.hexaware.fastx.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hexaware.fastx.dto.RefundDto;
import com.hexaware.fastx.service.RefundService;

@RestController
@RequestMapping("/api/refunds")

public class RefundController {

    @Autowired
    private RefundService refundService;

    @PostMapping("/booking/{bookingId}")
    public ResponseEntity<RefundDto> createRefund(@PathVariable Long bookingId) {
        return new ResponseEntity<>(refundService.createRefund(bookingId), HttpStatus.CREATED);
    }

    @PutMapping("/{refundId}/approve")
    public ResponseEntity<RefundDto> approveRefund(@PathVariable Long refundId) {
        return new ResponseEntity<>(refundService.approveRefund(refundId), HttpStatus.OK);
    }

    @GetMapping("/{refundId}")
    public ResponseEntity<RefundDto> getRefundStatus(@PathVariable Long refundId) {
        return new ResponseEntity<>(refundService.getRefundStatus(refundId), HttpStatus.OK);
    }

    @GetMapping("/status")
    public ResponseEntity<List<RefundDto>> getRefundsByStatus(@RequestParam com.hexaware.fastx.enums.RefundStatus status) {
        return new ResponseEntity<>(refundService.getRefundsByStatus(status), HttpStatus.OK);
    }
}