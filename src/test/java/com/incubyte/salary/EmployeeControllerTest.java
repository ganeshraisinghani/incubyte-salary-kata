package com.incubyte.salary;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.incubyte.salary.model.Employee;
import com.incubyte.salary.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateEmployee() throws Exception {
        Employee emp = new Employee(1L, "Alice", "Developer", "India", 1000.0);

        Mockito.when(service.create(Mockito.any())).thenReturn(emp);

        mockMvc.perform(post("/employees")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(emp)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value("Alice"));
    }

    @Test
    void shouldGetEmployeeById() throws Exception {
        Employee emp = new Employee(1L, "Alice", "Developer", "India", 1000.0);

        Mockito.when(service.get(1L)).thenReturn(emp);

        mockMvc.perform(get("/employees/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value("Alice"));
    }

    @Test
    void shouldReturn404WhenEmployeeNotFound() throws Exception {
        Mockito.when(service.get(2L)).thenReturn(null);

        mockMvc.perform(get("/employees/2"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldGetAllEmployees() throws Exception {
        List<Employee> employees = List.of(
                new Employee(1L, "Alice", "Developer", "India", 1000.0),
                new Employee(2L, "Bob", "Manager", "US", 2000.0)
        );

        Mockito.when(service.getAll()).thenReturn(employees);

        mockMvc.perform(get("/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void shouldUpdateEmployee() throws Exception {
        Employee emp = new Employee(1L, "Alice Updated", "Developer", "India", 1200.0);

        Mockito.when(service.update(Mockito.eq(1L), Mockito.any())).thenReturn(emp);

        mockMvc.perform(put("/employees/1")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(emp)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.salary").value(1200.0));
    }

    @Test
    void shouldDeleteEmployee() throws Exception {
        Employee emp = new Employee(1L, "Alice", "Developer", "India", 1000.0);

        Mockito.when(service.delete(1L)).thenReturn(emp);

        mockMvc.perform(delete("/employees/1"))
                .andExpect(status().isOk());
    }
}