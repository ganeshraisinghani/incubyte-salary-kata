package com.incubyte.salary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the Incubyte Salary Kata Application.
 * * @SpringBootApplication enables:
 * 1. Configuration: Tags the class as a source of bean definitions.
 * 2. EnableAutoConfiguration: Tells Spring Boot to start adding beans based on classpath settings.
 * 3. ComponentScan: Automatically looks for Controllers, Services, and Repositories 
 * in the 'com.incubyte.salary' package and its sub-packages.
 */
@SpringBootApplication
public class SalaryKataApplication {

    public static void main(String[] args) {
        SpringApplication.run(SalaryKataApplication.class, args);
    }
}