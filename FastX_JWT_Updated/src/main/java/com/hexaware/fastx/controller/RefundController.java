package com.hexaware.fastx.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.hexaware.fastx.dto.RefundDto;
import com.hexaware.fastx.enums.RefundStatus;
import com.hexaware.fastx.service.RefundService;

// -----------------------------------------------------------
// RefundController  –  role-based access
//
// USER   → request a refund for their cancelled booking
// ADMIN  → approve/reject refunds, view all refunds by status
// -----------------------------------------------------------
@RestController
@RequestMapping("/api/refunds")
public class RefundController {

    @Autowired
    private RefundService refundService;

    // USER: request a refund after cancelling a booking
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping("/booking/{bookingId}")
    public ResponseEntity<RefundDto> createRefund(@PathVariable Long bookingId) {
        return new ResponseEntity<>(refundService.createRefund(bookingId), HttpStatus.CREATED);
    }

    // ADMIN-only: approve a pending refund
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{refundId}/approve")
    public ResponseEntity<RefundDto> approveRefund(@PathVariable Long refundId) {
        return new ResponseEntity<>(refundService.approveRefund(refundId), HttpStatus.OK);
    }

    // USER or ADMIN: check the status of a specific refund
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/{refundId}")
    public ResponseEntity<RefundDto> getRefundStatus(@PathVariable Long refundId) {
        return new ResponseEntity<>(refundService.getRefundStatus(refundId), HttpStatus.OK);
    }

    // ADMIN-only: view all refunds by status (e.g., PENDING, APPROVED)
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/status")
    public ResponseEntity<List<RefundDto>> getRefundsByStatus(@RequestParam RefundStatus status) {
        return new ResponseEntity<>(refundService.getRefundsByStatus(status), HttpStatus.OK);
    }
}
