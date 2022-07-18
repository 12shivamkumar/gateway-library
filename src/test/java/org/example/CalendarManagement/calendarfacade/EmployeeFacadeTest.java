package org.example.CalendarManagement.calendarfacade;

import org.example.CalendarManagement.api.request.AddEmployeeDataRequest;
import org.example.CalendarManagement.calendarpersistence.model.Employee;
import org.example.CalendarManagement.calendarpersistence.repository.EmployeeRepository;
import org.example.CalendarManagement.calendarservice.implementation.EmployeeService;
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
@MockitoSettings(strictness = Strictness.LENIENT)
public class EmployeeFacadeTest {


    @Mock
    EmployeeService employeeService;
    @InjectMocks
    EmployeeFacade employeeFacade;

    @Test
    public void employeeFacadeTest_employeeSavedInRepository(){
        String employeeId = "CAP-1";
        String email = "s@cap.com";
        String name = "xyz";
        int officeID = 101;
        Employee employee = new Employee(employeeId,name,officeID,email);
        Mockito.when(employeeService.addEmployee(new Employee(employeeId,name,officeID,email))).thenReturn(employee);
        AddEmployeeDataRequest requestTesting = new AddEmployeeDataRequest(employeeId,name,email,officeID);
        Employee savedEmployee = employeeFacade.saveEmployee(requestTesting);
        assertNotNull(savedEmployee);
        assertFalse(savedEmployee.isDeleted());

    }

}