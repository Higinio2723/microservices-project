package com.system.mapper;

import com.system.dto.EmployeeDTO;
import com.system.model.Employee;

public class Mapper {

    //Mapeo de Employee a EmployeeDTO
    public static EmployeeDTO toDTO(Employee e) {
        if (e == null) return null;

        return EmployeeDTO.builder()
                .id(e.getId())
                .firstName(e.getFirstName())
                .secondName(e.getSecondName())
                .firstLastName(e.getFirstLastName())
                .secondLastName(e.getSecondLastName())
                .age(e.getAge())
                .sex(e.getSex())
                .dateBirth(e.getDateBirth())
                .position(e.getPosition())
                .systemRegistrationDate(e.getSystemRegistrationDate())
                .status(e.isStatus())
                .build();
    }
}
