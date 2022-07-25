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
    public void validateCompanyPoliciesTest_noOfEmployeesGreaterThanSix()
    {
        List<String> employeeList = Arrays.asList("XYZ-11" ,"XYZ-12" ,"XYZ-13","XYZ-14","XYZ-15","XYZ-16","XYZ-17");
        ValidateResponse validateResponse = validateCompanyPolicies.noOfEmployeeInMeeting(employeeList);
        assertNotNull(validateResponse);
        assertFalse(validateResponse.isValid());
    }

    @Test
    public void validateCompanyPoliciesTest_noOfEmployeesLessThanAndEqualToSix()
    {
        List<String> employeeList = Arrays.asList("XYZ-11" ,"XYZ-12" ,"XYZ-13","XYZ-14","XYZ-15","XYZ-16");
        ValidateResponse validateResponse = validateCompanyPolicies.noOfEmployeeInMeeting(employeeList);
        assertNotNull(validateResponse);
        assertTrue(validateResponse.isValid());
    }

    @Test
    public void validateCompanyPoliciesTest_meetingDurationGreaterThanThirtyMinutes()
    {
        LocalTime startTime = LocalTime.of(11,30);
        LocalTime endTime = LocalTime.of(12,30);
        ValidateResponse validateResponse = validateCompanyPolicies.meetingDurationGreaterThanThirty(startTime,endTime);
        assertNotNull(validateResponse);
        assertTrue(validateResponse.isValid());
    }

    @Test
    public void validateCompanyPoliciesTest_meetingDurationLessThanThirtyMinutes()
    {
        LocalTime startTime = LocalTime.of(11,30);
        LocalTime endTime = LocalTime.of(11,55);
        ValidateResponse validateResponse = validateCompanyPolicies.meetingDurationGreaterThanThirty(startTime,endTime);
        assertNotNull(validateResponse);
        assertFalse(validateResponse.isValid());
    }

    @Test
    public void validateCompanyPoliciesTest_meetingBetweenOfficeHours()
    {
        LocalTime startTime = LocalTime.of(11,30);
        LocalTime endTime = LocalTime.of(12,30);
        ValidateResponse validateResponse = validateCompanyPolicies.meetingBetweenOfficeHours(startTime, endTime);
        assertNotNull(validateResponse);
        assertTrue(validateResponse.isValid());
    }

    @Test
    public void validateCompanyPoliciesTest_meetingNotBetweenOfficeHours()
    {
        LocalTime startTime = LocalTime.of(9,30);
        LocalTime endTime = LocalTime.of(10,30);
        ValidateResponse validateResponse = validateCompanyPolicies.meetingBetweenOfficeHours(startTime, endTime);
        assertNotNull(validateResponse);
        assertFalse(validateResponse.isValid());
    }
}