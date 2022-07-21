package org.example.CalendarManagement.calendarservice.implementation;

import org.example.CalendarManagement.calendarpersistence.model.Employee;
import org.example.CalendarManagement.calendarpersistence.repository.EmployeeRepository;
import org.example.CalendarManagement.calendarservice.interfaces.EmployeeInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class EmployeeService implements  EmployeeInterface
{
    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    @Transactional
    public Employee addEmployee(Employee employee)
    {
        return employeeRepository.save(employee);
    }

    @Override
    public Employee removeEmployeeById(String id) {
        return employeeRepository.deletedById(id);
    }

    @Override
    public Employee removeEmployeeByEmail(String email) {
        return employeeRepository.deleteByEmail(email);
    }
}
