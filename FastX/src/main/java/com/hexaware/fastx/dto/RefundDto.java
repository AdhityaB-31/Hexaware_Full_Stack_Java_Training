package com.hexaware.fastx.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RefundDto {
    private Long refundId;

    private Double refundAmount;

    private LocalDateTime refundDate;

    private String refundStatus;

    @NotNull(message = "Booking ID is required")
    private Long bookingId;
}
