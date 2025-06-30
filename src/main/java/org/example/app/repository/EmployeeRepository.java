package org.example.app.repository;

import org.example.app.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("employeeRepository")
public interface EmployeeRepository extends JpaRepository<Employee,Long> {


    @Query("FROM Employee e ORDER BY e.id DESC")
    Optional<Employee> getLast();
}
