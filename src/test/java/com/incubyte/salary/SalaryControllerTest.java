import com.incubyte.salary.controller.SalaryController;
import com.incubyte.salary.SalaryKataApplication; // ADD THIS IMPORT
import com.incubyte.salary.dto.SalaryResponse;
import com.incubyte.salary.service.SalaryService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SalaryController.class)
@ContextConfiguration(classes = SalaryKataApplication.class)
class SalaryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SalaryService service;

    @Test
    void shouldCalculateSalaryForIndia() throws Exception {
        SalaryResponse response = SalaryResponse.builder()
                .grossSalary(new BigDecimal("1000.00"))
                .deduction(new BigDecimal("100.00"))
                .netSalary(new BigDecimal("900.00"))
                .build();

        Mockito.when(service.calculateSalary(1L)).thenReturn(Optional.of(response));

        mockMvc.perform(get("/salary/calculate/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.netSalary").value(900.00));
    }

    @Test
    void shouldReturn404WhenEmployeeNotFound() throws Exception {
        Mockito.when(service.calculateSalary(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/salary/calculate/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnCountryMetrics() throws Exception {
        // Mocking metrics: Avg=1500, Min=1000, Max=2000
        SalaryResponse response = SalaryResponse.builder()
                .grossSalary(new BigDecimal("1500.00"))
                .deduction(new BigDecimal("1000.00"))
                .netSalary(new BigDecimal("2000.00"))
                .build();

        Mockito.when(service.metricsByCountry("India")).thenReturn(response);

        mockMvc.perform(get("/salary/metrics/country/India"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.grossSalary").value(1500.00));
    }

    @Test
    void shouldReturn404WhenNoCountryData() throws Exception {
        Mockito.when(service.metricsByCountry("Mars")).thenReturn(null);

        mockMvc.perform(get("/salary/metrics/country/Mars"))
                .andExpect(status().isNotFound());
    }

    @Test
void shouldReturnAverageSalaryByJobTitle() throws Exception {
    // Change "1200.0" to "1200.00" to match the BigDecimal scale
    Mockito.when(service.averageSalaryByJob("Developer")).thenReturn(new BigDecimal("1200.00"));

    mockMvc.perform(get("/salary/metrics/job/Developer"))
            .andExpect(status().isOk())
            .andExpect(content().string("1200.00")); // Update this line
}
}