
import com.incubyte.salary.model.Employee;
import com.incubyte.salary.repository.EmployeeRepository;
import com.incubyte.salary.service.SalaryService;
import com.incubyte.salary.dto.SalaryResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat; // Better for BigDecimal
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class) // Cleanest way to test Services
class SalaryServiceTest {

    @Mock
    private EmployeeRepository repository;

    @InjectMocks
    private SalaryService service;

    @Test
    void shouldCalculateSalaryForIndiaWithTenPercentTax() {
        // Arrange
        BigDecimal gross = new BigDecimal("1000.00");
        Employee emp = Employee.builder()
                .id(1L)
                .fullName("Alice")
                .country("India")
                .salary(gross)
                .build();

        when(repository.findById(1L)).thenReturn(Optional.of(emp));

        // Act
        var result = service.calculateSalary(1L).orElseThrow();

        // Assert - Using isEqualByComparingTo to handle BigDecimal scale (e.g., 100.0 vs 100.00)
        assertThat(result.getGrossSalary()).isEqualByComparingTo(gross);
        assertThat(result.getDeduction()).isEqualByComparingTo(new BigDecimal("100.00"));
        assertThat(result.getNetSalary()).isEqualByComparingTo(new BigDecimal("900.00"));
    }

    @Test
    void shouldReturnZeroDeductionForUnknownCountries() {
        // Arrange
        BigDecimal gross = new BigDecimal("1000.00");
        Employee emp = Employee.builder()
                .id(2L)
                .fullName("Bob")
                .country("Germany")
                .salary(gross)
                .build();

        when(repository.findById(2L)).thenReturn(Optional.of(emp));

        // Act
        var result = service.calculateSalary(2L).orElseThrow();

        // Assert
        assertThat(result.getDeduction()).isEqualByComparingTo(BigDecimal.ZERO);
        assertThat(result.getNetSalary()).isEqualByComparingTo(gross);
    }
}