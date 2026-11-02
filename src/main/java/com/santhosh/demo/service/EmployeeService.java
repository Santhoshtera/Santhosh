package com.santhosh.demo.service;

import com.santhosh.demo.Entity.Employee;
import com.santhosh.demo.model.EmployeeRequest;
import com.santhosh.demo.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    // CREATE EMPLOYEE - POST
    public Employee createEmployee(EmployeeRequest request) {

        Employee employee = new Employee();

        employee.setName(request.getName());
        employee.setEmail(request.getEmail());
        employee.setDepartment(request.getDepartment());
        employee.setSalary(request.getSalary());

        return employeeRepository.save(employee);
    }

    // GET ALL EMPLOYEES - GET
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public List<Employee> getEmployeesByDepartment(String department) {

        List<Employee> employees = employeeRepository.findByDepartment(department);

        if (employees.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "No employees found in department: " + department
            );
        }

        return employees;
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Employee not found with ID: " + id
                        ));
    }

    // UPDATE EMPLOYEE - PUT
    public Employee updateEmployee(Long id, EmployeeRequest request) {

        Employee employee = getEmployeeById(id);

        employee.setName(request.getName());
        employee.setEmail(request.getEmail());
        employee.setDepartment(request.getDepartment());
        employee.setSalary(request.getSalary());

        return employeeRepository.save(employee);
    }

    // PARTIAL UPDATE EMPLOYEE - PATCH
    public Employee partialUpdateEmployee(Long id, Map<String, Object> updates) {

        Employee employee = getEmployeeById(id);

        updates.forEach((key, value) -> {

            switch (key) {

                case "name":
                    employee.setName((String) value);
                    break;

                case "email":
                    employee.setEmail((String) value);
                    break;

                case "department":
                    employee.setDepartment((String) value);
                    break;

                case "salary":
                    employee.setSalary(Double.parseDouble(value.toString()));
                    break;

                default:
                    throw new IllegalArgumentException("Invalid field: " + key);
            }
        });

        return employeeRepository.save(employee);
    }

    // DELETE EMPLOYEE - DELETE
    public void deleteEmployee(Long id) {

        Employee employee = getEmployeeById(id);

        employeeRepository.delete(employee);
    }
}

