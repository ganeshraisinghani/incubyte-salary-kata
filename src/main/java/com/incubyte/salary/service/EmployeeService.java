package com.incubyte.salary.service;

import com.incubyte.salary.dto.EmployeeDto;
import com.incubyte.salary.model.Employee;
import com.incubyte.salary.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository repository;

    public Employee create(EmployeeDto dto) {
        Employee employee = Employee.builder()
                .fullName(dto.getFullName())
                .jobTitle(dto.getJobTitle())
                .country(dto.getCountry())
                .salary(dto.getSalary()) // Now correctly handling BigDecimal
                .build();
        return repository.save(employee);
    }

    public Optional<Employee> get(Long id) {
        return repository.findById(id);
    }

    public List<Employee> getAll() {
        return repository.findAll();
    }

    public Optional<Employee> update(Long id, EmployeeDto updatedDto) {
        return repository.findById(id)
                .map(emp -> {
                    emp.setFullName(updatedDto.getFullName());
                    emp.setJobTitle(updatedDto.getJobTitle());
                    emp.setCountry(updatedDto.getCountry());
                    emp.setSalary(updatedDto.getSalary());
                    return repository.save(emp);
                });
    }

    public boolean delete(Long id) {
        return repository.findById(id).map(emp -> {
            repository.delete(emp);
            return true;
        }).orElse(false);
    }
}