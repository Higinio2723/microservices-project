package com.system.controller;

import com.system.dto.EmployeeDTO;
import com.system.mapper.Mapper;
import com.system.model.Employee;
import com.system.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;


@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;


    @Test
    void getAllEmployees() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/employees"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());

    }

    @Test
    void getAllEmployeesByName() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/employees/search")
                        .param("name", "John"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());
    }


    @Test
    void createEmployee() throws Exception {
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setFirstName("John");
        employee.setSecondName("Doe");
        employee.setFirstLastName("GUNNES");
        employee.setSecondLastName("");
        employee.setAge(20);
        employee.setSex("H");
        employee.setDateBirth("");
        employee.setPosition("Departamento");
        employee.setStatus(true);

        when(employeeService.createEmployee(any(EmployeeDTO.class)))
                .thenReturn(Mapper.toDTO(employee));

        StringBuilder data = new StringBuilder();
        data.append("{");
        data.append("\"firstName\": \"John\",");
        data.append("\"secondName\": \"Doe\",");
        data.append("\"firstLastName\": \"GUNNES\",");
        data.append("\"secondLastName\": \"\",");
        data.append("\"age\": 20,");
        data.append("\"sex\": \"H\",");
        data.append("\"dateBirth\": \"\",");
        data.append("\"position\": \"Departamento\",");
        data.append("\"status\": \"true\"");
        data.append("}");

        mockMvc.perform(MockMvcRequestBuilders.post("/employees")
                        .content(data.toString())
                        .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
              .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("John"));

    }

    @Test
    void updateEmployee() throws Exception{
        Long id = 1L;

        Employee employee = new Employee();
        employee.setId(1L);
        employee.setFirstName("John");
        employee.setSecondName("Doe");
        employee.setFirstLastName("GUNNES");
        employee.setSecondLastName("");
        employee.setAge(20);
        employee.setSex("H");
        employee.setDateBirth("");
        employee.setPosition("Departamento");
        employee.setStatus(true);
        when(employeeService.getEmployeeById(id))
                .thenReturn(Mapper.toDTO(employee));

        when(employeeService.updateEmployee(eq(id),any()))
                .thenReturn(Mapper.toDTO(employee));

        StringBuilder data = new StringBuilder();
        data.append("{");
        data.append("\"firstName\": \"John\",");
        data.append("\"secondName\": \"Doe\",");
        data.append("\"firstLastName\": \"GONZALEZ\",");
        data.append("\"secondLastName\": \"\",");
        data.append("\"age\": 20,");
        data.append("\"sex\": \"H\",");
        data.append("\"dateBirth\": \"\",");
        data.append("\"position\": \"Departamento\",");
        data.append("\"status\": \"true\"");
        data.append("}");

        mockMvc.perform(MockMvcRequestBuilders.put("/employees/{id}", 1)
                        .content(data.toString())
                        .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firStName").value("John"));
    }

    @Test
    void deleteEmployee() throws Exception{
        Long id = 1L;

        Employee employee = new Employee();
        employee.setId(1L);
        employee.setFirstName("John");
        employee.setSecondName("Doe");
        employee.setFirstLastName("GUNNES");
        employee.setSecondLastName("");
        employee.setAge(20);
        employee.setSex("H");
        employee.setDateBirth("");
        employee.setPosition("Departamento");
        employee.setStatus(true);
        when(employeeService.getEmployeeById(id))
                .thenReturn(Mapper.toDTO(employee));

        doNothing().when(employeeService).deleteEmployee(id);

        mockMvc.perform(MockMvcRequestBuilders.delete("/employees/{id}", 1L ))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

    }



}