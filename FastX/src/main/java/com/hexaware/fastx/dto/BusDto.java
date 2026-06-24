package com.hexaware.fastx.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BusDto {

    private Long busId;

    @NotBlank(message = "Bus name is required")
    private String busName;

    @NotBlank(message = "Bus number is required")
    private String busNumber;

    @NotBlank(message = "Bus type is required")
    @Pattern(regexp = "^(AC_SLEEPER|NON_AC_SLEEPER|AC_SEATER|NON_AC_SEATER|VOLVO)$",
             message = "Bus type must be AC_SLEEPER, NON_AC_SLEEPER, AC_SEATER, NON_AC_SEATER, or VOLVO")
    private String busType;

    @NotBlank(message = "Origin is required")
    private String origin;

    @NotBlank(message = "Destination is required")
    private String destination;

    @NotNull(message = "Journey date is required")
    @FutureOrPresent(message = "Journey date must be today or a future date")
    private LocalDate journeyDate;

    @NotNull(message = "Departure time is required")
    private LocalTime departureTime;

    @NotNull(message = "Arrival time is required")
    private LocalTime arrivalTime;

    @NotNull(message = "Fare is required")
    @Min(value = 1, message = "Fare must be greater than 0")
    private Double fare;

    @NotNull(message = "Total seats is required")
    @Min(value = 1, message = "Total seats must be at least 1")
    private Integer totalSeats;

    @NotNull(message = "Operator ID is required")
    private Long operatorId;

    private List<Long> amenityIds;
}
