import com.fasterxml.jackson.databind.ObjectMapper;
import com.incubyte.salary.SalaryKataApplication;
import com.incubyte.salary.controller.EmployeeController;
import com.incubyte.salary.dto.EmployeeDto;
import com.incubyte.salary.model.Employee;
import com.incubyte.salary.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmployeeController.class)
@ContextConfiguration(classes = SalaryKataApplication.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateEmployee() throws Exception {
        BigDecimal salary = new BigDecimal("1000.00");
        EmployeeDto dto = new EmployeeDto("Alice", "Developer", "India", salary);
        Employee savedEmployee = Employee.builder().id(1L).fullName("Alice").salary(salary).build();

        Mockito.when(service.create(any(EmployeeDto.class))).thenReturn(savedEmployee);

        mockMvc.perform(post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value("Alice"))
                .andExpect(jsonPath("$.salary").value(1000.00));
    }

    @Test
    void shouldGetEmployeeById() throws Exception {
        Employee emp = Employee.builder().id(1L).fullName("Alice").salary(new BigDecimal("1000.00")).build();

        Mockito.when(service.get(1L)).thenReturn(Optional.of(emp));

        mockMvc.perform(get("/employees/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value("Alice"));
    }

    @Test
    void shouldReturn404WhenEmployeeNotFound() throws Exception {
        Mockito.when(service.get(2L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/employees/2"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldGetAllEmployees() throws Exception {
        List<Employee> employees = List.of(
                Employee.builder().id(1L).fullName("Alice").salary(new BigDecimal("1000.00")).build(),
                Employee.builder().id(2L).fullName("Bob").salary(new BigDecimal("2000.00")).build()
        );

        Mockito.when(service.getAll()).thenReturn(employees);

        mockMvc.perform(get("/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void shouldUpdateEmployee() throws Exception {
        BigDecimal updatedSalary = new BigDecimal("1200.00");
        EmployeeDto dto = new EmployeeDto("Alice Updated", "Developer", "India", updatedSalary);
        Employee updatedEmployee = Employee.builder().id(1L).fullName("Alice Updated").salary(updatedSalary).build();

        Mockito.when(service.update(eq(1L), any(EmployeeDto.class))).thenReturn(Optional.of(updatedEmployee));

        mockMvc.perform(put("/employees/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.salary").value(1200.00));
    }

    @Test
    void shouldDeleteEmployeeAndReturnNoContent() throws Exception {
        Mockito.when(service.delete(1L)).thenReturn(true);

        mockMvc.perform(delete("/employees/1"))
                .andExpect(status().isNoContent());
    }
}