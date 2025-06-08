// Datei: src/test/java/com/luv2code/springboot/todos/controller/UserControllerTest.java
package com.luv2code.springboot.todos.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luv2code.springboot.todos.request.PasswordUpdateRequest;
import com.luv2code.springboot.todos.response.UserResponse;
import com.luv2code.springboot.todos.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("GET /api/users/info - Erfolgreich")
    void getUserInfo_success() throws Exception {
        UserResponse response = new UserResponse();
        response.setUsername("testuser");
        Mockito.when(userService.getUserInfo()).thenReturn(response);

        mockMvc.perform(get("/api/users/info"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testuser"));
    }

    @Test
    @DisplayName("GET /api/users/info - Service gibt null zurück (Edge Case)")
    void getUserInfo_nullResponse() throws Exception {
        Mockito.when(userService.getUserInfo()).thenReturn(null);

        mockMvc.perform(get("/api/users/info"))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    @Test
    @DisplayName("DELETE /api/users/delete - Erfolgreich")
    void deleteUser_success() throws Exception {
        mockMvc.perform(delete("/api/users/delete"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("DELETE /api/users/delete - Service wirft Exception (Edge Case)")
    void deleteUser_serviceThrows() throws Exception {
        doThrow(new RuntimeException("Fehler")).when(userService).deleteUser();

        mockMvc.perform(delete("/api/users/delete"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("PUT /api/users/update-password - Erfolgreich")
    void updatePassword_success() throws Exception {
        PasswordUpdateRequest req = new PasswordUpdateRequest();
        req.setOldPassword("old");
        req.setNewPassword("new");

        mockMvc.perform(put("/api/users/update-password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("PUT /api/users/update-password - Ungültige Anfrage (Edge Case)")
    void updatePassword_invalidRequest() throws Exception {
        PasswordUpdateRequest req = new PasswordUpdateRequest(); // Felder leer

        mockMvc.perform(put("/api/users/update-password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isBadRequest());
    }
}