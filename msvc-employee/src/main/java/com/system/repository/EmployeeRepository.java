package com.system.repository;

import com.system.dto.EmployeeDTO;
import com.system.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long>{

    @Query("SELECT e FROM Employee e WHERE firstName=:firstName")
    public List<Employee> findByFirstName(@Param("firstName") String firstName);
}
