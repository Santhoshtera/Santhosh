package com.santhosh.demo.repository;

import com.santhosh.demo.Entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long>
{
    List<Employee> findByDepartment(String department);
}
