package org.example.CalendarManagement.api.validator;

import org.example.CalendarManagement.api.Response;
import org.example.CalendarManagement.api.request.AddMeetingDataRequest;
import org.example.CalendarManagement.calendarpersistence.model.MeetingRoom;
import org.example.CalendarManagement.calendarpersistence.repository.EmployeeRepository;
import org.example.CalendarManagement.calendarpersistence.repository.MeetingRoomRepository;
import org.example.CalendarManagement.calendarservice.implementation.MeetingRoomService;
import org.example.CalendarManagement.thriftclients.interfaces.MeetingServiceClient;
import org.example.CalendarThriftConfiguration.EmployeeAvailabilityDataRequest;
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
class ValidateMeetingRoomAvailabilityTest
{
    @Mock
    MeetingRoomService meetingRoomService;
    @Mock
    MeetingRoomRepository meetingRoomRepository;
    @Mock
    EmployeeRepository employeeRepository;

    @InjectMocks
    ValidateMeetingRoomAvailability validateMeetingRoomAvailability;

    @Test
    public void freeMeetingRoomNotAvailableWhenMeetingRoomIsNotGiven()
    {
        AddMeetingDataRequest addMeetingDataRequest = new AddMeetingDataRequest.Builder(
                "abc-10",
                "daily sync up",
                "details",
                Arrays.asList("abc-12","abc-13","abc-14","abc-15"),
                LocalDate.of(2022,8,25),
                LocalTime.of(11,00,00),
                LocalTime.of(12,30,00)
        ).roomName(Optional.empty()).build();

        Mockito.when(employeeRepository.findOfficeIdById(Mockito.any())).thenReturn(1);
        Mockito.when(meetingRoomService.findFreeMeetingRoom(1 , LocalDate.of(2022,8,25), LocalTime.of(11,00,00), LocalTime.of(12,30,00)))
                .thenReturn(Optional.empty());
        ValidateResponse validateResponse = validateMeetingRoomAvailability.checkMeetingRoomAvailability(addMeetingDataRequest);
        assertNotNull(validateResponse);
        assertFalse(validateResponse.isValid());
        assertEquals("No Meeting room is available", validateResponse.getMessage());
    }

    @Test
    public void GivenMeetingRoomNotFree()
    {
        AddMeetingDataRequest addMeetingDataRequest = new AddMeetingDataRequest.Builder(
                "abc-10",
                "daily sync up",
                "details",
                Arrays.asList("abc-12","abc-13","abc-14","abc-15"),
                LocalDate.of(2022,8,25),
                LocalTime.of(11,00,00),
                LocalTime.of(12,30,00)
        ).roomName(Optional.of("reon-dev")).build();

        Mockito.when(meetingRoomService.meetingRoomAvailable("reon-dev",  LocalDate.of(2022,8,25),LocalTime.of(11,00,00), LocalTime.of(12,30,00)))
                .thenReturn(false);
        ValidateResponse validateResponse = validateMeetingRoomAvailability.checkMeetingRoomAvailability(addMeetingDataRequest);
        assertNotNull(validateResponse);
        assertFalse(validateResponse.isValid());
        assertEquals( "Given Meeting room is not available" , validateResponse.getMessage());
    }

    @Test
    public void freeMeetingRoomAvailableWhenMeetingRoomIsNotGiven()
    {
        AddMeetingDataRequest addMeetingDataRequest = new AddMeetingDataRequest.Builder(
                "abc-10",
                "daily sync up",
                "details",
                Arrays.asList("abc-12","abc-13","abc-14","abc-15"),
                LocalDate.of(2022,8,25),
                LocalTime.of(11,00,00),
                LocalTime.of(12,30,00)
        ).roomName(Optional.empty()).build();

        Mockito.when(employeeRepository.findOfficeIdById(Mockito.any())).thenReturn(1);
        Mockito.when(meetingRoomService.findFreeMeetingRoom(1, LocalDate.of(2022,8,25),LocalTime.of(11,00,00),LocalTime.of(12,30,00)))
                .thenReturn(Optional.of(1));
        ValidateResponse validateResponse = validateMeetingRoomAvailability.checkMeetingRoomAvailability(addMeetingDataRequest);
        assertNotNull(validateResponse);
        assertTrue(validateResponse.isValid());
    }


    @Test
    public void GivenMeetingRoomFree()
    {
        AddMeetingDataRequest addMeetingDataRequest = new AddMeetingDataRequest.Builder(
                "abc-10",
                "daily sync up",
                "details",
                Arrays.asList("abc-12","abc-13","abc-14","abc-15"),
                LocalDate.of(2022,8,25),
                LocalTime.of(11,00,00),
                LocalTime.of(12,30,00)
        ).roomName(Optional.of("reon-dev")).build();

        Mockito.when(meetingRoomService.meetingRoomAvailable("reon-dev", LocalDate.of(2022,8,25),LocalTime.of(11,00,00),LocalTime.of(12,30,00))).
                thenReturn(true);
        Mockito.when(meetingRoomRepository.findByName("reon-dev")).thenReturn(Optional.of(new MeetingRoom("reon-dev" ,1, true)));
        ValidateResponse validateResponse = validateMeetingRoomAvailability.checkMeetingRoomAvailability(addMeetingDataRequest);
        assertNotNull(validateResponse);
        assertTrue(validateResponse.isValid());
    }

}