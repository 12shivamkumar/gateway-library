package org.example.CalendarManagement.calendarfacade;

import org.apache.thrift.TException;
import org.example.CalendarManagement.api.Response;
import org.example.CalendarManagement.api.request.AddEmployeeDataRequest;
//import org.example.CalendarManagement.api.request.RemoveEmployeeDataRequest;
//import org.example.CalendarManagement.api.request.RemoveEmployeeDataRequest;
import org.example.CalendarManagement.api.request.RemoveEmployeeDataRequest;
import org.example.CalendarManagement.calendarpersistence.model.Employee;
import org.example.CalendarManagement.calendarpersistence.repository.EmployeeRepository;
import org.example.CalendarManagement.calendarservice.implementation.EmployeeService;
//import org.example.CalendarManagement.thriftclients.implementation.Client;
import org.example.CalendarManagement.thriftclients.implementation.Client;
import org.example.CalendarThriftConfiguration.MeetingSvc;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
/*@MockitoSettings(strictness = Strictness.LENIENT)*/
public class EmployeeFacadeTest {


    @Mock
    EmployeeService employeeService;
    @InjectMocks
    EmployeeFacade employeeFacade;

    @Mock
    Client client;

    @Test
    public void employeeFacadeTest_employeeSavedInRepository(){
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
    public void employeeFacadeTest_employeeRemovedByIdSuccessfully() throws TException {
        String id = "xyz-123";

        RemoveEmployeeDataRequest removeEmployeeDataRequest = new RemoveEmployeeDataRequest(id);

        Mockito.when(employeeService.removeEmployeeById(id)).
                thenReturn(new Employee(id, "tushar", 1, "tushar@gmail.com"));

        Response removedEmployeeResponse = employeeFacade.removeEmployee(removeEmployeeDataRequest , "id");

        assertNotNull(removedEmployeeResponse);

        Employee employee = (Employee) removedEmployeeResponse.getData();

        assertEquals(id , employee.getId());
    }

    @Test
    public void employeeFacadeTest_employeeRemovedByEmailSuccessfully() throws TException {
        String email = "tushar@xyz.com";

        RemoveEmployeeDataRequest removeEmployeeDataRequest = new RemoveEmployeeDataRequest(email);

        Mockito.when(employeeService.removeEmployeeByEmail(email)).
                thenReturn(new Employee("xyz-123", "tushar", 1, email));

        Response removedEmployeeResponse = employeeFacade.removeEmployee(removeEmployeeDataRequest , "email");

        assertNotNull(removedEmployeeResponse);

        Employee employee = (Employee) removedEmployeeResponse.getData();

        assertEquals(email , employee.getEmail());
    }

    @Test
    public void employeeFacadeTest_removedEmployeeMeetingCancelFail() throws TException {
        String id = "xyz-123";
        String findBy = "id";
        RemoveEmployeeDataRequest removeEmployeeDataRequest = new RemoveEmployeeDataRequest(id);
        Mockito.when(employeeService.removeEmployeeById(id)).
                thenReturn(new Employee(id, "tushar", 1, "tushar@gmail.com"));
        Mockito.when(client.cancelMeetingForRemovedEmployee(id)).thenThrow(TException.class);
        Response removeEmployeeThriftFailResponse = employeeFacade.removeEmployee(removeEmployeeDataRequest,findBy);
        Assertions.assertEquals("Thrift Exception unable to update Meetings for removed employee",removeEmployeeThriftFailResponse.getError());
    }

    @Test
    public void  employeeFacadeTest_removedEmployeeUpdateStatusFail() throws TException {
        String id = "xyz-123";
        String findBy = "id";
        RemoveEmployeeDataRequest removeEmployeeDataRequest = new RemoveEmployeeDataRequest(id);
        Mockito.when(employeeService.removeEmployeeById(id)).
                thenReturn(new Employee(id, "tushar", 1, "tushar@gmail.com"));
        Mockito.when(client.updateStatusForRemovedEmployee(id)).thenThrow(TException.class);
        Response removeEmployeeThriftFailResponse = employeeFacade.removeEmployee(removeEmployeeDataRequest,findBy);
        Assertions.assertEquals("Thrift Exception unable to update Meetings for removed employee",removeEmployeeThriftFailResponse.getError());
    }
}