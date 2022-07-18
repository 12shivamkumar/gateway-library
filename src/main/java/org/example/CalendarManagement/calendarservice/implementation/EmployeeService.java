package org.example.CalendarManagement.calendarservice.implementation;

import org.example.CalendarManagement.RestExceptionHandler.EmployeeAlreadyExistsException;
import org.example.CalendarManagement.calendarpersistence.model.Employee;
import org.example.CalendarManagement.calendarpersistence.repository.EmployeeRepository;
import org.example.CalendarManagement.calendarservice.interfaces.EmployeeInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

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
    public Employee removeEmployee(String empID) {
        return null;
    }

    @Override
    public int getOffice(List<String> empID) {
        return 0;
    }

    @Override
    public List<String> findEmployeeByID(List<String> empID) {
        return null;
    }
}
