package com.system.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String secondName;
    private String firstLastName;
    private String secondLastName;

    private int age;
    private String sex;
    private String dateBirth;

    private String position;
    @PrePersist
    public void prePresist(){
        systemRegistrationDate = LocalDate.now();
    }

    private LocalDate systemRegistrationDate;
    private boolean status;

}
