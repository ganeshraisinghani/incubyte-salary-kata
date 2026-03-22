package com.incubyte.salary.dto;

import lombok.*;
import java.math.BigDecimal; // Import for precision

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalaryResponse {
    private BigDecimal grossSalary;
    private BigDecimal deduction;
    private BigDecimal netSalary;
}