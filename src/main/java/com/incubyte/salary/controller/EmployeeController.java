package com.incubyte.salary.controller;

import com.incubyte.salary.model.Employee;
import com.incubyte.salary.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService service;

    @PostMapping
    public Employee create(@RequestBody Employee emp) {
        return service.create(emp);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> get(@PathVariable Long id) {
        Employee emp = service.get(id);
        return emp != null ? ResponseEntity.ok(emp) : ResponseEntity.notFound().build();
    }

    @GetMapping
    public List<Employee> getAll() {
        return service.getAll();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> update(@PathVariable Long id, @RequestBody Employee emp) {
        Employee updated = service.update(id, emp);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Employee> delete(@PathVariable Long id) {
        Employee deleted = service.delete(id);
        return deleted != null ? ResponseEntity.ok(deleted) : ResponseEntity.notFound().build();
    }
}