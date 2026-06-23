package com.hexaware.fastx.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hexaware.fastx.dto.RefundDto;
import com.hexaware.fastx.entity.Booking;
import com.hexaware.fastx.entity.Refund;
import com.hexaware.fastx.enums.BookingStatus;
import com.hexaware.fastx.enums.RefundStatus;
import com.hexaware.fastx.exception.BookingException;
import com.hexaware.fastx.exception.ResourceNotFoundException;
import com.hexaware.fastx.repository.BookingRepository;
import com.hexaware.fastx.repository.RefundRepository;
import com.hexaware.fastx.service.RefundService;

@Service
@Transactional
public class RefundServiceImpl implements RefundService {

    private static final Logger logger = LoggerFactory.getLogger(RefundServiceImpl.class);

    @Autowired
    private RefundRepository refundRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Override
    public RefundDto createRefund(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with ID: " + bookingId));

        // Refund should only be created for cancelled bookings
        if (booking.getBookingStatus() != BookingStatus.CANCELLED) {
            throw new BookingException("Refund can only be created for cancelled bookings. Current status: " + booking.getBookingStatus());
        }

        Refund refund = new Refund();

        refund.setRefundAmount(booking.getTotalAmount());
        refund.setRefundDate(LocalDateTime.now());
        refund.setRefundStatus(RefundStatus.PENDING);
        refund.setBooking(booking);

        Refund saved = refundRepository.save(refund);
        logger.info("Refund created for Booking ID: {}. Refund Amount: {}", bookingId, saved.getRefundAmount());
        return mapToDto(saved);
    }

    @Override
    public RefundDto approveRefund(Long refundId) {
        Refund refund = refundRepository.findById(refundId)
                .orElseThrow(() -> new ResourceNotFoundException("Refund not found with ID: " + refundId));
        refund.setRefundStatus(RefundStatus.APPROVED);
        Refund approved = refundRepository.save(refund);
        logger.info("Refund approved with ID: {}", refundId);
        return mapToDto(approved);
    }

    @Override
    public RefundDto getRefundStatus(Long refundId) {
        Refund refund = refundRepository.findById(refundId)
                .orElseThrow(() -> new ResourceNotFoundException("Refund not found with ID: " + refundId));
        return mapToDto(refund);
    }
    
    @Override
    public List<RefundDto> getRefundsByStatus(RefundStatus status) {

        List<Refund> refunds =
                refundRepository.findByRefundStatus(status);

        List<RefundDto> refundDtos =
                new ArrayList<>();

        for (Refund refund : refunds) {
            refundDtos.add(mapToDto(refund));
        }

        return refundDtos;
    }

    private RefundDto mapToDto(Refund refund) {
        RefundDto dto = new RefundDto();
        dto.setRefundId(refund.getRefundId());
        dto.setRefundAmount(refund.getRefundAmount());
        dto.setRefundDate(refund.getRefundDate());
        dto.setRefundStatus(refund.getRefundStatus() != null ? refund.getRefundStatus().name() : null);
        dto.setBookingId(refund.getBooking() != null ? refund.getBooking().getBookingId() : null);
        return dto;
    }

}