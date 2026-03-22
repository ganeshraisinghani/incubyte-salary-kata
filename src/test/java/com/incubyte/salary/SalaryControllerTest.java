package com.incubyte.salary;

import com.incubyte.salary.controller.SalaryController;
import com.incubyte.salary.dto.SalaryResponse;
import com.incubyte.salary.service.SalaryService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
class SalaryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SalaryService service;

    @Test
    void shouldCalculateSalaryForIndia() throws Exception {
        SalaryResponse response = new SalaryResponse(1000.0, 100.0, 900.0);

        Mockito.when(service.calculateSalary(1L)).thenReturn(response);

        mockMvc.perform(get("/salary/calculate/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.netSalary").value(900.0));
    }

    @Test
    void shouldReturn404WhenEmployeeNotFound() throws Exception {
        Mockito.when(service.calculateSalary(99L)).thenReturn(null);

        mockMvc.perform(get("/salary/calculate/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnCountryMetrics() throws Exception {
        SalaryResponse response = new SalaryResponse(1500.0, 1000.0, 2000.0);

        Mockito.when(service.metricsByCountry("India")).thenReturn(response);

        mockMvc.perform(get("/salary/metrics/country/India"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.grossSalary").value(1500.0));
    }

    @Test
    void shouldReturn404WhenNoCountryData() throws Exception {
        Mockito.when(service.metricsByCountry("Mars")).thenReturn(null);

        mockMvc.perform(get("/salary/metrics/country/Mars"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnAverageSalaryByJobTitle() throws Exception {
        Mockito.when(service.averageSalaryByJob("Developer")).thenReturn(1200.0);

        mockMvc.perform(get("/salary/metrics/job/Developer"))
                .andExpect(status().isOk())
                .andExpect(content().string("1200.0"));
    }
}