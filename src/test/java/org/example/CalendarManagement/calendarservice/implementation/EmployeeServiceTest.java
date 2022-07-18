package org.example.CalendarManagement.calendarservice.implementation;

import org.example.CalendarManagement.api.validator.ValidateResponse;
import org.example.CalendarManagement.calendarpersistence.model.Employee;
import org.example.CalendarManagement.calendarpersistence.repository.EmployeeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {
    @Mock
    EmployeeRepository employeeRepository;
    @InjectMocks
    EmployeeService employeeService;

    @Test
    public void addEmployeeTest_employeeSavedSuccessfully(){
        String employeeId = "CAP-1";
        String email = "s@cap.com";
        String name = "xyz";
        int officeID = 101;
        Employee employeeWithValidArguments = new Employee(employeeId,name,officeID,email);
        Mockito.when(employeeRepository.save(employeeWithValidArguments)).thenReturn(employeeWithValidArguments);
        assertNotNull(employeeService.addEmployee(employeeWithValidArguments));
    }

    @Test()
    public void addEmployeeTest_employeeSaveFailed(){
        String employeeId = "CAP-1";
        String name = "xyz";
        int officeID = 101;
        boolean thrown = false;
        Employee employeeWithInvalidArguments = new Employee(employeeId,name,officeID, null);
        DataAccessException dataAccessException = new DataAccessException("Data cannot be accessed") {
            @Override
            public String getMessage() {
                return super.getMessage();
            }
        };
        Mockito.when(employeeRepository.save(employeeWithInvalidArguments)).thenThrow(dataAccessException);
        Assertions.assertThrows(DataAccessException.class, () -> employeeService.addEmployee(employeeWithInvalidArguments));
    }
}