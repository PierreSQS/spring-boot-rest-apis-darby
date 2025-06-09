package com.luv2code.springboot.todos.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luv2code.springboot.todos.request.PasswordUpdateRequest;
import com.luv2code.springboot.todos.response.UserResponse;
import com.luv2code.springboot.todos.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerIT {
    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    UserService userService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("GET /api/users/info - Erfolgreich")
    @WithMockUser(username="admin",roles={"USER","ADMIN"})
    void getUserInfo_success() throws Exception {
        UserResponse response = UserResponse.builder()
                .fullName("testuser")
                .build();

        Mockito.when(userService.getUserInfo()).thenReturn(response);

        mockMvc.perform(get("/api/users/info"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value("testuser"))
                .andDo(print());
    }

    @Test
    @DisplayName("GET /api/users/info - Service gibt null zurück (Edge Case)")
    @WithMockUser(username="admin",roles={"USER","ADMIN"})
    void getUserInfo_nullResponse() throws Exception {
        Mockito.when(userService.getUserInfo()).thenReturn(null);

        mockMvc.perform(get("/api/users/info"))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    @Test
    @DisplayName("DELETE /api/users/delete - Erfolgreich")
    @WithMockUser(username="admin",roles={"USER","ADMIN"})
    void deleteUser_success() throws Exception {
        mockMvc.perform(delete("/api/users/delete"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("DELETE /api/users/delete - Service wirft Exception (Edge Case)")
    @WithMockUser(username="admin",roles={"USER","ADMIN"})
    void deleteUser_serviceThrows() throws Exception {
        doThrow(new RuntimeException("Fehler")).when(userService).deleteUser();

        mockMvc.perform(delete("/api/users/delete"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("PUT /api/users/update-password - Erfolgreich")
    @WithMockUser(username="admin",roles={"USER","ADMIN"})
    void updatePassword_success() throws Exception {
        PasswordUpdateRequest req = PasswordUpdateRequest.builder()
                .pwdToUpdate("oldPwd")
                .newPassword("newPwd")
                .confirmedPassword("newPwd")
                .build();

        mockMvc.perform(put("/api/users/update-password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("PUT /api/users/update-password - Ungültige Anfrage (Edge Case)")
    @WithMockUser(username="admin",roles={"USER","ADMIN"})
    void updatePassword_invalidRequest() throws Exception {
        PasswordUpdateRequest req = PasswordUpdateRequest.builder().build(); // Felder leer

        mockMvc.perform(put("/api/users/update-password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isBadRequest());
    }
    
}
