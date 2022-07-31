package org.example.CalendarManagement.api.validator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ValidateMeetingDateTimeTest {
    @InjectMocks
    private ValidateMeetingDateTime validateMeetingDateTime;

    @Test
    public void validDateTime()
    {
        LocalDate date = LocalDate.of(2022,8,25);
        LocalTime startTime = LocalTime.of(19,30);
        ValidateResponse validateResponse = validateMeetingDateTime.checkMeetingDateTime(date , startTime);
        assertNotNull(validateResponse);
        assertTrue(validateResponse.isValid());
    }

    @Test
    public void validCurrentDate()
    {
        LocalDate date = LocalDate.of(2022,7,31);
        LocalTime startTime = LocalTime.of(19,30);
        ValidateResponse validateResponse = validateMeetingDateTime.checkMeetingDateTime(date , startTime);
        assertNotNull(validateResponse);
        assertTrue(validateResponse.isValid());
    }

    @Test
    public void validCurrentTime()
    {
        LocalDate date = LocalDate.of(2022,7,31);
        LocalTime startTime = LocalTime.of(10,30);
        ValidateResponse validateResponse = validateMeetingDateTime.checkMeetingDateTime(date , startTime);
        assertNotNull(validateResponse);
        assertFalse(validateResponse.isValid());
    }

    @Test
    public void inValidCurrentDateTime()
    {
        LocalDate date = LocalDate.of(2022,7,29);
        LocalTime startTime = LocalTime.of(10,30);
        ValidateResponse validateResponse = validateMeetingDateTime.checkMeetingDateTime(date , startTime);
        assertNotNull(validateResponse);
        assertFalse(validateResponse.isValid());
    }

    @Test
    public void validFutureDate()
    {
        LocalDate date = LocalDate.of(2022,9,26);
        LocalTime startTime = LocalTime.of(14,30);
        ValidateResponse validateResponse = validateMeetingDateTime.checkMeetingDateTime(date , startTime);
        assertNotNull(validateResponse);
        assertTrue(validateResponse.isValid());
    }

    @Test
    public void inValidDate()
    {
        LocalDate date = LocalDate.of(2022,07,24);
        LocalTime startTime = LocalTime.of(16,30);
        ValidateResponse validateResponse = validateMeetingDateTime.checkMeetingDateTime(date , startTime);
        assertNotNull(validateResponse);
        assertFalse(validateResponse.isValid());
    }

    @Test
    public void inValidTime()
    {
        LocalDate date = LocalDate.of(2022,07,25);
        LocalTime startTime = LocalTime.of(10,30);
        ValidateResponse validateResponse = validateMeetingDateTime.checkMeetingDateTime(date , startTime);
        assertNotNull(validateResponse);
        assertFalse(validateResponse.isValid());
    }
}