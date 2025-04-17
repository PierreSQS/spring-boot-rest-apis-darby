package com.luv2code.springboot.employees.controller;

import com.luv2code.springboot.employees.entity.Employee;
import com.luv2code.springboot.employees.request.EmployeeRequest;
import com.luv2code.springboot.employees.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmployeeRestController.class)
class EmployeeRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EmployeeService employeeService;

    @Test
    @WithMockUser(roles = "EMPLOYEE")
    void testFindAllEmployeesWithEmployeeRole() throws Exception {
        Mockito.when(employeeService.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/employees"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    @WithMockUser(roles = "MANAGER")
    void testAddEmployeeWithManagerRole() throws Exception {
        EmployeeRequest employeeRequest = new EmployeeRequest();
        employeeRequest.setFirstName("John");
        employeeRequest.setLastName("Doe");
        employeeRequest.setEmail("john.doe@example.com");

        Employee savedEmployee = new Employee();
        savedEmployee.setId(1L);
        savedEmployee.setFirstName("John");
        savedEmployee.setLastName("Doe");
        savedEmployee.setEmail("john.doe@example.com");

        Mockito.when(employeeService.save(any(EmployeeRequest.class))).thenReturn(savedEmployee);

        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "firstName": "John",
                                    "lastName": "Doe",
                                    "email": "john.doe@example.com"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testDeleteEmployeeWithAdminRole() throws Exception {
        Mockito.doNothing().when(employeeService).deleteById(eq(1L));

        mockMvc.perform(delete("/api/employees/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testUnauthorizedAccess() throws Exception {
        mockMvc.perform(get("/api/employees"))
                .andExpect(status().isUnauthorized());
    }
}