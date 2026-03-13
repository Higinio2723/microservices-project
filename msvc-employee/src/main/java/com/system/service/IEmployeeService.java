package com.system.service;

import com.system.dto.EmployeeDTO;
import com.system.exception.NotFoundException;
import com.system.mapper.Mapper;
import com.system.model.Employee;
import com.system.repository.EmployeeRepository;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IEmployeeService  implements EmployeeService{

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        return employeeRepository.findAll()
                .stream()
                .map(Mapper::toDTO)
                .toList();
    }

    @Override
    public List<EmployeeDTO> getAllEmployees(String name) {

        return employeeRepository.findByFirstName(name)
                .stream()
                .map(Mapper::toDTO)
                .toList();

    }

    @Override
    public EmployeeDTO getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .map(Mapper::toDTO)
                .orElse(null);
    }

    @Override
    @Transactional
    public EmployeeDTO createEmployee(EmployeeDTO employeeDto) {
       var employee = Employee.builder()
                .firstName(employeeDto.getFirstName())
                .secondName(employeeDto.getSecondName())
                .firstLastName(employeeDto.getFirstLastName())
                .secondLastName(employeeDto.getSecondLastName())
                .sex(employeeDto.getSex())
                .dateBirth(employeeDto.getDateBirth())
                .age(employeeDto.getAge())
                .position(employeeDto.getPosition())
                .systemRegistrationDate(employeeDto.getSystemRegistrationDate())
                .status(employeeDto.isStatus())
                .build();
        employeeRepository.save(employee);

        return Mapper.toDTO(employee);
    }

    @Override
    @Transactional
    public EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDto) {
       Employee employee = employeeRepository.findById(id)
               .orElseThrow(()-> new NotFoundException("Empleado no encontrado"));
       employee.setFirstName(employeeDto.getFirstName());
       employee.setSecondName(employeeDto.getSecondName());
       employee.setFirstLastName(employeeDto.getFirstLastName());
       employee.setSecondLastName(employeeDto.getSecondLastName());
       employee.setSex(employeeDto.getSex());
       employee.setDateBirth(employeeDto.getDateBirth());
       employee.setAge(employeeDto.getAge());
       employee.setPosition(employeeDto.getPosition());
       employee.setStatus(employeeDto.isStatus());

        return Mapper.toDTO(employeeRepository.save(employee));
    }

    @Override
    public void deleteEmployee(Long id) {
        if(!employeeRepository.existsById(id)){
            throw new NotFoundException("Empleado no encontrado");
        }
        employeeRepository.deleteById(id);
    }
}
