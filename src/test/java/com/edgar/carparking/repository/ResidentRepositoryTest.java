package com.edgar.carparking.repository;

import com.edgar.carparking.model.Community;
import com.edgar.carparking.model.Resident;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
class ResidentRepositoryTest {
    @Autowired
    ResidentRepository residentRepository;

    @Test
    void createResidentTest() {
        Community community = new Community(1L, "test");
        Resident resident = new Resident();
        resident.setUsername("Testgtgtgt");
        resident.setPassword("testgtgtgtg");
        resident.setCommunity(community);

        Resident savedResident = residentRepository.save(resident);

        assertNotNull(savedResident);
    }
}