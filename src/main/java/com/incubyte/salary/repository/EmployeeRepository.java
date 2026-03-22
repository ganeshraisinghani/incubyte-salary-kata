package com.incubyte.salary.repository;

import com.incubyte.salary.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByCountry(String country);
    List<Employee> findByJobTitle(String jobTitle);

}