package org.example.app.service.employee;


import org.example.app.dto.EmployeeDtoRequest;
import org.example.app.entity.Employee;
import org.example.app.entity.Position;
import org.example.app.repository.employee.EmployeeRepository;
import org.example.app.repository.position.PositionRepository;
import org.example.app.service.BaseService;
import org.example.app.utils.EmployeeMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service("employeeService")
public class EmployeeService implements BaseService<EmployeeDtoRequest, Employee> {

    private final EmployeeRepository employeeRepository;

    private final PositionRepository positionRepository;

    public EmployeeService(
            @Qualifier("employeeRepository") EmployeeRepository employeeRepository,
            @Qualifier("positionRepository") PositionRepository positionRepository
    ){
        this.employeeRepository = employeeRepository;
        this.positionRepository = positionRepository;
    }

    @Override
    public Employee save(EmployeeDtoRequest request) {

        Objects.requireNonNull(request, "Parameter [request] must not be null");
        Employee employee = new EmployeeMapper().requestToEntity(request);
        Long positionId = request.positionId();

        Position position = positionRepository.findById(positionId).orElse(null);

        if(!(position ==null)){
            employee.setPosition(position);
            return employeeRepository.save(employee);
        }
        return null;

    }

    @Override
    public boolean deleteById(Long id) {
        Objects.requireNonNull(id, "Parameter [id] must not be null");
        var employee = employeeRepository.findById(id);

        if(employee.isPresent()){
            employeeRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<Employee> readAll() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee readById(Long id) {
        Objects.requireNonNull(id, "Parameter [id] must not be null");
        Optional<Employee> employee = employeeRepository.findById(id);

        return employee.orElse(null);
    }

    @Override
    public Employee readLast() {
        Pageable pageable = (Pageable) PageRequest.of(0,1);
        return employeeRepository.getLast(pageable);
    }

    @Override
    public Employee updateById(Long id, EmployeeDtoRequest request) {
        Objects.requireNonNull(id,"Parameter [id] must not be null");
        Objects.requireNonNull(request,"Parameter [request] must not be null");


        var employee = employeeRepository.findById(id);

        if(employee.isPresent()){
            Employee employeeOld = employee.get();
            Employee employeeNew = new EmployeeMapper().requestToEntity(request);

            Position position = positionRepository.findById(request.positionId()).orElse(null);
            if(position == null){
                return null;
            }

            employeeNew.setPosition(position);

            employeeOld.setPhone(employeeNew.getPhone());
            employeeOld.setLastName(employeeNew.getLastName());
            employeeOld.setFirstName(employeeNew.getFirstName());
            employeeOld.setPosition(employeeNew.getPosition());

            employeeRepository.save(employeeOld);
            return employeeOld;
        }
        return null;

    }
}
