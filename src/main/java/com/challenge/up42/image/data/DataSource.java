package com.challenge.up42.image.data;

import com.challenge.up42.image.dto.FeatureCollectionDto;
import com.challenge.up42.image.dto.FeatureDto;
import com.challenge.up42.image.exception.InternalServerErrorException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class DataSource {
    private static final String dataFileName = "source-data.json";
    private List<FeatureCollectionDto> featureCollections;

    public DataSource() {
        load();
    }

    private void load() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        try {
            featureCollections = Arrays.asList(mapper.readValue(getClass().getClassLoader().getResourceAsStream(dataFileName), FeatureCollectionDto[].class));
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new InternalServerErrorException("The data input file cannot be parsed.");
        }
    }

    public List<FeatureDto> getFeatures() {
        return featureCollections.stream().flatMap(x -> Arrays.stream(x.getFeatures())).collect(Collectors.toList());
    }

    public Optional<FeatureDto> getFeatureById(UUID id) {
        return getFeatures().stream().filter(f -> f.getProperties().getId().equals(id)).findAny();
    }
}
