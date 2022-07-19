package org.example.CalendarManagement.calendarcontroller;

import org.example.CalendarManagement.api.request.AddEmployeeDataRequest;
import org.example.CalendarManagement.api.validator.ValidateEmployeeEmail;
import org.example.CalendarManagement.api.validator.ValidateOfficeId;
import org.example.CalendarManagement.api.validator.ValidateResponse;
import org.example.CalendarManagement.calendarfacade.EmployeeFacade;
import org.example.CalendarManagement.calendarpersistence.model.Employee;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class EmployeeControllerTest {

    @Mock
    EmployeeFacade employeeFacade;

    @Mock
    ValidateOfficeId validateOfficeId;

    @Mock
    ValidateEmployeeEmail validateEmployeeEmail;

    @InjectMocks
    EmployeeController employeeController;

    @Test
    public void employeeControllerTest_employeeSavedInFacade()
    {
        String employeeId = "CAP-1";
        String email = "s@cap.com";
        String name = "xyz";
        int officeID = 101;
        Employee employee = new Employee(employeeId,name,officeID,email);
        AddEmployeeDataRequest addEmployeeDataRequest = new AddEmployeeDataRequest(employeeId,name,email,officeID);
        Mockito.when(validateOfficeId.checkOfficeId(officeID)).thenReturn(new ValidateResponse("Valid OfficeID" , true));
        Mockito.when(validateEmployeeEmail.checkEmployeeEmailExist(email)).thenReturn(new ValidateResponse("valid Email", true));
        Mockito.when(employeeFacade.saveEmployee(addEmployeeDataRequest)).thenReturn(employee);
        ResponseEntity<Object> responseEntity = employeeController.saveEmployee(addEmployeeDataRequest);
        assertNotNull(responseEntity);
        assertEquals(201, responseEntity.getStatusCodeValue());
        assertEquals(employee , responseEntity.getBody());
    }

    @Test
    public void employeeControllerTest_employeeFailedEmailValidation()
    {
        String employeeId = "CAP-1";
        String email = "s@cap.com";
        String name = "xyz";
        int officeID = 101;
        Employee employee = new Employee(employeeId,name,officeID,email);
        AddEmployeeDataRequest addEmployeeDataRequest = new AddEmployeeDataRequest(employeeId,name,email,officeID);
        Mockito.when(validateOfficeId.checkOfficeId(officeID)).thenReturn(new ValidateResponse("Valid OfficeID" , true));
        Mockito.when(validateEmployeeEmail.checkEmployeeEmailExist(email)).thenReturn(new ValidateResponse("Employee with this email exists", false));
        Mockito.when(employeeFacade.saveEmployee(addEmployeeDataRequest)).thenReturn(employee);
        ResponseEntity<Object> responseEntity = employeeController.saveEmployee(addEmployeeDataRequest);
        assertNotNull(responseEntity);
        assertEquals(400, responseEntity.getStatusCodeValue());
        assertEquals("Employee with this email exists" , responseEntity.getBody());
    }

    @Test
    public void employeeControllerTest_employeeFailedOfficeIdValidation()
    {
        String employeeId = "CAP-1";
        String email = "s@cap.com";
        String name = "xyz";
        int officeID = 101;
        Employee employee = new Employee(employeeId,name,officeID,email);
        AddEmployeeDataRequest addEmployeeDataRequest = new AddEmployeeDataRequest(employeeId,name,email,officeID);
        Mockito.when(validateOfficeId.checkOfficeId(officeID)).thenReturn(new ValidateResponse("Office Information is Not Present" , false));
        Mockito.when(validateEmployeeEmail.checkEmployeeEmailExist(email)).thenReturn(new ValidateResponse("Employee with this email does not exist", true));
        Mockito.when(employeeFacade.saveEmployee(addEmployeeDataRequest)).thenReturn(employee);
        ResponseEntity<Object> responseEntity = employeeController.saveEmployee(addEmployeeDataRequest);
        assertNotNull(responseEntity);
        assertEquals(400, responseEntity.getStatusCodeValue());
        assertEquals("Office Information is Not Present" , responseEntity.getBody());
    }

}