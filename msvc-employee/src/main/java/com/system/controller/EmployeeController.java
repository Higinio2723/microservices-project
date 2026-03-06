package com.system.controller;

import com.system.dto.EmployeeDTO;
import com.system.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/")
@Tag(name = "Employee", description = "API to gestion on Employee")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @GetMapping("employees")
    @Operation(summary = "Get all employees", description = "Get a list of all employees")
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @GetMapping("employees/search")
    @Operation(summary = "Search employees by name", description = "Get a list of employees that match the search criteria")
    public ResponseEntity<List<EmployeeDTO>> getAllEmployeesByName(@RequestParam String name) {
        return ResponseEntity.ok(employeeService.getAllEmployees(name));
    }

    @PostMapping("employees")
    @Operation(summary = "Create a new employee", description = "Create a new employee with the provided information")
    public ResponseEntity<EmployeeDTO> createEmployee(@RequestBody EmployeeDTO employeeDto) {
        EmployeeDTO created = employeeService.createEmployee(employeeDto);
        return ResponseEntity.created(URI.create("/employees" + created.getId())).body(created);
    }

    @PutMapping("employees/{id}")
    @Operation(summary = "Update an existing employee", description = "Update the information of an existing employee by ID")
    public ResponseEntity<EmployeeDTO> updateEmployee(@PathVariable Long id,
                                                      @RequestBody EmployeeDTO employeeDto) {
        return ResponseEntity.ok(employeeService.updateEmployee(id, employeeDto));
    }

    @DeleteMapping("employees/{id}")
    @Operation(summary = "Delete an employee", description = "Delete an existing employee by ID")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

}
