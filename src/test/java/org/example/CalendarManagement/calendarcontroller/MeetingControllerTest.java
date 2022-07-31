package org.example.CalendarManagement.calendarcontroller;

import org.example.CalendarManagement.api.Response;
import org.example.CalendarManagement.api.request.AddMeetingDataRequest;
import org.example.CalendarManagement.api.validator.*;
import org.example.CalendarManagement.calendarfacade.MeetingFacade;
import org.example.CalendarManagement.calendarpersistence.repository.EmployeeRepository;
import org.example.CalendarManagement.calendarpersistence.repository.MeetingRoomRepository;
import org.example.CalendarManagement.thriftobjectmappers.AddMeetingToEmployeeAvailabilityMapper;
import org.example.CalendarThriftConfiguration.Date;
import org.example.CalendarThriftConfiguration.EmployeeAvailabilityDataRequest;
import org.example.CalendarThriftConfiguration.EmployeeMeetingDetails;
import org.example.CalendarThriftConfiguration.Time;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MeetingControllerTest {

    @Mock
    ValidateCompanyPolicies validateCompanyPolicies;

    @Mock
    ValidateMeetingDateTime validateMeetingDateTime;

    @Mock
    ValidateEmployeeId validateEmployeeId;

    @Mock
    ValidateListOfEmployees validateListOfEmployees;

    @Mock
    EmployeeRepository employeeRepository;

    @Mock
    ValidateMeetingRoomExistsInDb validateMeetingRoomExistsInDb;

    @Mock
    ValidateMeetingRoomAvailability validateMeetingRoomAvailability;

    @Mock
    ValidateEmployeeAvailability validateEmployeeAvailability;

    @Mock
    MeetingFacade meetingFacade;

    @Mock
    MeetingRoomRepository meetingRoomRepository;

    @InjectMocks
    MeetingController meetingController;

    @Test
    public void companyPolicyNoOfEmployeeInMeetingValidationFailed()
    {
        List<String> employeeList = Arrays.asList("abc-11", "abc-12", "abc-13", "abc-14", "abc-15", "abc-16", "abc-17");
        AddMeetingDataRequest addMeetingDataRequest = new AddMeetingDataRequest.Builder
                ("abc-10" , "sync-up","details",employeeList, LocalDate.of(2022,8,10), LocalTime.of(11,00), LocalTime.of(12,00)).roomName(Optional.of("Room1")).build();
        Mockito.when(validateCompanyPolicies.noOfEmployeeInMeeting(employeeList))
                .thenReturn(new ValidateResponse("Employees more than six are present So meeting is not Productive" ,false));
        ResponseEntity<Response> responseEntity = meetingController.scheduleMeeting(addMeetingDataRequest);
        assertNotNull(responseEntity);
        assertEquals(400,responseEntity.getStatusCodeValue());
        assertNotNull(responseEntity.getBody());
        assertEquals("Employees more than six are present So meeting is not Productive" , responseEntity.getBody().getError());
    }

    @Test
    public void companyPolicyMeetingDurationGreaterThanThirtyMinutesValidationFailed()
    {
        LocalTime startTime = LocalTime.of(11,00);
        LocalTime endTime = LocalTime.of(11,20);
        List<String> employeeList = Arrays.asList("abc-11", "abc-12", "abc-13", "abc-14", "abc-15", "abc-16");
        AddMeetingDataRequest addMeetingDataRequest = new AddMeetingDataRequest.Builder("abc-10" , "sync-up","details",employeeList, LocalDate.of(2022,8,10),startTime, endTime).roomName(Optional.of("Room1")).build();
        Mockito.when(validateCompanyPolicies.noOfEmployeeInMeeting(employeeList)).thenReturn(new ValidateResponse( "Employee less than or equal to six are present so meeting is productive", true));
        Mockito.when(validateCompanyPolicies.meetingDurationGreaterThanThirtyMinutes(startTime,endTime)).thenReturn(new ValidateResponse("meeting won't be productive" , false));
        ResponseEntity<Response> responseEntity = meetingController.scheduleMeeting(addMeetingDataRequest);
        assertNotNull(responseEntity);
        assertEquals(400,responseEntity.getStatusCodeValue());
        assertNotNull(responseEntity.getBody());
        assertEquals("meeting won't be productive" , responseEntity.getBody().getError());
    }

    @Test
    public void companyPolicyMeetingBetweenOfficeHoursValidationFailed()
    {
        LocalTime startTime = LocalTime.of(17,00);
        LocalTime endTime = LocalTime.of(18,20);
        List<String> employeeList = Arrays.asList("abc-11", "abc-12", "abc-13", "abc-14", "abc-15", "abc-16");
        AddMeetingDataRequest addMeetingDataRequest = new AddMeetingDataRequest.Builder
                ("abc-10" , "sync-up","details",employeeList, LocalDate.of(2022,8,10),startTime, endTime).roomName(Optional.of("Room1")).build();
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
    public void validateMeetingDateTimeFailed()
    {
        LocalDate dateOfMeeting = LocalDate.of(2022,7,26);
        LocalTime startTime = LocalTime.of(16,00);
        LocalTime endTime = LocalTime.of(16,50);
        List<String> employeeList = Arrays.asList("abc-11", "abc-12", "abc-13", "abc-14", "abc-15", "abc-16");
        AddMeetingDataRequest addMeetingDataRequest = new AddMeetingDataRequest.Builder
                ("abc-10" , "sync-up","details",employeeList, dateOfMeeting,startTime, endTime).roomName(Optional.of("Room1")).build();
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
    public void ownerIdDoesNotExistsInDb()
    {
        LocalDate dateOfMeeting = LocalDate.of(2022,8,26);
        LocalTime startTime = LocalTime.of(16,00);
        LocalTime endTime = LocalTime.of(16,50);
        List<String> employeeList = Arrays.asList("abc-11", "abc-12", "abc-13", "abc-14", "abc-15", "abc-16");
        AddMeetingDataRequest addMeetingDataRequest = new AddMeetingDataRequest.Builder
                ("abc-10" , "sync-up","details",employeeList, dateOfMeeting,startTime, endTime).roomName(Optional.of("Room1")).build();
        Mockito.when(validateCompanyPolicies.noOfEmployeeInMeeting(employeeList)).thenReturn(new ValidateResponse( "Employee less than or equal to six are present so meeting is productive", true));
        Mockito.when(validateCompanyPolicies.meetingDurationGreaterThanThirtyMinutes(startTime,endTime)).thenReturn(new ValidateResponse("meeting is productive" , true));
        Mockito.when(validateCompanyPolicies.meetingBetweenOfficeHours(startTime, endTime)).thenReturn(new ValidateResponse("meeting is productive", true));
        Mockito.when(validateMeetingDateTime.checkMeetingDateTime(dateOfMeeting,startTime)).thenReturn(new ValidateResponse("meeting can be scheduled" , true));
        Mockito.when(validateEmployeeId.checkEmployeeId("abc-10")).thenReturn(new ValidateResponse("Owner does not exists", false));
        ResponseEntity<Response> responseEntity = meetingController.scheduleMeeting(addMeetingDataRequest);
        assertNotNull(responseEntity);
        assertEquals(400,responseEntity.getStatusCodeValue());
        assertNotNull(responseEntity.getBody());
        assertEquals("Owner does not exists",responseEntity.getBody().getError());
    }

    @Test
    public void allEmployeesNotInSameOffice()
    {
        LocalDate dateOfMeeting = LocalDate.of(2022,8,26);
        LocalTime startTime = LocalTime.of(16,00);
        LocalTime endTime = LocalTime.of(16,50);
        int officeId = 1;
        List<String> employeeList = Arrays.asList("abc-11", "abc-12", "abc-13", "abc-14", "abc-15", "abc-16");
        AddMeetingDataRequest addMeetingDataRequest = new AddMeetingDataRequest.Builder
                ("abc-10" , "sync-up","details",employeeList, dateOfMeeting,startTime, endTime).roomName(Optional.of("Room1")).build();
        Mockito.when(validateCompanyPolicies.noOfEmployeeInMeeting(employeeList)).thenReturn(new ValidateResponse( "Employee less than or equal to six are present so meeting is productive", true));
        Mockito.when(validateCompanyPolicies.meetingDurationGreaterThanThirtyMinutes(startTime,endTime)).thenReturn(new ValidateResponse("meeting is productive" , true));
        Mockito.when(validateCompanyPolicies.meetingBetweenOfficeHours(startTime, endTime)).thenReturn(new ValidateResponse("meeting is productive", true));
        Mockito.when(validateMeetingDateTime.checkMeetingDateTime(dateOfMeeting,startTime)).thenReturn(new ValidateResponse("meeting can be scheduled" , true));
        Mockito.when(validateEmployeeId.checkEmployeeId("abc-10")).thenReturn(new ValidateResponse("Owner exists", true));
        Mockito.when(employeeRepository.findOfficeIdById("abc-10")).thenReturn(1);
        Mockito.when(validateListOfEmployees.checkIfEmployeeExistInSameOffice(employeeList,officeId,"abc-10")).thenReturn(new ValidateResponse("Employee working in different offices" , false));
        ResponseEntity<Response> responseEntity = meetingController.scheduleMeeting(addMeetingDataRequest);
        assertNotNull(responseEntity);
        assertEquals(400,responseEntity.getStatusCodeValue());
        assertNotNull(responseEntity.getBody());
        assertEquals("Employee working in different offices", responseEntity.getBody().getError());
    }

    @Test
    public void givenMeetingRoomNotExistsInDb()
    {
        LocalDate dateOfMeeting = LocalDate.of(2022,8,26);
        LocalTime startTime = LocalTime.of(16,00);
        LocalTime endTime = LocalTime.of(16,50);
        int officeId = 1;
        List<String> employeeList = Arrays.asList("abc-11", "abc-12", "abc-13", "abc-14", "abc-15", "abc-16");
        AddMeetingDataRequest addMeetingDataRequest = new AddMeetingDataRequest.Builder
                ("abc-10" , "sync-up","details",employeeList, dateOfMeeting,startTime, endTime).roomName(Optional.of("Room1")).build();
        Mockito.when(validateCompanyPolicies.noOfEmployeeInMeeting(employeeList)).thenReturn(new ValidateResponse( "Employee less than or equal to six are present so meeting is productive", true));
        Mockito.when(validateCompanyPolicies.meetingDurationGreaterThanThirtyMinutes(startTime,endTime)).thenReturn(new ValidateResponse("meeting is productive" , true));
        Mockito.when(validateCompanyPolicies.meetingBetweenOfficeHours(startTime, endTime)).thenReturn(new ValidateResponse("meeting is productive", true));
        Mockito.when(validateMeetingDateTime.checkMeetingDateTime(dateOfMeeting,startTime)).thenReturn(new ValidateResponse("meeting can be scheduled" , true));
        Mockito.when(validateEmployeeId.checkEmployeeId("abc-10")).thenReturn(new ValidateResponse("Owner exists", true));
        Mockito.when(employeeRepository.findOfficeIdById("abc-10")).thenReturn(1);
        Mockito.when(validateListOfEmployees.checkIfEmployeeExistInSameOffice(employeeList,officeId,"abc-10")).thenReturn(new ValidateResponse(" Employees exist in DB and belong to same office" , true));
        Mockito.when(validateMeetingRoomExistsInDb.checkMeetingRoomInDb(Optional.of("Room1"))).thenReturn(new ValidateResponse("meeting room does not exist or is closed",false));
        ResponseEntity<Response> responseEntity = meetingController.scheduleMeeting(addMeetingDataRequest);
        assertNotNull(responseEntity);
        assertEquals(400,responseEntity.getStatusCodeValue());
        assertNotNull(responseEntity.getBody());
        assertEquals( "meeting room does not exist or is closed", responseEntity.getBody().getError());
    }

    @Test
    public void noMeetingRoomGivenNoFreeRoomAvailable()
    {
        LocalDate dateOfMeeting = LocalDate.of(2022,8,26);
        LocalTime startTime = LocalTime.of(16,00);
        LocalTime endTime = LocalTime.of(16,50);
        int officeId = 1;
        List<String> employeeList = Arrays.asList("abc-11", "abc-12", "abc-13", "abc-14", "abc-15", "abc-16");
        AddMeetingDataRequest addMeetingDataRequest = new AddMeetingDataRequest.Builder
                ("abc-10" , "sync-up","details",employeeList, dateOfMeeting,startTime, endTime).roomName(Optional.empty()).build();
        Mockito.when(validateCompanyPolicies.noOfEmployeeInMeeting(employeeList)).thenReturn(new ValidateResponse( "Employee less than or equal to six are present so meeting is productive", true));
        Mockito.when(validateCompanyPolicies.meetingDurationGreaterThanThirtyMinutes(startTime,endTime)).thenReturn(new ValidateResponse("meeting is productive" , true));
        Mockito.when(validateCompanyPolicies.meetingBetweenOfficeHours(startTime, endTime)).thenReturn(new ValidateResponse("meeting is productive", true));
        Mockito.when(validateMeetingDateTime.checkMeetingDateTime(dateOfMeeting,startTime)).thenReturn(new ValidateResponse("meeting can be scheduled" , true));
        Mockito.when(validateEmployeeId.checkEmployeeId("abc-10")).thenReturn(new ValidateResponse("Owner exists", true));
        Mockito.when(employeeRepository.findOfficeIdById("abc-10")).thenReturn(1);
        Mockito.when(validateListOfEmployees.checkIfEmployeeExistInSameOffice(employeeList,officeId,"abc-10")).thenReturn(new ValidateResponse(" Employees exist in DB and belong to same office" , true));
        Mockito.when(validateMeetingRoomExistsInDb.checkMeetingRoomInDb(Optional.empty())).thenReturn(new ValidateResponse("meeting room is not entered by owner search for any room",true));
        Mockito.when(validateMeetingRoomAvailability.checkMeetingRoomAvailability(addMeetingDataRequest)).thenReturn(new ValidateResponse("No Meeting room is available" , false));
        ResponseEntity<Response> responseEntity = meetingController.scheduleMeeting(addMeetingDataRequest);
        assertNotNull(responseEntity);
        assertEquals(400,responseEntity.getStatusCodeValue());
        assertNotNull(responseEntity.getBody());
        assertEquals( "No Meeting room is available", responseEntity.getBody().getError());
    }

    @Test
    public void givenMeetingRoomExistsButNotAvailable()
    {
        LocalDate dateOfMeeting = LocalDate.of(2022,8,26);
        LocalTime startTime = LocalTime.of(16,00);
        LocalTime endTime = LocalTime.of(16,50);
        int officeId = 1;
        List<String> employeeList = Arrays.asList("abc-11", "abc-12", "abc-13", "abc-14", "abc-15", "abc-16");
        AddMeetingDataRequest addMeetingDataRequest = new AddMeetingDataRequest.Builder
                ("abc-10" , "sync-up","details",employeeList, dateOfMeeting,startTime, endTime).roomName(Optional.of("Room1")).build();
        Mockito.when(validateCompanyPolicies.noOfEmployeeInMeeting(employeeList)).thenReturn(new ValidateResponse( "Employee less than or equal to six are present so meeting is productive", true));
        Mockito.when(validateCompanyPolicies.meetingDurationGreaterThanThirtyMinutes(startTime,endTime)).thenReturn(new ValidateResponse("meeting is productive" , true));
        Mockito.when(validateCompanyPolicies.meetingBetweenOfficeHours(startTime, endTime)).thenReturn(new ValidateResponse("meeting is productive", true));
        Mockito.when(validateMeetingDateTime.checkMeetingDateTime(dateOfMeeting,startTime)).thenReturn(new ValidateResponse("meeting can be scheduled" , true));
        Mockito.when(validateEmployeeId.checkEmployeeId("abc-10")).thenReturn(new ValidateResponse("Owner exists", true));
        Mockito.when(employeeRepository.findOfficeIdById("abc-10")).thenReturn(1);
        Mockito.when(validateListOfEmployees.checkIfEmployeeExistInSameOffice(employeeList,officeId,"abc-10")).thenReturn(new ValidateResponse(" Employees exist in DB and belong to same office" , true));
        Mockito.when(validateMeetingRoomExistsInDb.checkMeetingRoomInDb(Optional.of("Room1"))).thenReturn(new ValidateResponse("meeting room present in db",true));
        Mockito.when(validateMeetingRoomAvailability.checkMeetingRoomAvailability(addMeetingDataRequest)).thenReturn(new ValidateResponse("Given Meeting room is not available" , false));
        ResponseEntity<Response> responseEntity = meetingController.scheduleMeeting(addMeetingDataRequest);
        assertNotNull(responseEntity);
        assertEquals(400,responseEntity.getStatusCodeValue());
        assertNotNull(responseEntity.getBody());
        assertEquals( "Given Meeting room is not available", responseEntity.getBody().getError());
    }

    @Test
    public void allEmployeesNotAvailableTest()
    {
        LocalDate dateOfMeeting = LocalDate.of(2022,8,26);
        LocalTime startTime = LocalTime.of(16,00);
        LocalTime endTime = LocalTime.of(16,50);
        int officeId = 1;
        List<String> employeeList = Arrays.asList("abc-11", "abc-12", "abc-13", "abc-14", "abc-15", "abc-16");
        AddMeetingDataRequest addMeetingDataRequest = new AddMeetingDataRequest.Builder
                ("abc-10" , "sync-up","details",employeeList, dateOfMeeting,startTime, endTime).roomName(Optional.of("Room1")).build();
        EmployeeAvailabilityDataRequest employeeAvailabilityDataRequest = AddMeetingToEmployeeAvailabilityMapper.map(addMeetingDataRequest);
        Mockito.when(validateCompanyPolicies.noOfEmployeeInMeeting(employeeList)).thenReturn(new ValidateResponse( "Employee less than or equal to six are present so meeting is productive", true));
        Mockito.when(validateCompanyPolicies.meetingDurationGreaterThanThirtyMinutes(startTime,endTime)).thenReturn(new ValidateResponse("meeting is productive" , true));
        Mockito.when(validateCompanyPolicies.meetingBetweenOfficeHours(startTime, endTime)).thenReturn(new ValidateResponse("meeting is productive", true));
        Mockito.when(validateMeetingDateTime.checkMeetingDateTime(dateOfMeeting,startTime)).thenReturn(new ValidateResponse("meeting can be scheduled" , true));
        Mockito.when(validateEmployeeId.checkEmployeeId("abc-10")).thenReturn(new ValidateResponse("Owner exists", true));
        Mockito.when(employeeRepository.findOfficeIdById("abc-10")).thenReturn(1);
        Mockito.when(validateListOfEmployees.checkIfEmployeeExistInSameOffice(employeeList,officeId,"abc-10")).thenReturn(new ValidateResponse(" Employees exist in DB and belong to same office" , true));
        Mockito.when(validateMeetingRoomExistsInDb.checkMeetingRoomInDb(Optional.of("Room1"))).thenReturn(new ValidateResponse("1",true));
        Mockito.when(validateMeetingRoomAvailability.checkMeetingRoomAvailability(addMeetingDataRequest)).thenReturn(new ValidateResponse("Room1" , true));
        Mockito.when(validateEmployeeAvailability.checkEmployeeAvailability(employeeAvailabilityDataRequest)).thenReturn(new ValidateResponse(Arrays.asList("abc-12" , "abc-13")+"" , false));
        ResponseEntity<Response> responseEntity = meetingController.scheduleMeeting(addMeetingDataRequest);
        assertNotNull(responseEntity);
        assertEquals(400,responseEntity.getStatusCodeValue());
        assertNotNull(responseEntity.getBody());
    }

    @Test
    public void meetingScheduledSuccessfullyRoomGiven()
    {
        LocalDate dateOfMeeting = LocalDate.of(2022,8,26);
        LocalTime startTime = LocalTime.of(16,00);
        LocalTime endTime = LocalTime.of(16,50);
        int officeId = 1;
        List<String> employeeList = Arrays.asList("abc-11", "abc-12", "abc-13", "abc-14", "abc-15", "abc-16");
        AddMeetingDataRequest addMeetingDataRequest = new AddMeetingDataRequest.Builder
                ("abc-10" , "sync-up","details",employeeList, dateOfMeeting,startTime, endTime).roomName(Optional.of("Room1")).build();
        EmployeeAvailabilityDataRequest employeeAvailabilityDataRequest = AddMeetingToEmployeeAvailabilityMapper.map(addMeetingDataRequest);
        Mockito.when(validateCompanyPolicies.noOfEmployeeInMeeting(employeeList)).thenReturn(new ValidateResponse( "Employee less than or equal to six are present so meeting is productive", true));
        Mockito.when(validateCompanyPolicies.meetingDurationGreaterThanThirtyMinutes(startTime,endTime)).thenReturn(new ValidateResponse("meeting is productive" , true));
        Mockito.when(validateCompanyPolicies.meetingBetweenOfficeHours(startTime, endTime)).thenReturn(new ValidateResponse("meeting is productive", true));
        Mockito.when(validateMeetingDateTime.checkMeetingDateTime(dateOfMeeting,startTime)).thenReturn(new ValidateResponse("meeting can be scheduled" , true));
        Mockito.when(validateEmployeeId.checkEmployeeId("abc-10")).thenReturn(new ValidateResponse("Owner exists", true));
        Mockito.when(employeeRepository.findOfficeIdById("abc-10")).thenReturn(1);
        Mockito.when(validateListOfEmployees.checkIfEmployeeExistInSameOffice(employeeList,officeId,"abc-10")).thenReturn(new ValidateResponse(" Employees exist in DB and belong to same office" , true));
        Mockito.when(validateMeetingRoomExistsInDb.checkMeetingRoomInDb(Optional.of("Room1"))).thenReturn(new ValidateResponse("meeting room present in db",true));
        Mockito.when(validateMeetingRoomAvailability.checkMeetingRoomAvailability(addMeetingDataRequest)).thenReturn(new ValidateResponse("1",true));
        Mockito.when(validateEmployeeAvailability.checkEmployeeAvailability(employeeAvailabilityDataRequest)).thenReturn(new ValidateResponse("All Employees are available", true));
        Mockito.when(meetingFacade.scheduleMeeting(addMeetingDataRequest,1)).thenReturn(new Response(null ,20128229));
        ResponseEntity<Response> responseEntity = meetingController.scheduleMeeting(addMeetingDataRequest);
        assertNotNull(responseEntity);
        assertEquals(201,responseEntity.getStatusCodeValue());
        assertNotNull(responseEntity.getBody());
    }

    @Test
    public void meetingScheduledSuccessfullyRoomNotGiven()
    {
        LocalDate dateOfMeeting = LocalDate.of(2022,8,26);
        LocalTime startTime = LocalTime.of(16,00);
        LocalTime endTime = LocalTime.of(16,50);
        int officeId = 1;
        List<String> employeeList = Arrays.asList("abc-11", "abc-12", "abc-13", "abc-14", "abc-15", "abc-16");
        AddMeetingDataRequest addMeetingDataRequest = new AddMeetingDataRequest.Builder
                ("abc-10" , "sync-up","details",employeeList, dateOfMeeting,startTime, endTime).build();
        EmployeeAvailabilityDataRequest employeeAvailabilityDataRequest = AddMeetingToEmployeeAvailabilityMapper.map(addMeetingDataRequest);
        Mockito.when(validateCompanyPolicies.noOfEmployeeInMeeting(employeeList)).thenReturn(new ValidateResponse( "Employee less than or equal to six are present so meeting is productive", true));
        Mockito.when(validateCompanyPolicies.meetingDurationGreaterThanThirtyMinutes(startTime,endTime)).thenReturn(new ValidateResponse("meeting is productive" , true));
        Mockito.when(validateCompanyPolicies.meetingBetweenOfficeHours(startTime, endTime)).thenReturn(new ValidateResponse("meeting is productive", true));
        Mockito.when(validateMeetingDateTime.checkMeetingDateTime(dateOfMeeting,startTime)).thenReturn(new ValidateResponse("meeting can be scheduled" , true));
        Mockito.when(validateEmployeeId.checkEmployeeId("abc-10")).thenReturn(new ValidateResponse("Owner exists", true));
        Mockito.when(employeeRepository.findOfficeIdById("abc-10")).thenReturn(1);
        Mockito.when(validateListOfEmployees.checkIfEmployeeExistInSameOffice(employeeList,officeId,"abc-10")).thenReturn(new ValidateResponse(" Employees exist in DB and belong to same office" , true));
        Mockito.when(validateMeetingRoomExistsInDb.checkMeetingRoomInDb(Optional.empty())).thenReturn(new ValidateResponse("meeting room is not entered by owner search for any room",true));
        Mockito.when(validateMeetingRoomAvailability.checkMeetingRoomAvailability(addMeetingDataRequest)).thenReturn(new ValidateResponse("1",true));
        Mockito.when(validateEmployeeAvailability.checkEmployeeAvailability(employeeAvailabilityDataRequest)).thenReturn(new ValidateResponse("All Employees are available", true));
        Mockito.when(meetingFacade.scheduleMeeting(addMeetingDataRequest ,1)).thenReturn(new Response(null ,20128229));
        ResponseEntity<Response> responseEntity = meetingController.scheduleMeeting(addMeetingDataRequest);
        assertNotNull(responseEntity);
        assertEquals(201,responseEntity.getStatusCodeValue());
        assertNotNull(responseEntity.getBody());
    }

    @Test
    public void getMeetingTestFailedValidation(){
        String employeeId ="xyz-12";
        Mockito.when(validateEmployeeId.checkEmployeeId(Mockito.anyString())).thenReturn(new ValidateResponse("employee does not exist",false));
        ResponseEntity responseEntity = meetingController.getMeetingsOfEmployee(employeeId);
        assertNotNull(responseEntity);
        assertEquals(400,responseEntity.getStatusCodeValue());
        Response invalidRequestResponse = (Response) responseEntity.getBody();
        assertEquals("employee does not exist", invalidRequestResponse.getError());
    }
    @Test
    public void getMeetingsTestFailedDueToThriftException(){
        String employeeId = "xyz-25";
        Mockito.when(validateEmployeeId.checkEmployeeId(Mockito.anyString())).thenReturn(new ValidateResponse("employee exist",true));
        Mockito.when(meetingFacade.getMeetings(employeeId)).thenThrow(RuntimeException.class);
        assertThrows(RuntimeException.class, ()->meetingController.getMeetingsOfEmployee(employeeId));
    }

    @Test
    public void getMeetingsTestSucess(){
        String employeeId = "xyz-25";
        List<EmployeeMeetingDetails> employeeMeetingDetails = new ArrayList<>();
        employeeMeetingDetails.add(0, new EmployeeMeetingDetails(
                2,
                "accepted",
                "description",
                "agenda",
                "xyz-20",
                new Date(22,8,2022),
                new Time(11,00,00),
                new Time(12,00,00),
                true,
                2
        ));
        Response responseFromFacade = new Response(null,employeeMeetingDetails);
        Mockito.when(validateEmployeeId.checkEmployeeId(Mockito.anyString())).thenReturn(new ValidateResponse("employee exist",true));
        Mockito.when(meetingFacade.getMeetings(employeeId)).thenReturn(responseFromFacade);
        ResponseEntity responseFromGetMeetings = meetingController.getMeetingsOfEmployee(employeeId);
        assertNotNull(responseFromGetMeetings);
        assertEquals(200,responseFromGetMeetings.getStatusCodeValue());
        Response returnedResponseFromController = (Response) responseFromGetMeetings.getBody();
        assertTrue(returnedResponseFromController.getData().equals(employeeMeetingDetails));

    }
}