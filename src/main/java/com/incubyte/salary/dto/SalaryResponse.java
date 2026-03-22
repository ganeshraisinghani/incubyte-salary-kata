package com.incubyte.salary.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalaryResponse {
    private Double grossSalary;
    private Double deduction;
    private Double netSalary;
}