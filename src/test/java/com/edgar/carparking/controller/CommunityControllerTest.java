package com.edgar.carparking.controller;

import com.edgar.carparking.config.SecurityConfig;
import com.edgar.carparking.dto.CommunityItemResponse;
import com.edgar.carparking.dto.CommunityResponse;
import com.edgar.carparking.service.CommunityService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(controllers = CommunityController.class)
@Import(SecurityConfig.class)
class CommunityControllerTest {

    @MockBean
    private CommunityService communityService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAllCommunitiesTest() throws Exception {
        CommunityItemResponse community1 = new CommunityItemResponse(1L, "community 1");
        CommunityItemResponse community2 = new CommunityItemResponse(1L, "community 1");
        CommunityItemResponse community3 = new CommunityItemResponse(1L, "community 1");
        List<CommunityItemResponse> communityItems = new ArrayList<>();
        communityItems.add(community1);
        communityItems.add(community2);
        communityItems.add(community3);
        CommunityResponse communities = new CommunityResponse(communityItems);

        Mockito.when(communityService.getAllCommunities())
                .thenReturn(communities);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/community"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.size()", Matchers.is(1)));

        Mockito.verify(communityService).getAllCommunities();
    }

}