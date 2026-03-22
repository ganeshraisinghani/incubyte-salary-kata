package com.incubyte.salary.dto;

import lombok.*;
import java.math.BigDecimal; // Add this import

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeDto {
    private String fullName;
    private String jobTitle;
    private String country;
    private BigDecimal salary; // Changed from Double to BigDecimal
}