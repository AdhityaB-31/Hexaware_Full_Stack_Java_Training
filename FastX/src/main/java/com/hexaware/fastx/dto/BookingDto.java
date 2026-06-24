package com.hexaware.fastx.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BookingDto {
    private Long bookingId;

    private LocalDate bookingDate;

    private String bookingStatus;

    private LocalDateTime reservationExpiresAt;
    
    private Double totalAmount;

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Bus ID is required")
    private Long busId;

    @Valid
    @NotNull(message = "Passengers list is required")
    @Size(min = 1, message = "At least one passenger is required")
    private List<PassengerDto> passengers;
}
