package org.example.CalendarManagement.calendarfacade;

import org.apache.thrift.TException;
import org.example.CalendarManagement.api.request.AddEmployeeDataRequest;
import org.example.CalendarManagement.api.request.RemoveEmployeeDataRequest;
import org.example.CalendarManagement.api.validator.ValidateOfficeId;
import org.example.CalendarManagement.api.validator.ValidateResponse;
import org.example.CalendarManagement.calendarpersistence.model.Employee;
import org.example.CalendarManagement.calendarservice.implementation.EmployeeService;
import org.example.CalendarManagement.thriftclients.implementation.Client;
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

    @Autowired
    Client thriftClient;

    public Employee saveEmployee(AddEmployeeDataRequest request)
    {
        Employee employee = new Employee(request.getEmployeeId(),request.getName(), request.getOfficeId(), request.getEmail());
        employeeService.addEmployee(employee);
        return employee;
    }

    public Employee removeEmployee(RemoveEmployeeDataRequest request,  String findEmployeeBy){
        Employee removedEmployee = null;

        if(findEmployeeBy.equals("id")) {
            removedEmployee= employeeService.removeEmployeeById(request.getIdentity());
        }

        if(findEmployeeBy.equals("email")) {
            removedEmployee= employeeService.removeEmployeeByEmail(request.getIdentity());
        }

        try {
            thriftClient.cancelMeetingForRemovedEmployee(removedEmployee.getId());

            thriftClient.updateStatusForRemovedEmployee(removedEmployee.getId());
        }catch (TException e) {
            System.out.println(e.getMessage());
        }

        return removedEmployee;
    }

}
