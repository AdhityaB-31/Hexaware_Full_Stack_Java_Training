package com.hexaware.fastx.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema
public class BookingDto {
    @Schema(example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long bookingId;

    @Schema(example = "2026-07-01", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDate bookingDate;

    @Schema(example = "PENDING",
            allowableValues = {"PENDING", "CONFIRMED", "CANCELLED", "EXPIRED"}, accessMode = Schema.AccessMode.READ_ONLY)
    private String bookingStatus;

    @Schema(example = "2026-07-01T10:30:00", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime reservationExpiresAt;

    @Schema(example = "1198.0", accessMode = Schema.AccessMode.READ_ONLY)
    private Double totalAmount;

    @NotNull(message = "User ID is required")
    @Schema(example = "1")
    private Long userId;

    @NotNull(message = "Bus ID is required")
    @Schema(example = "1")
    private Long busId;

    @Valid
    @NotNull(message = "Passengers list is required")
    @Size(min = 1, message = "At least one passenger is required")
    @Schema
    private List<PassengerDto> passengers;
}
