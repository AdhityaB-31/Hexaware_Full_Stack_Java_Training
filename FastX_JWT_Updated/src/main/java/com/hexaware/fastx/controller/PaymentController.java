package com.hexaware.fastx.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.hexaware.fastx.dto.PaymentDto;
import com.hexaware.fastx.service.PaymentService;

// -----------------------------------------------------------
// PaymentController  –  role-based access
//
// USER        → process payment, view own payment
// ADMIN       → view any payment, approve refunds
// -----------------------------------------------------------
@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    // USER or ADMIN: view payment details for a booking
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<PaymentDto> getPaymentByBookingId(@PathVariable Long bookingId) {
        return new ResponseEntity<>(paymentService.getPaymentByBookingId(bookingId), HttpStatus.OK);
    }

    // USER: process (make) a payment for a booking
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/process/{bookingId}")
    public ResponseEntity<PaymentDto> processPayment(@PathVariable Long bookingId,
                                                      @RequestParam String paymentMethod) {
        return new ResponseEntity<>(paymentService.processPayment(bookingId, paymentMethod), HttpStatus.OK);
    }

    // USER or ADMIN: verify payment status
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/{paymentId}/verify")
    public ResponseEntity<PaymentDto> verifyPayment(@PathVariable Long paymentId) {
        return new ResponseEntity<>(paymentService.verifyPayment(paymentId), HttpStatus.OK);
    }

    // ADMIN-only: process a refund for a payment
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{paymentId}/refund")
    public ResponseEntity<PaymentDto> refundPayment(@PathVariable Long paymentId) {
        return new ResponseEntity<>(paymentService.refundPayment(paymentId), HttpStatus.OK);
    }
}
