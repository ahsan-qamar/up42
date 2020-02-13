package com.challenge.up42.image.dto.converter;

import com.challenge.up42.image.dto.FeatureDto;
import com.challenge.up42.image.dto.FeatureResponseDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class FeatureToFeatureResponseDtoConverter implements Converter<FeatureDto, FeatureResponseDto> {
    @Override
    public FeatureResponseDto convert(FeatureDto featureDto) {
        FeatureResponseDto result = FeatureResponseDto.builder().id(featureDto.getProperties().getId())
                .timestamp(featureDto.getProperties().getTimestamp())
                .beginViewingDate(featureDto.getProperties().getAcquisition().getBeginViewingDate())
                .endViewingDate(featureDto.getProperties().getAcquisition().getEndViewingDate())
                .missionName(featureDto.getProperties().getAcquisition().getMissionName())
                .build();

        return result;
    }
}
