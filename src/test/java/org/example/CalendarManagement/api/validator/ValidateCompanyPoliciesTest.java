package org.example.CalendarManagement.api.validator;

import org.example.CalendarManagement.calendarpersistence.model.Employee;
import org.example.CalendarManagement.calendarpersistence.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Array;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ValidateCompanyPoliciesTest {
    @InjectMocks
    ValidateCompanyPolicies validateCompanyPolicies;

    @Test
    public void noOfEmployeesGreaterThanSix()
    {
        List<String> employeeList = Arrays.asList("abc-11", "abc-12", "abc-13", "abc-14", "abc-15", "abc-16" , "abc-17");
        ValidateResponse validateResponse = validateCompanyPolicies.noOfEmployeeInMeeting(employeeList);
        assertNotNull(validateResponse);
        assertFalse(validateResponse.isValid());
    }

    @Test
    public void meetingTimingInputWrong()
    {
        LocalTime startTime = LocalTime.of(11,30);
        LocalTime endTime = LocalTime.of(10,30);
        ValidateResponse validateResponse = validateCompanyPolicies.meetingBetweenOfficeHours(startTime, endTime);
        assertNotNull(validateResponse);
        assertTrue(validateResponse.isValid());
    }

    @Test
    public void noOfEmployeesLessThanAndEqualToSix()
    {
        List<String> employeeList = Arrays.asList("abc-11", "abc-12", "abc-13", "abc-14", "abc-15", "abc-16");
        ValidateResponse validateResponse = validateCompanyPolicies.noOfEmployeeInMeeting(employeeList);
        assertNotNull(validateResponse);
        assertTrue(validateResponse.isValid());
    }

    @Test
    public void meetingDurationGreaterThanThirtyMinutes()
    {
        LocalTime startTime = LocalTime.of(11,30);
        LocalTime endTime = LocalTime.of(12,30);
        ValidateResponse validateResponse = validateCompanyPolicies.meetingDurationGreaterThanThirtyMinutes(startTime,endTime);
        assertNotNull(validateResponse);
        assertTrue(validateResponse.isValid());
    }

    @Test
    public void meetingDurationLessThanThirtyMinutes()
    {
        LocalTime startTime = LocalTime.of(11,30);
        LocalTime endTime = LocalTime.of(11,55);
        ValidateResponse validateResponse = validateCompanyPolicies.meetingDurationGreaterThanThirtyMinutes(startTime,endTime);
        assertNotNull(validateResponse);
        assertFalse(validateResponse.isValid());
    }

    @Test
    public void meetingDurationLessThanZero()
    {
        LocalTime startTime = LocalTime.of(11,30);
        LocalTime endTime = LocalTime.of(11,25);
        ValidateResponse validateResponse = validateCompanyPolicies.meetingDurationGreaterThanThirtyMinutes(startTime,endTime);
        assertNotNull(validateResponse);
        assertFalse(validateResponse.isValid());
    }

    @Test
    public void meetingBetweenOfficeHours()
    {
        LocalTime startTime = LocalTime.of(11,30);
        LocalTime endTime = LocalTime.of(12,30);
        ValidateResponse validateResponse = validateCompanyPolicies.meetingBetweenOfficeHours(startTime, endTime);
        assertNotNull(validateResponse);
        assertTrue(validateResponse.isValid());
    }

    @Test
    public void meetingNotBetweenOfficeHours()
    {
        LocalTime startTime = LocalTime.of(9,30);
        LocalTime endTime = LocalTime.of(10,30);
        ValidateResponse validateResponse = validateCompanyPolicies.meetingBetweenOfficeHours(startTime, endTime);
        assertNotNull(validateResponse);
        assertFalse(validateResponse.isValid());
    }
}