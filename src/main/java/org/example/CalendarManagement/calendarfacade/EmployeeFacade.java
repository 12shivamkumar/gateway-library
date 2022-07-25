package org.example.CalendarManagement.calendarfacade;

import org.apache.thrift.TException;
import org.example.CalendarManagement.api.Response;
import org.example.CalendarManagement.api.request.AddEmployeeDataRequest;
//import org.example.CalendarManagement.api.request.RemoveEmployeeDataRequest;
//import org.example.CalendarManagement.api.request.RemoveEmployeeDataRequest;
import org.example.CalendarManagement.api.request.RemoveEmployeeDataRequest;
import org.example.CalendarManagement.calendarpersistence.model.Employee;
import org.example.CalendarManagement.calendarservice.implementation.EmployeeService;
import org.example.CalendarManagement.thriftclients.interfaces.ThriftMeetingServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class EmployeeFacade {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private ThriftMeetingServiceClient meetingClient;

    public Employee saveEmployee(AddEmployeeDataRequest request)
    {
        Employee employee = new Employee(request.getEmployeeId(),request.getName(), request.getOfficeId(), request.getEmail());
        employeeService.addEmployee(employee);
        return employee;
    }
    @Transactional
    public Response removeEmployee(RemoveEmployeeDataRequest request) {
        Employee removedEmployee = null;

        Response removedEmployeeResponse = null;
        removedEmployee= employeeService.removeEmployeeById(request.getEmployeeId());
        removedEmployeeResponse = new Response(null, removedEmployee);
        try {
            meetingClient.cancelMeetingForRemovedEmployee(removedEmployee.getId());
            meetingClient.updateStatusForRemovedEmployee(removedEmployee.getId());
        }catch (TException ex){
            throw new RuntimeException(ex.getMessage());
        }
        return removedEmployeeResponse;
    }

}
