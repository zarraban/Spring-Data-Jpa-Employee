package org.example.app.utils;

import org.example.app.dto.EmployeeDtoRequest;
import org.example.app.entity.Employee;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {

    public Employee requestToEntity(EmployeeDtoRequest request){
        Employee employee = new Employee();

        if(request.firstName() != null && !request.firstName().isBlank()){
            employee.setFirstName(request.firstName());
        }
        if(request.lastName() != null && !request.lastName().isBlank()){
            employee.setLastName(request.lastName());
        }
        if(request.phone() != null && !request.phone().isBlank()){
            employee.setPhone(request.phone());
        }

        return employee;
    }

}
