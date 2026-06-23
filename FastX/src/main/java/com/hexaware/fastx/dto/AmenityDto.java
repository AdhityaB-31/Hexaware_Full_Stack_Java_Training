package com.hexaware.fastx.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Schema
public class AmenityDto {

    private Long amenityId;

    @NotBlank(message = "Amenity name is required")
    private String amenityName;
}
