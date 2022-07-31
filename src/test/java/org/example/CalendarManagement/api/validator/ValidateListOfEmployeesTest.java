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
    @Mock
    ValidateEmployeeId validateEmployeeId;
    @InjectMocks
    ValidateListOfEmployees validateListOfEmployees;

    @Test
    public void ownerAlsoInListOfEmployee()
    {
        List<String> listOfEmployee = Arrays.asList("abc-12", "abc-13", "abc-14","abc-14");
        ValidateResponse validateResponse = validateListOfEmployees.checkIfEmployeeExistInSameOffice(listOfEmployee,2,"abc-12");
        assertNotNull(validateResponse);
        assertFalse(validateResponse.isValid());
        assertEquals("owner is also in list of employees",validateResponse.getMessage());
    }

    @Test
    public void employeeDuplicatesInList(){
        List<String> listOfEmployee = Arrays.asList("abc-12", "abc-13", "abc-14","abc-14");
        ValidateResponse validateResponse = validateListOfEmployees.checkIfEmployeeExistInSameOffice(listOfEmployee,2 ,"abc-11");
        assertNotNull(validateResponse);
        assertFalse(validateResponse.isValid());
        assertEquals(" Duplicate employee found",validateResponse.getMessage());
    }
    @Test
    public void employeeNotInDatabase(){
        List<String> listOfEmployee = Arrays.asList("abc-12", "abc-13", "abc-14","abc-8");
        Mockito.when(validateEmployeeId.checkEmployeeId(Mockito.anyString())).thenReturn(new ValidateResponse("Employee Not in Db",false));
        ValidateResponse validateResponse = validateListOfEmployees.checkIfEmployeeExistInSameOffice(listOfEmployee,2,"abc-11");
        assertNotNull(validateResponse);
        assertFalse(validateResponse.isValid());
        assertEquals("Not all employees exist in db",validateResponse.getMessage());
    }

    @Test
    public void employeeExistAndInSameOffice(){
        List<String> listOfEmployee = Arrays.asList("abc-12", "abc-13", "abc-14");
        Mockito.when(validateEmployeeId.checkEmployeeId(Mockito.anyString())).thenReturn(new ValidateResponse("Employee in Db",true));
        Mockito.when(employeeRepository.countByIdIn(Mockito.anyList())).thenReturn(3);
        Mockito.when(employeeRepository.findOfficeByEmployeeId(Mockito.anyList())).thenReturn(Arrays.asList(2));
        ValidateResponse validateResponse = validateListOfEmployees.checkIfEmployeeExistInSameOffice(listOfEmployee,2,"abc-11");
        assertNotNull(validateResponse);
        assertTrue(validateResponse.isValid());
    }
    @Test
    public void employeeExistAndNotInOffice(){
        List<String> listOfEmployee = Arrays.asList("abc-12", "abc-13", "abc-14");
        Mockito.when(validateEmployeeId.checkEmployeeId(Mockito.anyString())).thenReturn(new ValidateResponse("Employee in Db",true));
        Mockito.when(employeeRepository.countByIdIn(Mockito.anyList())).thenReturn(3);
        Mockito.when(employeeRepository.findOfficeByEmployeeId(Mockito.anyList())).thenReturn(Arrays.asList(2,3));
        ValidateResponse validateResponse = validateListOfEmployees.checkIfEmployeeExistInSameOffice(listOfEmployee,2, "abc-11");
        assertNotNull(validateResponse);
        assertFalse(validateResponse.isValid());
    }


}