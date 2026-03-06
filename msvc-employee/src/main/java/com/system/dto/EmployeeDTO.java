package com.system.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeDTO {

    private Long id;
    private String firstName;
    private String secondName;
    private String firstLastName;
    private String secondLastName;
    private int age;
    private String sex;
    private String dateBirth;
    private String position;
    private LocalDate systemRegistrationDate;
    private boolean status;
}
