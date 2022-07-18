package org.example.CalendarManagement.calendarfacade;

import org.example.CalendarManagement.api.request.AddEmployeeDataRequest;
import org.example.CalendarManagement.api.validator.ValidateOfficeId;
import org.example.CalendarManagement.api.validator.ValidateResponse;
import org.example.CalendarManagement.calendarpersistence.model.Employee;
import org.example.CalendarManagement.calendarservice.implementation.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;

@Component
public class EmployeeFacade {

    @Autowired
    private EmployeeService employeeService;

    public Employee saveEmployee(AddEmployeeDataRequest request)
    {
        Employee employee = new Employee(request.getEmployeeId(),request.getName(), request.getOfficeId(), request.getEmail());
        employeeService.addEmployee(employee);
        return employee;
    }

}
