# Salary Management System - Incubyte Kata

A robust Spring Boot application for managing employee data and calculating net salaries with regional tax deductions. This project was developed using **Test Driven Development (TDD)** and focuses on financial precision.

## 🚀 Features

* **Full CRUD for Employees:** Manage employee records (Create, Read, Update, Delete).
* **Net Salary Calculation:** Automated tax deductions based on country (e.g., India: 10%, US: 12%).
* **Regional Metrics:** View min, max, and average salary expenditures by country.
* **Role-based Analytics:** Calculate average salaries for specific job titles.

## 🛠️ Tech Stack

* **Java 17/22**
* **Spring Boot 3.x**
* **Spring Data JPA**
* **H2 Database** (In-memory for testing, file-based for dev)
* **Lombok** (For boilerplate reduction)
* **JUnit 5 & Mockito** (For Testing)
* **AssertJ** (For fluent assertions)

## 🎯 Clean Code & Design Choices

* **Financial Precision:** Used `BigDecimal` with `HALF_UP` rounding across the entire stack to avoid floating-point errors common with `Double`.
* **Null Safety:** Leveraged Java `Optional` in Service and Repository layers to prevent `NullPointerExceptions`.
* **Layered Architecture:** Strict separation between DTOs, Controllers, Services, and Entities.
* **TDD Approach:** Every feature was built by first defining a failing test (Red), implementing the logic (Green), and then optimizing the code (Refactor).

## 🚦 Getting Started

### Prerequisites
* Maven 3.6+
* JDK 17 or higher

### Installation
1. Clone the repository:
   ```bash
   git clone <your-repo-url>
   cd incubyte-salary-kata