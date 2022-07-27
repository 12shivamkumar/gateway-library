package org.example.CalendarManagement.api.validator;

import org.example.CalendarManagement.calendarpersistence.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ValidateListOfEmployeesTest {
    @Mock
    EmployeeRepository employeeRepository;

    @InjectMocks
    ValidateListOfEmployees validateListOfEmployees;


    // emp exists in db and office id is same
    @Test
    public void validateListOfEmployeeTest_employeeExistAndInSameOffice(){
        List<String> listOfEmployee = Arrays.asList("abc-12", "abc-13", "abc-14");
        Mockito.when(employeeRepository.countByIdIn(Mockito.anyList())).thenReturn(3);
        Mockito.when(employeeRepository.findOfficeByEmployeeId(Mockito.anyList())).thenReturn(Arrays.asList(2));
        ValidateResponse validateResponse = validateListOfEmployees.checkIfEmployeeExistInSameOffice(listOfEmployee,2);
        assertNotNull(validateResponse);
        assertTrue(validateResponse.isValid());
    }
    @Test
    public void validateListOfEmployeeTest_employeeExistAndNotInOffice(){
        List<String> listOfEmployee = Arrays.asList("abc-12", "abc-13", "abc-14");
        Mockito.when(employeeRepository.countByIdIn(Mockito.anyList())).thenReturn(3);
        Mockito.when(employeeRepository.findOfficeByEmployeeId(Mockito.anyList())).thenReturn(Arrays.asList(2,3));
        ValidateResponse validateResponse = validateListOfEmployees.checkIfEmployeeExistInSameOffice(listOfEmployee,2);
        assertNotNull(validateResponse);
        assertFalse(validateResponse.isValid());
    }
    @Test
    public void  validateListOfEmployeeTest_employeeDoesNotExist(){
        List<String> listOfEmployee = Arrays.asList("abc-12", "abc-13", "abc-14","abc-15");
        Mockito.when(employeeRepository.countByIdIn(Mockito.anyList())).thenReturn(2);
        ValidateResponse validateResponse = validateListOfEmployees.checkIfEmployeeExistInSameOffice(listOfEmployee,2);
        assertNotNull(validateResponse);
        assertFalse(validateResponse.isValid());
    }

}