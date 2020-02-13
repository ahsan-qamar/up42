package com.challenge.up42.image;

import com.challenge.up42.image.dto.FeatureResponseDto;
import com.challenge.up42.image.dto.converter.FeatureToFeatureResponseDtoConverter;
import com.challenge.up42.image.service.FeatureService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class FeatureControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private FeatureService featureService;
    @Autowired
    private FeatureToFeatureResponseDtoConverter featureToFeatureResponseDtoConverter;

    @Test
    void testGetFeatures() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/features", 42L)
                .accept("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        ObjectMapper mapper = new ObjectMapper();
        FeatureResponseDto[] features = mapper.readValue(result.getResponse().getContentAsString(), FeatureResponseDto[].class);
        assertThat(features.length, is(14));

        //the order of items cannot be ensured
        List<FeatureResponseDto> actual = Arrays.asList(features);
        List<FeatureResponseDto> expected = featureService.findAllFeatures();
        assertThat(actual.stream().filter(f -> !expected.contains(f)).findAny().isPresent(), is(false));
        assertThat(expected.stream().filter(f -> !actual.contains(f)).findAny().isPresent(), is(false));
    }

    @Test
    void testGetFeature() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/features/39c2f29e-c0f8-4a39-a98b-deed547d6aea", 42L)
                .accept("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        ObjectMapper mapper = new ObjectMapper();
        FeatureResponseDto feature = mapper.readValue(result.getResponse().getContentAsString(), FeatureResponseDto.class);
        assertThat(feature, notNullValue());

        assertThat(feature.getBeginViewingDate(), equalTo(1554831167697L));
        assertThat(feature.getEndViewingDate(), is(1554831202043L));
        assertThat(feature.getTimestamp(), is(1554831167697L));
        assertThat(feature.getMissionName(), is("Sentinel-1B"));

    }

    @Test
    void testGetFeature404() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/features/111111-c0f8-4a39-a98b-deed547d6aea", 42L)
                .accept("application/json"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn();
    }

    @Test
    void testGetQuickLook() throws Exception {
        String uuid = "39c2f29e-c0f8-4a39-a98b-deed547d6aea";
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(String.format("/features/%s/quicklook", uuid), 42L)
                .accept("image/png"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        byte[] actual = result.getResponse().getContentAsByteArray();
        byte[] expected = featureService.findQuicklookByFeatureId(UUID.fromString(uuid));

        assertThat(actual, notNullValue());
        assertThat(actual.length, is(expected.length));
        assertThat(actual, equalTo(expected));
    }

    @Test
    void testGetQuickLook404() throws Exception {
        String uuid = "1111111-c0f8-4a39-99b-deed547d6aea";
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(String.format("/features/%s/quicklook", uuid), 42L)
                .accept("image/png"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn();
    }
}
