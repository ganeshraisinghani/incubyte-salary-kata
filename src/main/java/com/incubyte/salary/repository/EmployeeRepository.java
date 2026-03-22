package com.incubyte.salary.repository;

import com.incubyte.salary.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for Employee entities.
 * Extends JpaRepository to provide standard CRUD operations.
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // Useful for regional tax calculations in the Salary Kata
    List<Employee> findByCountry(String country);

    // Useful for role-based bonus or deduction logic
    List<Employee> findByJobTitle(String jobTitle);
}