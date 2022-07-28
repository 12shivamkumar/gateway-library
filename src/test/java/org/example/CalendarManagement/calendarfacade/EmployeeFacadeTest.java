package org.example.CalendarManagement.calendarfacade;

import org.apache.thrift.TException;
import org.example.CalendarManagement.api.Response;
import org.example.CalendarManagement.api.request.AddEmployeeDataRequest;
//import org.example.CalendarManagement.api.request.RemoveEmployeeDataRequest;
//import org.example.CalendarManagement.api.request.RemoveEmployeeDataRequest;
import org.example.CalendarManagement.api.request.RemoveEmployeeDataRequest;
import org.example.CalendarManagement.calendarpersistence.model.Employee;
import org.example.CalendarManagement.calendarservice.implementation.EmployeeService;
//import org.example.CalendarManagement.thriftclients.implementation.Client;
import org.example.CalendarManagement.thriftclients.implementation.MeetingServiceClientImpl;
import org.example.CalendarManagement.thriftclients.interfaces.MeetingServiceClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
/*@MockitoSettings(strictness = Strictness.LENIENT)*/
public class EmployeeFacadeTest {


    @Mock
    EmployeeService employeeService;
    @InjectMocks
    EmployeeFacade employeeFacade;

    @Mock
    MeetingServiceClient client;

    @Test
    public void employeeSavedInRepository(){
        String employeeId = "CAP-1";
        String email = "s@cap.com";
        String name = "xyz";
        int officeID = 101;
        Employee employee = new Employee(employeeId,name,officeID,email);
        Mockito.when(employeeService.addEmployee(Mockito.any(Employee.class))).thenReturn(employee);
        AddEmployeeDataRequest requestTesting = new AddEmployeeDataRequest(employeeId,name,email,officeID);
        Employee savedEmployee = employeeFacade.saveEmployee(requestTesting);
        assertNotNull(savedEmployee);
        assertFalse(savedEmployee.isDeleted());
    }

    @Test
    public void employeeRemovedByIdSuccessfully() throws TException {
        String id = "xyz-123";

        RemoveEmployeeDataRequest removeEmployeeDataRequest = new RemoveEmployeeDataRequest(id);

        Mockito.when(employeeService.removeEmployeeById(id)).
                thenReturn(new Employee(id, "tushar", 1, "tushar@gmail.com"));

        Response removedEmployeeResponse = employeeFacade.removeEmployee(removeEmployeeDataRequest);

        assertNotNull(removedEmployeeResponse);

        Employee employee = (Employee) removedEmployeeResponse.getData();

        assertEquals(id , employee.getId());
    }


    @Test
    public void removedEmployeeMeetingCancelFail() throws TException {
        String id = "xyz-123";
        String findBy = "id";
        RemoveEmployeeDataRequest removeEmployeeDataRequest = new RemoveEmployeeDataRequest(id);
        Mockito.when(employeeService.removeEmployeeById(id)).
                thenReturn(new Employee(id, "tushar", 1, "tushar@gmail.com"));
        Mockito.when(client.cancelMeetingForRemovedEmployee(id)).thenThrow(RuntimeException.class);
        Assertions.assertThrows(RuntimeException.class , () -> employeeFacade.removeEmployee(removeEmployeeDataRequest));
    }

    @Test
    public void  removedEmployeeUpdateStatusFail() throws TException {
        String id = "xyz-123";
        String findBy = "id";
        RemoveEmployeeDataRequest removeEmployeeDataRequest = new RemoveEmployeeDataRequest(id);
        Mockito.when(employeeService.removeEmployeeById(id)).
                thenReturn(new Employee(id, "tushar", 1, "tushar@gmail.com"));
        Mockito.when(client.updateStatusForRemovedEmployee(id)).thenThrow(RuntimeException.class);
        Assertions.assertThrows(RuntimeException.class , () -> employeeFacade.removeEmployee(removeEmployeeDataRequest));
    }
}