package com.incubyte.salary.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeDto {
    private String fullName;
    private String jobTitle;
    private String country;
    private Double salary;
}