package org.example.CalendarManagement.api.validator;

import org.example.CalendarManagement.api.request.AddEmployeeDataRequest;
import org.example.CalendarManagement.api.validator.ValidateOfficeId;
import org.example.CalendarManagement.api.validator.ValidateResponse;
import org.example.CalendarManagement.calendarpersistence.model.Office;
import org.example.CalendarManagement.calendarpersistence.repository.OfficeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ValidateOfficeTest {

    //1. office id exists in db
    //2. office id not exists in db
    @Mock
    OfficeRepository officeRepository;

    @InjectMocks
    ValidateOfficeId validateOfficeId = new ValidateOfficeId();

    @Test
    public void validateOfficeTest_officeIdExistsInDb()
    {
        String employeeId = "CAP-1";
        String email = "s@cap.com";
        String name = "xyz";
        int officeID = 101;

        AddEmployeeDataRequest employeeRequest = new AddEmployeeDataRequest(employeeId,name,email,officeID);

        Mockito.when(officeRepository.findById(officeID)).thenReturn(Optional.of(new Office(officeID,"XYZ ",
                "Bangalore")));

        ValidateResponse validateResponse = validateOfficeId.checkOfficeId(employeeRequest.getOfficeId());

        assertNotNull(validateResponse);

        assertTrue(validateResponse.isValid());
    }

    @Test
    public void validateOfficeTest_officeIdNotExistsInDb()
    {
        String employeeId = "CAP-1";
        String email = "s@cap.com";
        String name = "xyz";
        int officeID = 101;

        AddEmployeeDataRequest employeeRequest = new AddEmployeeDataRequest(employeeId,name,email,officeID);

        Mockito.when(officeRepository.findById(officeID)).thenReturn(Optional.empty());

        ValidateResponse validateResponse = validateOfficeId.checkOfficeId(employeeRequest.getOfficeId());

        assertNotNull(validateResponse);

        assertFalse(validateResponse.isValid());
    }
}