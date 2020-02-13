package com.challenge.up42.image.service;

import com.challenge.up42.image.data.DataSource;
import com.challenge.up42.image.dto.FeatureDto;
import com.challenge.up42.image.dto.FeatureResponseDto;
import com.challenge.up42.image.dto.converter.FeatureToFeatureResponseDtoConverter;
import com.challenge.up42.image.exception.NotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Service
@Log4j2
public class FeatureService {
    private FeatureToFeatureResponseDtoConverter featureToFeatureResponseDtoConverter;
    private DataSource datasSource;

    @Autowired
    public FeatureService(FeatureToFeatureResponseDtoConverter featureToFeatureResponseDtoConverter, DataSource dataSource) {
        this.featureToFeatureResponseDtoConverter = featureToFeatureResponseDtoConverter;
        this.datasSource = dataSource;
    }

    public List<FeatureResponseDto> findAllFeatures() {
        log.debug("Returning all features");
        return datasSource.getFeatures().stream().map(featureToFeatureResponseDtoConverter::convert).collect(Collectors.toList());
    }

    public FeatureResponseDto findFeatureById(final UUID id) {
        log.debug(format("Returning feature by id={%s}.", id));
        return featureToFeatureResponseDtoConverter.convert(getFeatureById(id));
    }

    public byte[] findQuicklookByFeatureId(final UUID id) {
        log.debug(format("Returning quicklook for feature by id={%s}.", id));
        return getFeatureById(id).getProperties().getQuicklook();
    }

    private FeatureDto getFeatureById(UUID id) {
        return datasSource.getFeatureById(id)
                .orElseThrow(() ->new NotFoundException(format("Feature with id {%s} not found.", id.toString())));
    }
}
