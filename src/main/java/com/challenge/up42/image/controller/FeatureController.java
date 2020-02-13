package com.challenge.up42.image.controller;

import com.challenge.up42.image.dto.FeatureResponseDto;
import com.challenge.up42.image.service.FeatureService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

@RestController
@RequestMapping("/")
public class FeatureController {
    private FeatureService featureService;

    @Autowired
    public FeatureController(FeatureService featureService) {
        this.featureService = featureService;
    }

    @ApiOperation(value = "Return all features")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Features returned successfully", response = String.class)
    })
    @GetMapping(value = "/features", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<FeatureResponseDto>> getFeatures() {
        return new ResponseEntity(featureService.findAllFeatures(), HttpStatus.OK);
    }

    @ApiOperation(value = "Return a feature by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Feature returned successfully", response = String.class)
    })
    @GetMapping(value = "/features/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<FeatureResponseDto> getFeature(@PathVariable("id") final UUID featureId) {
        return ResponseEntity.ok().body(featureService.findFeatureById(featureId));
    }

    @ApiOperation(value = "Return a quick look image by feature id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Image returned successfully", response = String.class)
    })
    @GetMapping(value = "/features/{id}/quicklook", produces = IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getQuickLook(@PathVariable("id") final UUID featureId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setCacheControl(CacheControl.noCache().getHeaderValue());

        ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(featureService.findQuicklookByFeatureId(featureId), headers, HttpStatus.OK);
        return responseEntity;
    }
}
