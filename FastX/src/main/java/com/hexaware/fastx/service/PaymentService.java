package com.hexaware.fastx.service;

import com.hexaware.fastx.dto.PaymentDto;

public interface PaymentService {
    PaymentDto getPaymentByBookingId(Long bookingId);
    PaymentDto processPayment(Long bookingId, String paymentMethod);
    PaymentDto verifyPayment(Long paymentId);
    PaymentDto refundPayment(Long paymentId);
}
