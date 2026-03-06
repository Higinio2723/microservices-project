package com.system.service;

import com.system.dto.EmployeeDTO;

import java.util.List;

public interface EmployeeService {

    List<EmployeeDTO> getAllEmployees();
    List<EmployeeDTO> getAllEmployees(String name);
    EmployeeDTO getEmployeeById(Long id);
    EmployeeDTO createEmployee(EmployeeDTO employeeDto);
    EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDto);
    void deleteEmployee(Long id);

}
