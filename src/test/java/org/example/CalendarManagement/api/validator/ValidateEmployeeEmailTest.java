package org.example.CalendarManagement.api.validator;

import org.example.CalendarManagement.api.request.AddEmployeeDataRequest;
import org.example.CalendarManagement.calendarpersistence.model.Employee;
import org.example.CalendarManagement.calendarpersistence.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class ValidateEmployeeEmailTest {
    @Mock
    EmployeeRepository employeeRepository;
    @InjectMocks
    ValidateEmployeeEmail validateEmployeeEmail;
    @Test
    public void validateEmployeeEmailTest_emailIsDuplicate(){
        String employeeId = "CAP-1";
        String email = "s@cap.com";
        String name = "xyz";
        int officeID = 101;

        Mockito.when(employeeRepository.findByEmail(email)).thenReturn(Optional.of(new Employee(employeeId,name,officeID,email)));
        ValidateResponse responseForDuplicateEmail =validateEmployeeEmail.checkEmployeeEmailExist(email);
        assertNotNull(responseForDuplicateEmail);
        assertTrue(responseForDuplicateEmail.isValid());
    }
    @Test
    public void validateEmployeeEmailTest_emailIsNotDuplicate(){
        String employeeId = "CAP-1";
        String email = "s@cap.com";
        String name = "xyz";
        int officeID = 101;
        Mockito.when(employeeRepository.findByEmail(email)).thenReturn(Optional.empty());
        ValidateResponse responseForDuplicateEmail =validateEmployeeEmail.checkEmployeeEmailExist(email);
        assertNotNull(responseForDuplicateEmail);
        assertFalse(responseForDuplicateEmail.isValid());
    }
}