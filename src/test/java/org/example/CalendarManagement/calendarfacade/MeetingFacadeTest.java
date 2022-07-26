package org.example.CalendarManagement.calendarfacade;

import org.example.CalendarManagement.api.Response;
import org.example.CalendarManagement.api.request.AddMeetingDataRequest;
import org.example.CalendarManagement.calendarpersistence.model.MeetingRoom;
import org.example.CalendarManagement.calendarpersistence.repository.EmployeeRepository;
import org.example.CalendarManagement.calendarpersistence.repository.MeetingRoomRepository;
import org.example.CalendarManagement.calendarservice.implementation.MeetingRoomService;
import org.example.CalendarManagement.thriftclients.interfaces.MeetingServiceClient;
import org.example.CalendarThriftConfiguration.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class MeetingFacadeTest {
    @Mock
    MeetingRoomService meetingRoomService;
    @Mock
    MeetingServiceClient meetingServiceClient;
    @Mock
    MeetingRoomRepository meetingRoomRepository;
    @Mock
    EmployeeRepository employeeRepository;
    @InjectMocks
    MeetingFacade meetingFacade;

    @Test
    public void meetingFacadeTest_scheduleMeetingFailed_employeeNotAvailable(){
        AddMeetingDataRequest addMeetingDataRequest = new AddMeetingDataRequest(
                "xyz-12",
                "daily sync up",
                "details",
                Arrays.asList("xyz-12","xyz-13","xyz-14","xyz-15"),
                LocalDate.of(2022,8,25),
                LocalTime.of(11,00,00),
                LocalTime.of(12,30,00),
                "reon-dev"
        );
        Mockito.when(meetingServiceClient.checkEmployeeAvailability(Mockito.any(EmployeeAvailabilityDataRequest.class))).
                thenReturn(Arrays.asList("xyz-12" , "xyz-13"));
        Response responseFromFacade = meetingFacade.scheduleMeeting(addMeetingDataRequest);
        assertNotNull(responseFromFacade);
    }

    @Test
    public void meetingFacadeTest_scheduleMeetingFailedNoFreeMeetingRoomAvailable()
    {
        AddMeetingDataRequest addMeetingDataRequest = new AddMeetingDataRequest(
                "xyz-12",
                "daily sync up",
                "details",
                Arrays.asList("xyz-12","xyz-13","xyz-14","xyz-15"),
                LocalDate.of(2022,8,25),
                LocalTime.of(11,00,00),
                LocalTime.of(12,30,00),
                ""
        );

        Mockito.when(meetingServiceClient.checkEmployeeAvailability(Mockito.any(EmployeeAvailabilityDataRequest.class))).
                thenReturn(Arrays.asList());
        Mockito.when(employeeRepository.findOfficeIdById(Mockito.any())).thenReturn(1);
        Response responseFromFacade = meetingFacade.scheduleMeeting(addMeetingDataRequest);
        assertNotNull(responseFromFacade);
        assertEquals("No Meeting room is available", responseFromFacade.getError());
    }

    @Test
    public void meetingFacadeTest_scheduleMeetingFailedGivenMeetingRoomNotFree()
    {
        AddMeetingDataRequest addMeetingDataRequest = new AddMeetingDataRequest(
                "xyz-12",
                "daily sync up",
                "details",
                Arrays.asList("xyz-12","xyz-13","xyz-14","xyz-15"),
                LocalDate.of(2022,8,25),
                LocalTime.of(11,00,00),
                LocalTime.of(12,30,00),
                "reon-dev"
        );

        Mockito.when(meetingServiceClient.checkEmployeeAvailability(Mockito.any(EmployeeAvailabilityDataRequest.class))).
                thenReturn(Arrays.asList());
        Response responseFromFacade = meetingFacade.scheduleMeeting(addMeetingDataRequest);
        assertNotNull(responseFromFacade);
        assertEquals( "Given Meeting room is not available" , responseFromFacade.getError());
    }

    @Test
    public void meetingFacadeTest_scheduleMeetingSuccessFreeMeetingRoomAvailable()
    {
        AddMeetingDataRequest addMeetingDataRequest = new AddMeetingDataRequest(
                "xyz-12",
                "daily sync up",
                "details",
                Arrays.asList("xyz-12","xyz-13","xyz-14","xyz-15"),
                LocalDate.of(2022,8,25),
                LocalTime.of(11,00,00),
                LocalTime.of(12,30,00),
                ""
        );

        Mockito.when(meetingServiceClient.checkEmployeeAvailability(Mockito.any(EmployeeAvailabilityDataRequest.class))).
                thenReturn(Arrays.asList());
        Mockito.when(employeeRepository.findOfficeIdById(Mockito.any())).thenReturn(1);
        Mockito.when(meetingRoomService.findFreeMeetingRoom(1, LocalDate.of(2022,8,25),LocalTime.of(11,00,00),LocalTime.of(12,30,00)))
                .thenReturn(1);
        Response responseFromFacade = meetingFacade.scheduleMeeting(addMeetingDataRequest);
        assertNotNull(responseFromFacade);
        Boolean savedSuccessfully = (Boolean)responseFromFacade.getData();
        assertTrue(savedSuccessfully);
    }


    @Test
    public void meetingFacadeTest_scheduleMeetingSuccessGivenMeetingRoomFree()
    {
        AddMeetingDataRequest addMeetingDataRequest = new AddMeetingDataRequest(
                "xyz-12",
                "daily sync up",
                "details",
                Arrays.asList("xyz-12","xyz-13","xyz-14","xyz-15"),
                LocalDate.of(2022,8,25),
                LocalTime.of(11,00,00),
                LocalTime.of(12,30,00),
                "reon-dev"
        );

        Mockito.when(meetingServiceClient.checkEmployeeAvailability(Mockito.any(EmployeeAvailabilityDataRequest.class))).
                thenReturn(Arrays.asList());
        Mockito.when(meetingRoomService.meetingRoomAvailable("reon-dev", LocalDate.of(2022,8,25),LocalTime.of(11,00,00),LocalTime.of(12,30,00))).
                thenReturn(true);
        Mockito.when(meetingRoomRepository.findByName("reon-dev")).thenReturn(Optional.of(new MeetingRoom("reon-dev" ,1, true)));
        Response responseFromFacade = meetingFacade.scheduleMeeting(addMeetingDataRequest);
        assertNotNull(responseFromFacade);
        Boolean savedSuccessfully = (Boolean)responseFromFacade.getData();
        assertTrue(savedSuccessfully);
    }

    @Test
    public void meetingFacadeTest_saveMeetingDetails_meetingDetailsSavedSuccessfully()
    {
        AddMeetingDataRequest addMeetingDataRequest = new AddMeetingDataRequest(
                "xyz-12",
                "daily sync up",
                "details",
                Arrays.asList("xyz-12","xyz-13","xyz-14","xyz-15"),
                LocalDate.of(2022,8,25),
                LocalTime.of(11,00,00),
                LocalTime.of(12,30,00),
                "reon-dev"
        );
        Mockito.when(meetingServiceClient.checkEmployeeAvailability(Mockito.any(EmployeeAvailabilityDataRequest.class))).
                thenReturn(Arrays.asList());
        Mockito.when(meetingRoomRepository.findByName("reon-dev")).thenReturn(Optional.of(new MeetingRoom("reon-dev" ,1, true)));
        Mockito.when(meetingRoomService.meetingRoomAvailable("reon-dev", LocalDate.of(2022,8,25),LocalTime.of(11,00,00),LocalTime.of(12,30,00))).
                thenReturn(true);
        Mockito.when(meetingServiceClient.addMeetingDetails(Mockito.any(MeetingDetails.class))).thenReturn("20128229");
        Response responseFromFacade = meetingFacade.scheduleMeeting(addMeetingDataRequest);
        assertNotNull(responseFromFacade);
        Boolean savedSuccessfully = (Boolean)responseFromFacade.getData();
        assertTrue(savedSuccessfully);
    }

    @Test
    public void meetingFacadeTest_saveMeetingDetails_meetingDetailsNotSavedSuccessfully()
    {
        AddMeetingDataRequest addMeetingDataRequest = new AddMeetingDataRequest(
                "xyz-12",
                "daily sync up",
                "details",
                Arrays.asList("xyz-12","xyz-13","xyz-14","xyz-15"),
                LocalDate.of(2022,8,25),
                LocalTime.of(11,00,00),
                LocalTime.of(12,30,00),
                "reon-dev"
        );
        Mockito.when(meetingServiceClient.checkEmployeeAvailability(Mockito.any(EmployeeAvailabilityDataRequest.class))).
                thenReturn(Arrays.asList());
        Mockito.when(meetingRoomRepository.findByName("reon-dev")).thenReturn(Optional.of(new MeetingRoom("reon-dev" ,1, true)));
        Mockito.when(meetingRoomService.meetingRoomAvailable("reon-dev", LocalDate.of(2022,8,25),LocalTime.of(11,00,00),LocalTime.of(12,30,00))).
                thenReturn(true);
        Mockito.when(meetingServiceClient.addMeetingDetails(Mockito.any(MeetingDetails.class))).thenThrow(RuntimeException.class);
        Assertions.assertThrows(RuntimeException.class , () -> meetingFacade.scheduleMeeting(addMeetingDataRequest));
    }

    @Test
    public void meetingFacadeTest_saveEmployeeMeetingStatus_statusSavedSuccessfully()
    {
        AddMeetingDataRequest addMeetingDataRequest = new AddMeetingDataRequest(
                "xyz-12",
                "daily sync up",
                "details",
                Arrays.asList("xyz-12","xyz-13","xyz-14","xyz-15"),
                LocalDate.of(2022,8,25),
                LocalTime.of(11,00,00),
                LocalTime.of(12,30,00),
                "reon-dev"
        );
        Mockito.when(meetingServiceClient.checkEmployeeAvailability(Mockito.any(EmployeeAvailabilityDataRequest.class))).
                thenReturn(Arrays.asList());
        Mockito.when(meetingRoomRepository.findByName("reon-dev")).thenReturn(Optional.of(new MeetingRoom("reon-dev" ,1, true)));
        Mockito.when(meetingRoomService.meetingRoomAvailable("reon-dev", LocalDate.of(2022,8,25),LocalTime.of(11,00,00),LocalTime.of(12,30,00))).
                thenReturn(true);
        Mockito.when(meetingServiceClient.addMeetingDetails(Mockito.any(MeetingDetails.class))).thenReturn("20128229");
        Mockito.when(meetingServiceClient.addEmployeeMeetingStatus(Mockito.anyList())).thenReturn(true);
        Response responseFromFacade = meetingFacade.scheduleMeeting(addMeetingDataRequest);
        assertNotNull(responseFromFacade);
        boolean responseStatusSaved = (Boolean)responseFromFacade.getData();
        assertTrue(responseStatusSaved);
    }

    @Test
    public void meetingFacadeTest_saveEmployeeMeetingStatus_statusNotSavedSuccessfully()
    {
        AddMeetingDataRequest addMeetingDataRequest = new AddMeetingDataRequest(
                "xyz-12",
                "daily sync up",
                "details",
                Arrays.asList("xyz-12","xyz-13","xyz-14","xyz-15"),
                LocalDate.of(2022,8,25),
                LocalTime.of(11,00,00),
                LocalTime.of(12,30,00),
                "reon-dev"
        );
        Mockito.when(meetingServiceClient.checkEmployeeAvailability(Mockito.any(EmployeeAvailabilityDataRequest.class))).
                thenReturn(Arrays.asList());
        Mockito.when(meetingRoomRepository.findByName("reon-dev")).thenReturn(Optional.of(new MeetingRoom("reon-dev" ,1, true)));
        Mockito.when(meetingRoomService.meetingRoomAvailable("reon-dev", LocalDate.of(2022,8,25),LocalTime.of(11,00,00),LocalTime.of(12,30,00))).
                thenReturn(true);
        Mockito.when(meetingServiceClient.addMeetingDetails(Mockito.any(MeetingDetails.class))).thenReturn("20128229");
        Mockito.when(meetingServiceClient.addEmployeeMeetingStatus(Mockito.anyList())).thenThrow(RuntimeException.class);
        Assertions.assertThrows(RuntimeException.class , () -> meetingFacade.scheduleMeeting(addMeetingDataRequest));
    }
}