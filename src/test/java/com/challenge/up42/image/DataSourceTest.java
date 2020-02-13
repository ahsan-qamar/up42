package com.challenge.up42.image;

import com.challenge.up42.image.data.DataSource;
import com.challenge.up42.image.dto.FeatureCollectionDto;
import com.challenge.up42.image.dto.FeatureDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class DataSourceTest {
    private final String fileName = "source-data.json";
    private DataSource underTest;
    private List<FeatureCollectionDto> featureCollections;

    @BeforeEach
    void setUp() {
        underTest = new DataSource();
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        try {
            featureCollections = Arrays.asList(mapper.readValue(getClass().getClassLoader().getResourceAsStream(fileName), FeatureCollectionDto[].class));
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException("The data input file cannot be parsed.");
        }
    }

    @Test
    void testGetFeatures() {
        List<FeatureDto> expected = featureCollections.stream().flatMap(f -> Arrays.stream(f.getFeatures())).collect(Collectors.toList());
        List<FeatureDto> actual = underTest.getFeatures();
        assertThat(actual, equalTo(expected));
    }

    @Test
    void testGetFeature() {
        UUID uuid = UUID.fromString("39c2f29e-c0f8-4a39-a98b-deed547d6aea");
        Optional<FeatureDto> actual = underTest.getFeatureById(uuid);
        FeatureDto expected = featureCollections.stream().flatMap(f -> Arrays.stream(f.getFeatures())).filter(f -> f.getProperties().getId().equals(uuid)).findFirst().get();
        assertThat(actual.isPresent(), is(true));
        assertThat(actual.get(), equalTo(expected));
    }

    @Test
    void testGetFeatureNotFound() {
        UUID uuid = UUID.fromString("11111-c0f8-4a39-a98b-deed547d6aea");
        Optional<FeatureDto> actual = underTest.getFeatureById(uuid);
        assertThat(actual.isPresent(), is(false));
    }
}
