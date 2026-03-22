package com.incubyte.salary.service;

import com.incubyte.salary.model.Employee;
import com.incubyte.salary.repository.EmployeeRepository;
import com.incubyte.salary.dto.SalaryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SalaryService {

    private final EmployeeRepository repository;

    public Optional<SalaryResponse> calculateSalary(Long employeeId) {
        return repository.findById(employeeId).map(emp -> {
            BigDecimal grossSalary = emp.getSalary();
            
            // Define deduction rates as BigDecimals
            BigDecimal deductionRate = switch (emp.getCountry()) {
                case "India" -> new BigDecimal("0.10");
                case "United States" -> new BigDecimal("0.12");
                default -> BigDecimal.ZERO;
            };

            BigDecimal deduction = grossSalary.multiply(deductionRate).setScale(2, RoundingMode.HALF_UP);
            BigDecimal netSalary = grossSalary.subtract(deduction);

            return SalaryResponse.builder()
                    .grossSalary(grossSalary)
                    .deduction(deduction)
                    .netSalary(netSalary)
                    .build();
        });
    }

    public BigDecimal averageSalaryByJob(String jobTitle) {
        List<Employee> employees = repository.findByJobTitle(jobTitle);
        if (employees.isEmpty()) return BigDecimal.ZERO;

        BigDecimal total = employees.stream()
                .map(Employee::getSalary)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return total.divide(BigDecimal.valueOf(employees.size()), 2, RoundingMode.HALF_UP);
    }

    public SalaryResponse metricsByCountry(String country) {
        List<Employee> employees = repository.findByCountry(country);
        if (employees.isEmpty()) return null;

        BigDecimal min = employees.stream()
                .map(Employee::getSalary)
                .min(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);

        BigDecimal max = employees.stream()
                .map(Employee::getSalary)
                .max(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);

        BigDecimal avg = averageSalaryByCountry(employees);

        // Note: Using SalaryResponse fields creatively to match your previous logic
        return SalaryResponse.builder()
                .grossSalary(avg)
                .deduction(min)
                .netSalary(max)
                .build();
    }

    private BigDecimal averageSalaryByCountry(List<Employee> employees) {
        BigDecimal total = employees.stream()
                .map(Employee::getSalary)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return total.divide(BigDecimal.valueOf(employees.size()), 2, RoundingMode.HALF_UP);
    }
}