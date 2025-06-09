package com.luv2code.springboot.todos.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.luv2code.springboot.todos.request.AuthenticationRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerWithTokenIT {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    AuthenticationRequest authenticationRequest;

    @BeforeEach
    void setUp() {
        authenticationRequest = AuthenticationRequest.builder()
                .email("mongonnam@luv2code.com")
                .password("Mongonnam")
                .build();
    }

    @Test
    @DisplayName("POST /api/v1/auth/login and GET /api/users/info with JWT")
    void loginAndGetUserInfo_withJwtToken() throws Exception {

        String loginResponse = mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authenticationRequest)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        // extract JWT
        JsonNode jsonNode = objectMapper.readTree(loginResponse);
        String jwt = jsonNode.get("token").asText();

        // Authenticated request to /api/users/info
        mockMvc.perform(get("/api/users/info")
                        .header("Authorization", "Bearer " + jwt))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName")
                        .value("Pierrot Mongonnam"))
                .andExpect(jsonPath("$.authorities[0].authority").value("ROLE_ADMIN"))
                .andExpect(jsonPath("$.authorities[1].authority").value("ROLE_USER"))
                .andDo(print());
    }

    @Test
    @DisplayName(" GET /api/users/info without JWT")
    void getUserInfo_WithoutJwtToken() throws Exception {

        // Unauthenticated request to /api/users/info
        mockMvc.perform(get("/api/users/info"))
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }

}