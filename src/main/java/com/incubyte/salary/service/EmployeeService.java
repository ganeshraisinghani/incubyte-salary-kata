package com.incubyte.salary.service;

import com.incubyte.salary.model.Employee;
import com.incubyte.salary.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository repository;

    public Employee create(Employee employee) {
        return repository.save(employee);
    }

    public Employee get(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<Employee> getAll() {
        return repository.findAll();
    }

    public Employee update(Long id, Employee updated) {
        return repository.findById(id)
                .map(emp -> {
                    emp.setFullName(updated.getFullName());
                    emp.setJobTitle(updated.getJobTitle());
                    emp.setCountry(updated.getCountry());
                    emp.setSalary(updated.getSalary());
                    return repository.save(emp);
                }).orElse(null);
    }

    public Employee delete(Long id) {
        return repository.findById(id).map(emp -> {
            repository.delete(emp);
            return emp;
        }).orElse(null);
    }
}