package com.edgar.carparking.controller;

import com.edgar.carparking.config.SecurityConfig;
import com.edgar.carparking.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(controllers = AuthController.class)
@Import(SecurityConfig.class)
class AuthControllerTest {
    @MockBean
    private AuthService authService;

    @Autowired
    private MockMvc mockMvc;




}