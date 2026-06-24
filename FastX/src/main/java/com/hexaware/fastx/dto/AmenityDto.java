package com.hexaware.fastx.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AmenityDto {

    private Long amenityId;

    @NotBlank(message = "Amenity name is required")
    private String amenityName;
}
