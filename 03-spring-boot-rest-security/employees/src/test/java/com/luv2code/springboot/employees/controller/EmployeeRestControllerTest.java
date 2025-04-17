package com.luv2code.springboot.employees.controller;

import com.luv2code.springboot.employees.entity.Employee;
import com.luv2code.springboot.employees.request.EmployeeRequest;
import com.luv2code.springboot.employees.service.EmployeeService;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmployeeRestController.class)
class EmployeeRestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    EmployeeService employeeService;

    @Test
    @WithMockUser(roles = "EMPLOYEE")
    void findEmployeeByIdWithValidIdReturnsEmployee() throws Exception {
        Employee employee = new Employee();
        employee.setId(2L);
        employee.setFirstName("Jane");
        employee.setLastName("Doe");
        employee.setEmail("jane.doe@example.com");

        Mockito.when(employeeService.findById(2L)).thenReturn(employee);

        mockMvc.perform(get("/api/employees/2"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.firstName").value("Jane"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.email").value("jane.doe@example.com"));
    }

    @Test
    @WithMockUser(roles = "EMPLOYEE")
    void findEmployeeByIdWithInvalidIdReturnsNotFound() {
        Mockito.when(employeeService.findById(99L)).thenThrow(new RuntimeException("Employee not found"));

        assertThatThrownBy(() -> mockMvc.perform(get("/api/employees/99")))
                .isInstanceOf(ServletException.class)
                .hasMessageContaining("Employee not found");

    }

    @Test
    @WithMockUser(roles = "MANAGER")
    void updateEmployeeWithValidDataReturnsUpdatedEmployee() throws Exception {
        EmployeeRequest employeeRequest = new EmployeeRequest();
        employeeRequest.setFirstName("Updated");
        employeeRequest.setLastName("Name");
        employeeRequest.setEmail("updated.name@example.com");

        Employee updatedEmployee = new Employee();
        updatedEmployee.setId(1L);
        updatedEmployee.setFirstName("Updated");
        updatedEmployee.setLastName("Name");
        updatedEmployee.setEmail("updated.name@example.com");

        Mockito.when(employeeService.update(eq(1L), any(EmployeeRequest.class))).thenReturn(updatedEmployee);

        mockMvc.perform(put("/api/employees/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "firstName": "Updated",
                                    "lastName": "Name",
                                    "email": "updated.name@example.com"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("Updated"))
                .andExpect(jsonPath("$.lastName").value("Name"))
                .andExpect(jsonPath("$.email").value("updated.name@example.com"));
    }

    @Test
    @WithMockUser(roles = "MANAGER")
    void updateEmployeeWithInvalidIdReturnsNotFound() {
        Mockito.when(employeeService.update(eq(99L), any(EmployeeRequest.class))).thenThrow(new RuntimeException("Employee not found"));

        assertThatThrownBy(() -> mockMvc.perform(put("/api/employees/99")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "firstName": "Invalid",
                            "lastName": "User",
                            "email": "invalid.user@example.com"
                        }
                        """)))
                .isInstanceOf(ServletException.class)
                .hasMessageContaining("Employee not found");
    }
}