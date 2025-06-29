package org.example.app.utils;

import org.example.app.dto.EmployeeDtoRequest;
import org.example.app.entity.Employee;

public class EmployeeMapper {

    public Employee requestToEntity(EmployeeDtoRequest request){
        Employee employee = new Employee();


        if(!request.firstName().isEmpty()){
            if(!request.firstName().isBlank()){
                employee.setFirstName(request.firstName());
            }
        }
        if(!request.lastName().isEmpty()){
            if(!request.lastName().isBlank()){
                employee.setLastName(request.lastName());
            }
        }
        if(!request.phone().isEmpty()){
            if(!request.phone().isBlank()){
                employee.setPhone(request.phone());
            }
        }

        return employee;
    }

}

