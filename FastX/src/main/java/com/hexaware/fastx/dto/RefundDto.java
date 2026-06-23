package com.hexaware.fastx.dto;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Schema
public class RefundDto {
    private Long refundId;

    private Double refundAmount;

    private LocalDateTime refundDate;

    private String refundStatus;

    @NotNull(message = "Booking ID is required")
    private Long bookingId;
}
