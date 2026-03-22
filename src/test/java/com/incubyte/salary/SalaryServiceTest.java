package com.incubyte.salary;

import com.incubyte.salary.model.Employee;
import com.incubyte.salary.repository.EmployeeRepository;
import com.incubyte.salary.service.SalaryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class SalaryServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private SalaryService salaryService;

    private final EmployeeRepository repository = Mockito.mock(EmployeeRepository.class);
    private final SalaryService service = new SalaryService(repository);

    @Test
    void shouldCalculateSalaryForIndia() {
        Employee emp = new Employee(1L, "Alice", "Developer", "India", 1000.0);

        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(emp));

        var result = service.calculateSalary(1L);

        assertEquals(1000.0, result.getGrossSalary());
        assertEquals(100.0, result.getDeduction());
        assertEquals(900.0, result.getNetSalary());
    }

    @Test
    void shouldReturnZeroDeductionForOtherCountries() {
        Employee emp = new Employee(1L, "Bob", "Dev", "Germany", 1000.0);

        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(emp));

        var result = service.calculateSalary(1L);

        assertEquals(0.0, result.getDeduction());
        assertEquals(1000.0, result.getNetSalary());
    }
}