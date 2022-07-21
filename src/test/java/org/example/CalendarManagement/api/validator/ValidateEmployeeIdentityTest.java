package org.example.CalendarManagement.api.validator;

import org.example.CalendarManagement.api.request.RemoveEmployeeDataRequest;
import org.example.CalendarManagement.calendarpersistence.model.Employee;
import org.example.CalendarManagement.calendarpersistence.repository.EmployeeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ValidateEmployeeIdentityTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private ValidateEmployeeIdentity validateEmployeeIdentity;

    @Test
    public void validateEmployeeIdentityTest_employeeIdExists(){

        RemoveEmployeeDataRequest removeEmployeeDataRequest = new RemoveEmployeeDataRequest("XYZ-123");

        Mockito.when(employeeRepository.findById(removeEmployeeDataRequest.getIdentity())).
                thenReturn(Optional.of(new Employee(removeEmployeeDataRequest.getIdentity(), "shiavm", 1, "shivam@xyz.com")));

        ValidateResponse validateResponse = validateEmployeeIdentity.checkEmployeeId(removeEmployeeDataRequest.getIdentity());

        Assertions.assertNotNull(validateResponse);

        Assertions.assertTrue(validateResponse.isValid());
    }

    @Test
    public void validateEmployeeIdentityTest_employeeIdNotExists(){

        RemoveEmployeeDataRequest removeEmployeeDataRequest = new RemoveEmployeeDataRequest("XYZ-123");

        Mockito.when(employeeRepository.findById(removeEmployeeDataRequest.getIdentity())).
                thenReturn(Optional.empty());

        ValidateResponse validateResponse = validateEmployeeIdentity.checkEmployeeId(removeEmployeeDataRequest.getIdentity());

        Assertions.assertNotNull(validateResponse);

        Assertions.assertFalse(validateResponse.isValid());
    }

    @Test
    public void validateEmployeeIdentityTest_employeeEmailExists(){

        RemoveEmployeeDataRequest removeEmployeeDataRequest = new RemoveEmployeeDataRequest("shivam@xyz.com");

        Mockito.when(employeeRepository.findByEmail(removeEmployeeDataRequest.getIdentity())).
                thenReturn(Optional.of(new Employee("XYZ-123" , "Shivam" , 1 , removeEmployeeDataRequest.getIdentity())));

        ValidateResponse validateResponse = validateEmployeeIdentity.checkEmployeeEmail(removeEmployeeDataRequest.getIdentity());

        Assertions.assertNotNull(validateResponse);

        Assertions.assertTrue(validateResponse.isValid());
    }

    @Test
    public void validateEmployeeIdentityTest_employeeEmailNotExists(){

        RemoveEmployeeDataRequest removeEmployeeDataRequest = new RemoveEmployeeDataRequest("shivam@xyz.com");

        Mockito.when(employeeRepository.findByEmail(removeEmployeeDataRequest.getIdentity())).
                thenReturn(Optional.empty());

        ValidateResponse validateResponse = validateEmployeeIdentity.checkEmployeeEmail(removeEmployeeDataRequest.getIdentity());

        Assertions.assertNotNull(validateResponse);

        Assertions.assertFalse(validateResponse.isValid());
    }
}
