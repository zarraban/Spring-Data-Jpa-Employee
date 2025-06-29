package org.example.app.repository.employee;

import org.example.app.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;

@Repository("employeeRepository")
public interface EmployeeRepository extends JpaRepository<Employee,Long> {


    @Query("FROM Employee e ORDER BY e.id DESC")
    Employee getLast(Pageable pageable);
}
