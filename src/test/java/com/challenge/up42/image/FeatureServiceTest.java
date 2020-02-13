package com.challenge.up42.image;

import com.challenge.up42.image.data.DataSource;
import com.challenge.up42.image.dto.FeatureResponseDto;
import com.challenge.up42.image.dto.converter.FeatureToFeatureResponseDtoConverter;
import com.challenge.up42.image.service.FeatureService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class FeatureServiceTest {
    @Autowired
    private DataSource dataSource;
    @Autowired
    private FeatureToFeatureResponseDtoConverter featureToFeatureResponseDtoConverter;
    private FeatureService underTest;

    @BeforeEach
    void setUp() {
        underTest = new FeatureService(featureToFeatureResponseDtoConverter, dataSource);
    }

    @Test
    void testGetFeatures() {
        List<FeatureResponseDto> actual = underTest.findAllFeatures();
        List<FeatureResponseDto> expected = dataSource.getFeatures().stream().map(featureToFeatureResponseDtoConverter::convert).collect(Collectors.toList());
        assertThat(actual, equalTo(expected));
    }

    @Test
    void testGetFeature() {
        UUID uuid = UUID.fromString("39c2f29e-c0f8-4a39-a98b-deed547d6aea");
        FeatureResponseDto actual = underTest.findFeatureById(uuid);
        FeatureResponseDto expected = dataSource.getFeatureById(uuid).map(featureToFeatureResponseDtoConverter::convert).get();
        assertThat(actual, equalTo(expected));
    }

    @Test
    void testGetFeatureQuickLook() {
        UUID uuid = UUID.fromString("39c2f29e-c0f8-4a39-a98b-deed547d6aea");
        byte[] actual = underTest.findQuicklookByFeatureId(uuid);
        byte[] expected = dataSource.getFeatureById(uuid).map(f -> f.getProperties().getQuicklook()).get();
        assertThat(actual, equalTo(expected));
    }
}
