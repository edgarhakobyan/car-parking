package com.edgar.carparking.controller;

import com.edgar.carparking.dto.CommunityResponse;
import com.edgar.carparking.service.CommunityService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/community")
@AllArgsConstructor
@Tag(name = "Community")
public class CommunityController {
    private final CommunityService communityService;

    @GetMapping
    public ResponseEntity<CommunityResponse> getAllCommunities() {
        return new ResponseEntity<>(communityService.getAllCommunities(), OK);
    }
}
