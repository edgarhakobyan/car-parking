package com.edgar.carparking.repository;

import com.edgar.carparking.model.Community;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityRepository extends JpaRepository<Community, Long> {
}
