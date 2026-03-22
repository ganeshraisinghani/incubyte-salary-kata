package com.incubyte.salary.service;

import com.incubyte.salary.model.Employee;
import com.incubyte.salary.repository.EmployeeRepository;
import com.incubyte.salary.dto.SalaryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SalaryService {

    private final EmployeeRepository repository;

    public SalaryResponse calculateSalary(Long employeeId) {
        Employee emp = repository.findById(employeeId).orElse(null);
        if (emp == null) return null;

        double deductionRate = switch (emp.getCountry()) {
            case "India" -> 0.10;
            case "United States" -> 0.12;
            default -> 0.0;
        };

        double deduction = emp.getSalary() * deductionRate;
        double netSalary = emp.getSalary() - deduction;

        return SalaryResponse.builder()
                .grossSalary(emp.getSalary())
                .deduction(deduction)
                .netSalary(netSalary)
                .build();
    }

    public Double averageSalaryByJob(String jobTitle) {
        List<Employee> employees = repository.findByJobTitle(jobTitle);
        if (employees.isEmpty()) return 0.0;
        return employees.stream().mapToDouble(Employee::getSalary).average().orElse(0);
    }

    public SalaryResponse metricsByCountry(String country) {
        List<Employee> employees = repository.findByCountry(country);
        if (employees.isEmpty()) return null;

        double min = employees.stream().mapToDouble(Employee::getSalary).min().orElse(0);
        double max = employees.stream().mapToDouble(Employee::getSalary).max().orElse(0);
        double avg = employees.stream().mapToDouble(Employee::getSalary).average().orElse(0);

        return SalaryResponse.builder()
                .grossSalary(avg)
                .deduction(min)
                .netSalary(max)
                .build();
    }
}