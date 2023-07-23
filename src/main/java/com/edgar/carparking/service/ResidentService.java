package com.edgar.carparking.service;

import com.edgar.carparking.model.Resident;
import com.edgar.carparking.repository.ResidentRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ResidentService {
    private final ResidentRepository residentRepository;

    public Optional<Resident> getAuthorizedResident() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("========== " + authentication);
        if (authentication != null) {
            User principal = (User) authentication.getPrincipal();
            System.out.println("========== principal " + principal);
                System.out.println("========== " + principal.getUsername());
                return residentRepository.findByUsername(principal.getUsername());
        }
        return Optional.empty();
    }
}
