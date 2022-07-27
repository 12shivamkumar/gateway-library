package org.example.CalendarManagement.calendarcontroller;

import org.example.CalendarManagement.api.Response;
import org.example.CalendarManagement.api.request.AddMeetingDataRequest;
import org.example.CalendarManagement.api.validator.*;
import org.example.CalendarManagement.calendarfacade.MeetingFacade;
import org.example.CalendarManagement.calendarpersistence.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MeetingControllerTest {

    @Mock
    ValidateCompanyPolicies validateCompanyPolicies;

    @Mock
    ValidateMeetingDateTime validateMeetingDateTime;

    @Mock
    ValidateEmployeeId validateOwnerId;

    @Mock
    ValidateListOfEmployees validateListOfEmployees;

    @Mock
    EmployeeRepository employeeRepository;

    @Mock
    ValidateMeetingRoom validateMeetingRoom;

    @Mock
    MeetingFacade meetingFacade;

    @InjectMocks
    MeetingController meetingController;

    @Test
    public void meetingControllerTest_companyPolicyNoOfEmployeeInMeetingValidationFailed()
    {
        List<String> employeeList = Arrays.asList("abc-11", "abc-12", "abc-13", "abc-14", "abc-15", "abc-16", "abc-17");
        AddMeetingDataRequest addMeetingDataRequest = new AddMeetingDataRequest
                ("abc-11" , "sync-up","details",employeeList, LocalDate.of(2022,8,10), LocalTime.of(11,00), LocalTime.of(12,00), "Room1");
        Mockito.when(validateCompanyPolicies.noOfEmployeeInMeeting(employeeList))
                .thenReturn(new ValidateResponse("Employees more than six are present So meeting is not Productive" ,false));
        ResponseEntity<Response> responseEntity = meetingController.scheduleMeeting(addMeetingDataRequest);
        assertNotNull(responseEntity);
        assertEquals(400,responseEntity.getStatusCodeValue());
        assertNotNull(responseEntity.getBody());
        assertEquals("Employees more than six are present So meeting is not Productive" , responseEntity.getBody().getError());
    }

    @Test
    public void meetingControllerTest_companyPolicyMeetingDurationGreaterThanThirtyMinutesValidationFailed()
    {
        LocalTime startTime = LocalTime.of(11,00);
        LocalTime endTime = LocalTime.of(11,20);
        List<String> employeeList = Arrays.asList("abc-11", "abc-12", "abc-13", "abc-14", "abc-15", "abc-16");
        AddMeetingDataRequest addMeetingDataRequest = new AddMeetingDataRequest
                ("abc-11" , "sync-up","details",employeeList, LocalDate.of(2022,8,10),startTime, endTime, "Room1");
        Mockito.when(validateCompanyPolicies.noOfEmployeeInMeeting(employeeList)).thenReturn(new ValidateResponse( "Employee less than or equal to six are present so meeting is productive", true));
        Mockito.when(validateCompanyPolicies.meetingDurationGreaterThanThirtyMinutes(startTime,endTime)).thenReturn(new ValidateResponse("meeting won't be productive" , false));
        ResponseEntity<Response> responseEntity = meetingController.scheduleMeeting(addMeetingDataRequest);
        assertNotNull(responseEntity);
        assertEquals(400,responseEntity.getStatusCodeValue());
        assertNotNull(responseEntity.getBody());
        assertEquals("meeting won't be productive because meeting duration is less than 30 minutes" , responseEntity.getBody().getError());
    }

    @Test
    public void meetingControllerTest_companyPolicyMeetingBetweenOfficeHoursValidationFailed()
    {
        LocalTime startTime = LocalTime.of(17,00);
        LocalTime endTime = LocalTime.of(18,20);
        List<String> employeeList = Arrays.asList("abc-11", "abc-12", "abc-13", "abc-14", "abc-15", "abc-16");
        AddMeetingDataRequest addMeetingDataRequest = new AddMeetingDataRequest
                ("abc-11" , "sync-up","details",employeeList, LocalDate.of(2022,8,10),startTime, endTime, "Room1");
        Mockito.when(validateCompanyPolicies.noOfEmployeeInMeeting(employeeList)).thenReturn(new ValidateResponse( "Employee less than or equal to six are present so meeting is productive", true));
        Mockito.when(validateCompanyPolicies.meetingDurationGreaterThanThirtyMinutes(startTime,endTime)).thenReturn(new ValidateResponse("meeting is productive" , true));
        Mockito.when(validateCompanyPolicies.meetingBetweenOfficeHours(startTime, endTime)).thenReturn(new ValidateResponse("meeting won't be productive because meeting is outside office hours", false));
        ResponseEntity<Response> responseEntity = meetingController.scheduleMeeting(addMeetingDataRequest);
        assertNotNull(responseEntity);
        assertEquals(400,responseEntity.getStatusCodeValue());
        assertNotNull(responseEntity.getBody());
        assertEquals("meeting won't be productive because meeting is outside office hours" , responseEntity.getBody().getError());
    }

    @Test
    public void meetingControllerTest_validateMeetingDateTimeFailed()
    {
        LocalDate dateOfMeeting = LocalDate.of(2022,7,26);
        LocalTime startTime = LocalTime.of(16,00);
        LocalTime endTime = LocalTime.of(16,50);
        List<String> employeeList = Arrays.asList("abc-11", "abc-12", "abc-13", "abc-14", "abc-15", "abc-16");
        AddMeetingDataRequest addMeetingDataRequest = new AddMeetingDataRequest
                ("abc-11" , "sync-up","details",employeeList, dateOfMeeting,startTime, endTime, "Room1");
        Mockito.when(validateCompanyPolicies.noOfEmployeeInMeeting(employeeList)).thenReturn(new ValidateResponse( "Employee less than or equal to six are present so meeting is productive", true));
        Mockito.when(validateCompanyPolicies.meetingDurationGreaterThanThirtyMinutes(startTime,endTime)).thenReturn(new ValidateResponse("meeting is productive" , true));
        Mockito.when(validateCompanyPolicies.meetingBetweenOfficeHours(startTime, endTime)).thenReturn(new ValidateResponse("meeting is productive", true));
        Mockito.when(validateMeetingDateTime.checkMeetingDateTime(dateOfMeeting,startTime)).thenReturn(new ValidateResponse("Invalid input for scheduling a meeting" , false));
        ResponseEntity<Response> responseEntity = meetingController.scheduleMeeting(addMeetingDataRequest);
        assertNotNull(responseEntity);
        assertEquals(400,responseEntity.getStatusCodeValue());
        assertNotNull(responseEntity.getBody());
        assertEquals("Invalid input for scheduling a meeting", responseEntity.getBody().getError());
    }

    @Test
    public void meetingControllerTest_ownerIdDoesNotExistsInDb()
    {
        LocalDate dateOfMeeting = LocalDate.of(2022,8,26);
        LocalTime startTime = LocalTime.of(16,00);
        LocalTime endTime = LocalTime.of(16,50);
        List<String> employeeList = Arrays.asList("abc-11", "abc-12", "abc-13", "abc-14", "abc-15", "abc-16");
        AddMeetingDataRequest addMeetingDataRequest = new AddMeetingDataRequest
                ("abc-11" , "sync-up","details",employeeList, dateOfMeeting,startTime, endTime, "Room1");
        Mockito.when(validateCompanyPolicies.noOfEmployeeInMeeting(employeeList)).thenReturn(new ValidateResponse( "Employee less than or equal to six are present so meeting is productive", true));
        Mockito.when(validateCompanyPolicies.meetingDurationGreaterThanThirtyMinutes(startTime,endTime)).thenReturn(new ValidateResponse("meeting is productive" , true));
        Mockito.when(validateCompanyPolicies.meetingBetweenOfficeHours(startTime, endTime)).thenReturn(new ValidateResponse("meeting is productive", true));
        Mockito.when(validateMeetingDateTime.checkMeetingDateTime(dateOfMeeting,startTime)).thenReturn(new ValidateResponse("meeting can be scheduled" , true));
        Mockito.when(validateOwnerId.checkEmployeeId("abc-11")).thenReturn(new ValidateResponse("Owner does not exists", false));
        ResponseEntity<Response> responseEntity = meetingController.scheduleMeeting(addMeetingDataRequest);
        assertNotNull(responseEntity);
        assertEquals(400,responseEntity.getStatusCodeValue());
        assertNotNull(responseEntity.getBody());
        assertEquals("Owner does not exists",responseEntity.getBody().getError());
    }

    @Test
    public void meetingControllerTest_allEmployeesNotInSameOffice()
    {
        LocalDate dateOfMeeting = LocalDate.of(2022,8,26);
        LocalTime startTime = LocalTime.of(16,00);
        LocalTime endTime = LocalTime.of(16,50);
        int officeId = 1;
        List<String> employeeList = Arrays.asList("abc-11", "abc-12", "abc-13", "abc-14", "abc-15", "abc-16");
        AddMeetingDataRequest addMeetingDataRequest = new AddMeetingDataRequest
                ("abc-11" , "sync-up","details",employeeList, dateOfMeeting,startTime, endTime, "Room1");
        Mockito.when(validateCompanyPolicies.noOfEmployeeInMeeting(employeeList)).thenReturn(new ValidateResponse( "Employee less than or equal to six are present so meeting is productive", true));
        Mockito.when(validateCompanyPolicies.meetingDurationGreaterThanThirtyMinutes(startTime,endTime)).thenReturn(new ValidateResponse("meeting is productive" , true));
        Mockito.when(validateCompanyPolicies.meetingBetweenOfficeHours(startTime, endTime)).thenReturn(new ValidateResponse("meeting is productive", true));
        Mockito.when(validateMeetingDateTime.checkMeetingDateTime(dateOfMeeting,startTime)).thenReturn(new ValidateResponse("meeting can be scheduled" , true));
        Mockito.when(validateOwnerId.checkEmployeeId("abc-11")).thenReturn(new ValidateResponse("Owner exists", true));
        Mockito.when(employeeRepository.findOfficeIdById("abc-11")).thenReturn(1);
        Mockito.when(validateListOfEmployees.checkIfEmployeeExistInSameOffice(employeeList,officeId)).thenReturn(new ValidateResponse("Employee working in different offices" , false));
        ResponseEntity<Response> responseEntity = meetingController.scheduleMeeting(addMeetingDataRequest);
        assertNotNull(responseEntity);
        assertEquals(400,responseEntity.getStatusCodeValue());
        assertNotNull(responseEntity.getBody());
        assertEquals("All employees does not belongs to same office", responseEntity.getBody().getError());
    }

    @Test
    public void meetingControllerTest_meetingRoomNotInDb()
    {
        LocalDate dateOfMeeting = LocalDate.of(2022,8,26);
        LocalTime startTime = LocalTime.of(16,00);
        LocalTime endTime = LocalTime.of(16,50);
        int officeId = 1;
        List<String> employeeList = Arrays.asList("abc-11", "abc-12", "abc-13", "abc-14", "abc-15", "abc-16");
        AddMeetingDataRequest addMeetingDataRequest = new AddMeetingDataRequest
                ("abc-11" , "sync-up","details",employeeList, dateOfMeeting,startTime, endTime, "Room1");
        Mockito.when(validateCompanyPolicies.noOfEmployeeInMeeting(employeeList)).thenReturn(new ValidateResponse( "Employee less than or equal to six are present so meeting is productive", true));
        Mockito.when(validateCompanyPolicies.meetingDurationGreaterThanThirtyMinutes(startTime,endTime)).thenReturn(new ValidateResponse("meeting is productive" , true));
        Mockito.when(validateCompanyPolicies.meetingBetweenOfficeHours(startTime, endTime)).thenReturn(new ValidateResponse("meeting is productive", true));
        Mockito.when(validateMeetingDateTime.checkMeetingDateTime(dateOfMeeting,startTime)).thenReturn(new ValidateResponse("meeting can be scheduled" , true));
        Mockito.when(validateOwnerId.checkEmployeeId("abc-11")).thenReturn(new ValidateResponse("Owner exists", true));
        Mockito.when(employeeRepository.findOfficeIdById("abc-11")).thenReturn(1);
        Mockito.when(validateListOfEmployees.checkIfEmployeeExistInSameOffice(employeeList,officeId)).thenReturn(new ValidateResponse(" Employees exist in DB and belong to same office" , true));
        Mockito.when(validateMeetingRoom.checkMeetingRoomInDb("Room1")).thenReturn(new ValidateResponse("meeting room does not exist or is closed",false));
        ResponseEntity<Response> responseEntity = meetingController.scheduleMeeting(addMeetingDataRequest);
        assertNotNull(responseEntity);
        assertEquals(400,responseEntity.getStatusCodeValue());
        assertNotNull(responseEntity.getBody());
        assertEquals( "meeting room does not exist or is closed", responseEntity.getBody().getError());
    }

    @Test
    public void meetingControllerTest_meetingScheduledSFailed()
    {
        LocalDate dateOfMeeting = LocalDate.of(2022,8,26);
        LocalTime startTime = LocalTime.of(16,00);
        LocalTime endTime = LocalTime.of(16,50);
        int officeId = 1;
        List<String> employeeList = Arrays.asList("abc-11", "abc-12", "abc-13", "abc-14", "abc-15", "abc-16");
        AddMeetingDataRequest addMeetingDataRequest = new AddMeetingDataRequest
                ("abc-11" , "sync-up","details",employeeList, dateOfMeeting,startTime, endTime, "Room1");
        Mockito.when(validateCompanyPolicies.noOfEmployeeInMeeting(employeeList)).thenReturn(new ValidateResponse( "Employee less than or equal to six are present so meeting is productive", true));
        Mockito.when(validateCompanyPolicies.meetingDurationGreaterThanThirtyMinutes(startTime,endTime)).thenReturn(new ValidateResponse("meeting is productive" , true));
        Mockito.when(validateCompanyPolicies.meetingBetweenOfficeHours(startTime, endTime)).thenReturn(new ValidateResponse("meeting is productive", true));
        Mockito.when(validateMeetingDateTime.checkMeetingDateTime(dateOfMeeting,startTime)).thenReturn(new ValidateResponse("meeting can be scheduled" , true));
        Mockito.when(validateOwnerId.checkEmployeeId("abc-11")).thenReturn(new ValidateResponse("Owner exists", true));
        Mockito.when(employeeRepository.findOfficeIdById("abc-11")).thenReturn(1);
        Mockito.when(validateListOfEmployees.checkIfEmployeeExistInSameOffice(employeeList,officeId)).thenReturn(new ValidateResponse(" Employees exist in DB and belong to same office" , true));
        Mockito.when(validateMeetingRoom.checkMeetingRoomInDb("Room1")).thenReturn(new ValidateResponse("meeting room present in db",true));
        Mockito.when(meetingFacade.scheduleMeeting(addMeetingDataRequest)).thenReturn(new Response("Meeting cannot be scheduled" ,""));
        ResponseEntity<Response> responseEntity = meetingController.scheduleMeeting(addMeetingDataRequest);
        assertNotNull(responseEntity);
        assertEquals(400,responseEntity.getStatusCodeValue());
        assertNotNull(responseEntity.getBody());
    }

    @Test
    public void meetingControllerTest_meetingScheduledSuccessfullyRoomGiven()
    {
        LocalDate dateOfMeeting = LocalDate.of(2022,8,26);
        LocalTime startTime = LocalTime.of(16,00);
        LocalTime endTime = LocalTime.of(16,50);
        int officeId = 1;
        List<String> employeeList = Arrays.asList("abc-11", "abc-12", "abc-13", "abc-14", "abc-15", "abc-16");
        AddMeetingDataRequest addMeetingDataRequest = new AddMeetingDataRequest
                ("abc-11" , "sync-up","details",employeeList, dateOfMeeting,startTime, endTime, "Room1");
        Mockito.when(validateCompanyPolicies.noOfEmployeeInMeeting(employeeList)).thenReturn(new ValidateResponse( "Employee less than or equal to six are present so meeting is productive", true));
        Mockito.when(validateCompanyPolicies.meetingDurationGreaterThanThirtyMinutes(startTime,endTime)).thenReturn(new ValidateResponse("meeting is productive" , true));
        Mockito.when(validateCompanyPolicies.meetingBetweenOfficeHours(startTime, endTime)).thenReturn(new ValidateResponse("meeting is productive", true));
        Mockito.when(validateMeetingDateTime.checkMeetingDateTime(dateOfMeeting,startTime)).thenReturn(new ValidateResponse("meeting can be scheduled" , true));
        Mockito.when(validateOwnerId.checkEmployeeId("abc-11")).thenReturn(new ValidateResponse("Owner exists", true));
        Mockito.when(employeeRepository.findOfficeIdById("abc-11")).thenReturn(1);
        Mockito.when(validateListOfEmployees.checkIfEmployeeExistInSameOffice(employeeList,officeId)).thenReturn(new ValidateResponse(" Employees exist in DB and belong to same office" , true));
        Mockito.when(validateMeetingRoom.checkMeetingRoomInDb("Room1")).thenReturn(new ValidateResponse("meeting room present in db",true));
        Mockito.when(meetingFacade.scheduleMeeting(addMeetingDataRequest)).thenReturn(new Response(null ,"20128229"));
        ResponseEntity<Response> responseEntity = meetingController.scheduleMeeting(addMeetingDataRequest);
        assertNotNull(responseEntity);
        assertEquals(201,responseEntity.getStatusCodeValue());
        assertNotNull(responseEntity.getBody());
    }

    @Test
    public void meetingControllerTest_meetingScheduledSuccessfullyRoomNotGiven()
    {
        LocalDate dateOfMeeting = LocalDate.of(2022,8,26);
        LocalTime startTime = LocalTime.of(16,00);
        LocalTime endTime = LocalTime.of(16,50);
        int officeId = 1;
        List<String> employeeList = Arrays.asList("abc-11", "abc-12", "abc-13", "abc-14", "abc-15", "abc-16");
        AddMeetingDataRequest addMeetingDataRequest = new AddMeetingDataRequest
                ("abc-11" , "sync-up","details",employeeList, dateOfMeeting,startTime, endTime, "");
        Mockito.when(validateCompanyPolicies.noOfEmployeeInMeeting(employeeList)).thenReturn(new ValidateResponse( "Employee less than or equal to six are present so meeting is productive", true));
        Mockito.when(validateCompanyPolicies.meetingDurationGreaterThanThirtyMinutes(startTime,endTime)).thenReturn(new ValidateResponse("meeting is productive" , true));
        Mockito.when(validateCompanyPolicies.meetingBetweenOfficeHours(startTime, endTime)).thenReturn(new ValidateResponse("meeting is productive", true));
        Mockito.when(validateMeetingDateTime.checkMeetingDateTime(dateOfMeeting,startTime)).thenReturn(new ValidateResponse("meeting can be scheduled" , true));
        Mockito.when(validateOwnerId.checkEmployeeId("abc-11")).thenReturn(new ValidateResponse("Owner exists", true));
        Mockito.when(employeeRepository.findOfficeIdById("abc-11")).thenReturn(1);
        Mockito.when(validateListOfEmployees.checkIfEmployeeExistInSameOffice(employeeList,officeId)).thenReturn(new ValidateResponse(" Employees exist in DB and belong to same office" , true));
        Mockito.when(validateMeetingRoom.checkMeetingRoomInDb("")).thenReturn(new ValidateResponse("meeting room not given",true));
        Mockito.when(meetingFacade.scheduleMeeting(addMeetingDataRequest)).thenReturn(new Response(null ,"20128229"));
        ResponseEntity<Response> responseEntity = meetingController.scheduleMeeting(addMeetingDataRequest);
        assertNotNull(responseEntity);
        assertEquals(201,responseEntity.getStatusCodeValue());
        assertNotNull(responseEntity.getBody());
    }
}