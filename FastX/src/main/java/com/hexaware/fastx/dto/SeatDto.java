package com.hexaware.fastx.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
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
public class SeatDto {
    private Long seatId;

    @NotBlank(message = "Seat number is required")
    private String seatNumber;

    @NotBlank(message = "Seat type is required")
    private String seatType;

    private String seatStatus;

    @NotNull(message = "Bus ID is required")
    private Long busId;

    private Long reservedByBookingId;
}
