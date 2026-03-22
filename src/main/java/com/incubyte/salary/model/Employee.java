package com.incubyte.salary.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "employees")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fullName;

    private String jobTitle;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private BigDecimal salary; // Use BigDecimal for financial precision
}