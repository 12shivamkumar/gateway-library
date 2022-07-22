package org.example.CalendarManagement.calendarcontroller;

import org.example.CalendarManagement.api.Response;
import org.example.CalendarManagement.api.request.AddEmployeeDataRequest;
//import org.example.CalendarManagement.api.request.RemoveEmployeeDataRequest;
//import org.example.CalendarManagement.api.request.RemoveEmployeeDataRequest;
import org.example.CalendarManagement.api.request.RemoveEmployeeDataRequest;
import org.example.CalendarManagement.api.validator.ValidateEmployeeEmail;
//import org.example.CalendarManagement.api.validator.ValidateEmployeeIdentity;
import org.example.CalendarManagement.api.validator.ValidateEmployeeIdentity;
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
class EmployeeControllerTest {

    @Mock
    EmployeeFacade employeeFacade;
    @Mock
    ValidateEmployeeIdentity validateEmployeeIdentity;

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
        ResponseEntity<Response> responseEntity = employeeController.saveEmployee(addEmployeeDataRequest);
        assertNotNull(responseEntity);
        assertEquals(201, responseEntity.getStatusCodeValue());
        assertNotNull(responseEntity.getBody());
        assertEquals(employee , responseEntity.getBody().getData());
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
        Mockito.when(validateEmployeeEmail.checkEmployeeEmailExist(email)).thenReturn(new ValidateResponse("Employee with this email exists", false));
        ResponseEntity<Response> responseEntity = employeeController.saveEmployee(addEmployeeDataRequest);
        assertNotNull(responseEntity);
        assertEquals(400, responseEntity.getStatusCodeValue());
        assertNotNull(responseEntity.getBody());
        assertEquals("Employee with this email exists" , responseEntity.getBody().getError());
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
        ResponseEntity<Response> responseEntity = employeeController.saveEmployee(addEmployeeDataRequest);
        assertNotNull(responseEntity);
        assertEquals(400, responseEntity.getStatusCodeValue());
        assertNotNull(responseEntity.getBody());
        assertEquals("Office Information is Not Present" , responseEntity.getBody().getError());
    }
    @Test
    public void employeeControllerTest_removeEmployeeFailedIdValidation(){
        String id = "xyz-123";
        String findBy = "id";
        Mockito.when(validateEmployeeIdentity.checkEmployeeId(id)).thenReturn(new ValidateResponse("Employee does not exists", false));
        RemoveEmployeeDataRequest removeEmployeeDataRequest = new RemoveEmployeeDataRequest(id);
        ResponseEntity<Response> responseEntity = employeeController.removeEmployee(id,findBy);
        assertNotNull(responseEntity);
        assertEquals(400, responseEntity.getStatusCodeValue());
    }
    @Test
    public void employeeControllerTest_removeEmployeeFailedEmailValidation(){
        String email = "tushar@xyz.com";
        String findBy = "email";
        Mockito.when(validateEmployeeIdentity.checkEmployeeEmail(email)).
                thenReturn(new ValidateResponse("Employee does not exists" , false));
        RemoveEmployeeDataRequest removeEmployeeDataRequest = new RemoveEmployeeDataRequest(email);
        ResponseEntity<Response> responseEntity = employeeController.removeEmployee(email,findBy);
        assertNotNull(responseEntity);
        assertEquals(400, responseEntity.getStatusCodeValue());

    }
    @Test
    public void employeeControllerTest_removeEmployeeIncorrectFindByParameter(){
        String email = "tushar@xyz.com";
        String findBy = "IncorrectParameter";
        ResponseEntity<Response> responseEntity = employeeController.removeEmployee(email,findBy);
        assertNotNull(responseEntity);
        assertEquals(400,responseEntity.getStatusCodeValue());
    }

    @Test
    public void employeeControllerTest_removeEmployeeByIdSuccessfully(){
        String id = "xyz-123";
        String findBy = "id";
        RemoveEmployeeDataRequest removeEmployeeDataRequest = new RemoveEmployeeDataRequest(id);
        Mockito.when(validateEmployeeIdentity.checkEmployeeId(id)).
                thenReturn(new ValidateResponse("Employee Exists", true));
        Mockito.when(employeeFacade.removeEmployee(Mockito.any(RemoveEmployeeDataRequest.class),Mockito.anyString())).
                thenReturn(new Response(null, new Employee(id,"tushar",2,"tushar@xyz.com")));
        ResponseEntity<Response> responseEntity = employeeController.removeEmployee(id,findBy);
        assertNotNull(responseEntity);
        assertEquals(200,responseEntity.getStatusCodeValue());
    }

    @Test
    public void employeeControllerTest_removeEmployeeByEmailSuccessfully(){
        String email = "tushar@xyz.com";
        String findBy = "email";
        RemoveEmployeeDataRequest removeEmployeeDataRequest = new RemoveEmployeeDataRequest(email);
        Mockito.when(validateEmployeeIdentity.checkEmployeeEmail(email)).
                thenReturn(new ValidateResponse("Employee Exists", true));
        Mockito.when(employeeFacade.removeEmployee(Mockito.any(RemoveEmployeeDataRequest.class),Mockito.anyString())).
                thenReturn(new Response(null, new Employee("xyz-12","tushar",2,email)));
        ResponseEntity<Response> responseEntity = employeeController.removeEmployee(email,findBy);
        assertNotNull(responseEntity);
        assertEquals(200,responseEntity.getStatusCodeValue());
    }

}