package org.example.app.service.employee;


import org.example.app.dto.EmployeeDtoRequest;
import org.example.app.entity.Employee;
import org.example.app.entity.Position;
import org.example.app.repository.EmployeeRepository;
import org.example.app.repository.PositionRepository;
import org.example.app.utils.EmployeeMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service("employeeService")
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final PositionRepository positionRepository;
    private final EmployeeMapper employeeMapper;

    public EmployeeServiceImpl(
            @Qualifier("employeeRepository") EmployeeRepository employeeRepository,
            @Qualifier("positionRepository") PositionRepository positionRepository,
            EmployeeMapper employeeMapper
    ){
        this.employeeRepository = employeeRepository;
        this.positionRepository = positionRepository;
        this.employeeMapper = employeeMapper;
    }

    @Override
    @Transactional
    public Employee save(EmployeeDtoRequest request) {

        Objects.requireNonNull(request, "Parameter [request] must not be null");
        Employee employee = employeeMapper.requestToEntity(request);
        Long positionId = request.positionId();

        Position position = positionRepository.findById(positionId).orElse(null);

        if(position != null){
            employee.setPosition(position);
            return employeeRepository.save(employee);
        }
        return null;

    }

    @Override
    @Transactional
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
    @Transactional(readOnly = true)
    public List<Employee> readAll() {
        return employeeRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Employee readById(Long id) {
        Objects.requireNonNull(id, "Parameter [id] must not be null");
        Optional<Employee> employee = employeeRepository.findById(id);

        return employee.orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Employee readLast() {

        return employeeRepository.getLast().orElse(null);
    }

    @Override
    @Transactional
    public Employee updateById(Long id, EmployeeDtoRequest request) {
        Objects.requireNonNull(id,"Parameter [id] must not be null");
        Objects.requireNonNull(request,"Parameter [request] must not be null");


        var employee = employeeRepository.findById(id);

        if(employee.isPresent()){
            Employee employeeOld = employee.get();
            Employee employeeNew = employeeMapper.requestToEntity(request);

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
