package com.hexaware.fastx.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PaymentDto {

    private Long paymentId;

    private Double amount;

    @NotBlank(message = "Payment method is required")
    @Pattern(regexp = "^(UPI|CREDIT_CARD|DEBIT_CARD|NET_BANKING)$",
             message = "Payment method must be UPI, CREDIT_CARD, DEBIT_CARD, or NET_BANKING")
    private String paymentMethod;

    private String transactionId;

    private String paymentStatus;
    
    private LocalDateTime paymentDate;

    private Long bookingId;
}
