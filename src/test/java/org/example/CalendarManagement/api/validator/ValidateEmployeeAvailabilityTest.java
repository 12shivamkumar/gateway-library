package org.example.CalendarManagement.api.validator;

import org.example.CalendarManagement.api.Response;
import org.example.CalendarManagement.api.request.AddMeetingDataRequest;
import org.example.CalendarManagement.thriftclients.interfaces.MeetingServiceClient;
import org.example.CalendarThriftConfiguration.Date;
import org.example.CalendarThriftConfiguration.EmployeeAvailabilityDataRequest;
import org.example.CalendarThriftConfiguration.Time;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ValidateEmployeeAvailabilityTest {
    @Mock
    MeetingServiceClient meetingServiceClient;

    @InjectMocks
    ValidateEmployeeAvailability validateEmployeeAvailability;

    @Test
    public void allEmployeeNotAvailable(){

        AddMeetingDataRequest addMeetingDataRequest = new AddMeetingDataRequest.Builder(
                "abc-10",
                "daily sync up",
                "details",
                Arrays.asList("abc-12","abc-13","abc-14","abc-15"),
                LocalDate.of(2022,8,25),
                LocalTime.of(11,00,00),
                LocalTime.of(12,30,00)
        ).roomName(Optional.of("reon-dev")).build();

        Date dateOfMeeting = new Date(addMeetingDataRequest.getDateOfMeeting().getDayOfMonth(),addMeetingDataRequest.getDateOfMeeting().getMonthValue(),addMeetingDataRequest.getDateOfMeeting().getYear());
        Time meetingStartTime = new Time(addMeetingDataRequest.getStartTime().getHour(),addMeetingDataRequest.getStartTime().getMinute(),addMeetingDataRequest.getStartTime().getSecond());
        Time meetingEndTime = new Time(addMeetingDataRequest.getEndTime().getHour(),addMeetingDataRequest.getEndTime().getMinute(),addMeetingDataRequest.getEndTime().getSecond());
        EmployeeAvailabilityDataRequest employeeAvailabilityDataRequest = new EmployeeAvailabilityDataRequest(
                addMeetingDataRequest.getListOfEmployeeId(),
                meetingStartTime,
                meetingEndTime,
                dateOfMeeting
        );

        Mockito.when(meetingServiceClient.checkEmployeeAvailability(Mockito.any(EmployeeAvailabilityDataRequest.class))).
                thenReturn(Arrays.asList("abc-12" , "abc-13"));
        ValidateResponse validateResponse = validateEmployeeAvailability.checkEmployeeAvailability(employeeAvailabilityDataRequest);
       assertNotNull(validateResponse);
       assertFalse(validateResponse.isValid());
    }

    @Test
    public void allEmployeeAvailable() {
        AddMeetingDataRequest addMeetingDataRequest = new AddMeetingDataRequest.Builder(
                "abc-10",
                "daily sync up",
                "details",
                Arrays.asList("abc-12","abc-13","abc-14","abc-15"),
                LocalDate.of(2022,8,25),
                LocalTime.of(11,00,00),
                LocalTime.of(12,30,00)
        ).roomName(Optional.of("reon-dev")).build();

        Date dateOfMeeting = new Date(addMeetingDataRequest.getDateOfMeeting().getDayOfMonth(),addMeetingDataRequest.getDateOfMeeting().getMonthValue(),addMeetingDataRequest.getDateOfMeeting().getYear());
        Time meetingStartTime = new Time(addMeetingDataRequest.getStartTime().getHour(),addMeetingDataRequest.getStartTime().getMinute(),addMeetingDataRequest.getStartTime().getSecond());
        Time meetingEndTime = new Time(addMeetingDataRequest.getEndTime().getHour(),addMeetingDataRequest.getEndTime().getMinute(),addMeetingDataRequest.getEndTime().getSecond());
        EmployeeAvailabilityDataRequest employeeAvailabilityDataRequest = new EmployeeAvailabilityDataRequest(
                addMeetingDataRequest.getListOfEmployeeId(),
                meetingStartTime,
                meetingEndTime,
                dateOfMeeting
        );
        Mockito.when(meetingServiceClient.checkEmployeeAvailability(Mockito.any(EmployeeAvailabilityDataRequest.class))).
                thenReturn(Arrays.asList());
        ValidateResponse validateResponse = validateEmployeeAvailability.checkEmployeeAvailability(employeeAvailabilityDataRequest);
        assertNotNull(validateResponse);
        assertNull(validateResponse.getMessage());
        assertTrue(validateResponse.isValid());
    }
}