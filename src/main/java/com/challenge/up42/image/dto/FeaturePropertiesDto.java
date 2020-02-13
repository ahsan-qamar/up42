package com.challenge.up42.image.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class FeaturePropertiesDto {
    @NotNull
    private UUID id;
    private Long timestamp;
    private FeatureAcquisitionPropertiesDto acquisition;

    private byte[] quicklook;
}
