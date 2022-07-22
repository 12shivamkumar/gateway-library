package org.example.CalendarManagement.calendarfacade;

import org.example.CalendarManagement.api.Response;
import org.example.CalendarManagement.api.request.AddEmployeeDataRequest;
//import org.example.CalendarManagement.api.request.RemoveEmployeeDataRequest;
//import org.example.CalendarManagement.api.request.RemoveEmployeeDataRequest;
import org.example.CalendarManagement.api.request.RemoveEmployeeDataRequest;
import org.example.CalendarManagement.calendarpersistence.model.Employee;
import org.example.CalendarManagement.calendarservice.implementation.EmployeeService;
//import org.example.CalendarManagement.thriftclients.implementation.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

    public Response removeEmployee(RemoveEmployeeDataRequest request, String findEmployeeBy){
        Employee removedEmployee = null;

        Response removedEmployeeResponse = null;

        if(findEmployeeBy.equals("id")) {
            removedEmployee= employeeService.removeEmployeeById(request.getIdentity());
        }

        if(findEmployeeBy.equals("email")) {
            removedEmployee= employeeService.removeEmployeeByEmail(request.getIdentity());
        }

        removedEmployeeResponse = new Response(null, removedEmployee);

        return removedEmployeeResponse;
    }

}
