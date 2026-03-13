package com.system.dto;

import lombok.*;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeDTO {

    private Long id;

    @NotBlank(message = "El primer nombre es obligatorio")
    @Size(min = 2, max = 50, message = "El primer nombre debe tener entre 2 y 50 caracteres")
    private String firstName;

    private String secondName;

    @NotBlank(message = "El primer apellido es obligatorio")
    @Size(min = 2, max = 50, message = "El primer apellido debe tener entre 2 y 50 caracteres")
    private String firstLastName;

    private String secondLastName;

    @Min(value = 18, message = "La edad mínima es 18 años")
    @Max(value = 100, message = "La edad máxima es 100 años")
    private int age;

    @NotBlank(message = "El sexo es obligatorio")
    private String sex;

    @NotBlank(message = "La fecha de nacimiento es obligatoria")
    private String dateBirth;

    @NotBlank(message = "El puesto es obligatorio")
    private String position;

    private LocalDate systemRegistrationDate;
    private boolean status;
}
