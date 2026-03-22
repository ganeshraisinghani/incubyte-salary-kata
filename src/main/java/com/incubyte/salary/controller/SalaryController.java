package com.incubyte.salary.controller;

import com.incubyte.salary.dto.SalaryResponse;
import com.incubyte.salary.service.SalaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/salary")
@RequiredArgsConstructor
public class SalaryController {

    private final SalaryService service;

    @GetMapping("/calculate/{employeeId}")
    public ResponseEntity<SalaryResponse> calculate(@PathVariable Long employeeId) {
        return service.calculateSalary(employeeId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/metrics/country/{country}")
    public ResponseEntity<SalaryResponse> metricsByCountry(@PathVariable String country) {
        SalaryResponse resp = service.metricsByCountry(country);
        return resp != null ? ResponseEntity.ok(resp) : ResponseEntity.notFound().build();
    }

    @GetMapping("/metrics/job/{jobTitle}")
    public ResponseEntity<BigDecimal> avgSalaryByJob(@PathVariable String jobTitle) {
        BigDecimal avg = service.averageSalaryByJob(jobTitle);
        return ResponseEntity.ok(avg); // Now returns BigDecimal for precision
    }
}