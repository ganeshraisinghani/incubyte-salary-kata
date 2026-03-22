package com.incubyte.salary;

import com.incubyte.salary.model.Employee;
import com.incubyte.salary.repository.EmployeeRepository;
import com.incubyte.salary.service.SalaryService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class SalaryServiceTest {

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