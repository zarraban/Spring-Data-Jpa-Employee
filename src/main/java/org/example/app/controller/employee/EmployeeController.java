package org.example.app.controller.employee;


import org.example.app.dto.EmployeeDtoRequest;
import org.example.app.dto.error.AppError;
import org.example.app.entity.Employee;
import org.example.app.service.employee.EmployeeService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;


@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(
            @Qualifier("employeeService") EmployeeService employeeService
    ){
        this.employeeService =  employeeService;
    }


    @GetMapping
    public ResponseEntity<?> getAllEmployees(){
        try {
            List<Employee> list = employeeService.readAll();

            if (list.isEmpty()) {
                return  formResponse("List is empty!");
            }

            return new ResponseEntity<>(list, HttpStatus.OK);

        }
        catch (Exception e){
            return new ResponseEntity<>(
                    new AppError(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping
    public ResponseEntity<?> createEmployee(@RequestBody EmployeeDtoRequest request){
        try {

            Employee employee = employeeService.save(request);

            if(employee==null){
                return new ResponseEntity<>(
                        new AppError("position_id is invalid", HttpStatus.OK.value()),
                        HttpStatus.OK);
            }
            return new ResponseEntity<>(employee, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new AppError(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR.value()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    @RequestMapping("/{id}")
    public ResponseEntity<?> getEmployeeById(@PathVariable("id") Long id){
        try {
            Employee employee = employeeService.readById(id);

            if (employee == null) {
                return  formResponse("No such Employee with id = " + id);

            }

            return new ResponseEntity<>(employee, HttpStatus.OK);

        }
        catch (Exception e){
            return new ResponseEntity<>(
                    new AppError(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }




    @DeleteMapping
    @RequestMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable("id") Long id){

        try {
            if(employeeService.deleteById(id)){
                return formResponse("Successfully deleted!");
            }

            return formResponse("Deleting failed!");

        } catch (Exception e) {
            return new ResponseEntity<>(new AppError(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping
    @RequestMapping("/last")
    public ResponseEntity<?> getLastEmployee(){
        try{
            Employee employee = employeeService.readLast();

            if(!(employee ==null)){
                return new ResponseEntity<>(employee, HttpStatus.OK);
            }
            return formResponse("List is empty!");


        } catch (Exception e) {
            return new ResponseEntity<>(new AppError(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }



    @PutMapping
    @RequestMapping("/{id}")
    public ResponseEntity<?> updateEmployeeById(@PathVariable("id")Long id, @RequestBody EmployeeDtoRequest request){
        try {
            Employee employee = employeeService.updateById(id, request);

            if(employee == null){
                return formResponse("smth went wrong(employee with id " + id + " doesn't exist!");
            }
            return new ResponseEntity<>(employee, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(new AppError(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }



    private ResponseEntity<?> formResponse(String message){
        return new ResponseEntity<>(new HashMap<String, String>() {
            {
                put("message", message);
                put("statusCode", HttpStatus.OK.name());
            }
        }, HttpStatus.OK);
    }
}
