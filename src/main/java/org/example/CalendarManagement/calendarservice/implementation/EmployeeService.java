package org.example.CalendarManagement.calendarservice.implementation;

import org.example.CalendarManagement.calendarpersistence.model.Employee;
import org.example.CalendarManagement.calendarpersistence.repository.EmployeeRepository;
import org.example.CalendarManagement.calendarservice.interfaces.EmployeeInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

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
        Optional<Employee> employee = employeeRepository.findById(id);
        employee.get().setDeleted(true);
        employeeRepository.save(employee.get());
        return employee.get();
    }
}
