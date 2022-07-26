package org.example.CalendarManagement.api.validator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ValidateMeetingDateTimeTestDetails {
    @InjectMocks
    private ValidateMeetingDateTime validateMeetingDateTime;

    @Test
    public void validateMeetingDateTimeTest_validDateTime()
    {
        LocalDate date = LocalDate.of(2022,8,25);
        LocalTime startTime = LocalTime.of(19,30);
        ValidateResponse validateResponse = validateMeetingDateTime.checkMeetingDateTime(date , startTime);
        assertNotNull(validateResponse);
        assertTrue(validateResponse.isValid());
    }

    @Test
    public void validateMeetingDateTimeTest_validFutureDate()
    {
        LocalDate date = LocalDate.of(2022,9,26);
        LocalTime startTime = LocalTime.of(14,30);
        ValidateResponse validateResponse = validateMeetingDateTime.checkMeetingDateTime(date , startTime);
        assertNotNull(validateResponse);
        assertTrue(validateResponse.isValid());
    }

    @Test
    public void validateMeetingDateTimeTest_inValidDate()
    {
        LocalDate date = LocalDate.of(2022,07,24);
        LocalTime startTime = LocalTime.of(16,30);
        ValidateResponse validateResponse = validateMeetingDateTime.checkMeetingDateTime(date , startTime);
        assertNotNull(validateResponse);
        assertFalse(validateResponse.isValid());
    }

    @Test
    public void validateMeetingDateTimeTest_inValidTime()
    {
        LocalDate date = LocalDate.of(2022,07,25);
        LocalTime startTime = LocalTime.of(10,30);
        ValidateResponse validateResponse = validateMeetingDateTime.checkMeetingDateTime(date , startTime);
        assertNotNull(validateResponse);
        assertFalse(validateResponse.isValid());
    }
}