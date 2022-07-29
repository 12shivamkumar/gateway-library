package org.example.CalendarManagement.api.validator;

//import org.example.CalendarManagement.api.request.RemoveEmployeeDataRequest;
//import org.example.CalendarManagement.api.request.RemoveEmployeeDataRequest;
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
public class ValidateEmployeeIdTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private ValidateEmployeeId validateEmployeeId;

    @Test
    public void employeeIdIllegal(){
        RemoveEmployeeDataRequest removeEmployeeDataRequest = new RemoveEmployeeDataRequest("XYZ");

        ValidateResponse validateResponse = validateEmployeeId.checkEmployeeId(removeEmployeeDataRequest.getEmployeeId());
        Assertions.assertNotNull(validateResponse);
        Assertions.assertFalse(validateResponse.isValid());
    }

    @Test
    public void employeeIdExists(){

        RemoveEmployeeDataRequest removeEmployeeDataRequest = new RemoveEmployeeDataRequest("XYZ-123");

        Mockito.when(employeeRepository.findById(removeEmployeeDataRequest.getEmployeeId())).
                thenReturn(Optional.of(new Employee(removeEmployeeDataRequest.getEmployeeId(), "shiavm", 1, "shivam@xyz.com")));

        ValidateResponse validateResponse = validateEmployeeId.checkEmployeeId(removeEmployeeDataRequest.getEmployeeId());

        Assertions.assertNotNull(validateResponse);

        Assertions.assertTrue(validateResponse.isValid());
    }

    @Test
    public void employeeIdNotExists(){

        RemoveEmployeeDataRequest removeEmployeeDataRequest = new RemoveEmployeeDataRequest("XYZ-123");

        Mockito.when(employeeRepository.findById(removeEmployeeDataRequest.getEmployeeId())).
                thenReturn(Optional.empty());

        ValidateResponse validateResponse = validateEmployeeId.checkEmployeeId(removeEmployeeDataRequest.getEmployeeId());

        Assertions.assertNotNull(validateResponse);

        Assertions.assertFalse(validateResponse.isValid());
    }

}
