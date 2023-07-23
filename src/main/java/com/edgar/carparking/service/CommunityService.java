package com.edgar.carparking.service;

import com.edgar.carparking.dto.CommunityItemResponse;
import com.edgar.carparking.dto.CommunityResponse;
import com.edgar.carparking.exception.CarParkingException;
import com.edgar.carparking.model.Community;
import com.edgar.carparking.repository.CommunityRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CommunityService {
    private final CommunityRepository communityRepository;

    public Community getCommunityById(Long id) {
        return communityRepository.findById(id)
                .orElseThrow(() -> new CarParkingException(String.format("Community with id %s not found", id)));
    }

    public CommunityResponse getAllCommunities() {
        List<CommunityItemResponse> communityItemResponseList = communityRepository.findAll()
                .stream()
                .map(this::mapTo)
                .toList();
        return new CommunityResponse(communityItemResponseList);
    }

    private CommunityItemResponse mapTo(Community community) {
        return new CommunityItemResponse(community.getId(), community.getName());
    }
}
