package org.example.CalendarManagement.calendarservice.implementation;

import integrationtestclasses.config.MockMeetingServiceClient;
import org.example.CalendarManagement.calendarpersistence.model.MeetingRoom;
import org.example.CalendarManagement.calendarpersistence.repository.MeetingRoomRepository;
import org.example.CalendarManagement.thriftclients.interfaces.MeetingServiceClient;
import org.example.CalendarThriftConfiguration.Date;
import org.example.CalendarThriftConfiguration.FindFreeMeetingRoomDataRequest;
import org.example.CalendarThriftConfiguration.Time;
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
class MeetingRoomServiceTest {
    @Mock
    MeetingRoomRepository meetingRoomRepository;
    @InjectMocks
    MeetingRoomService meetingRoomService;

    @Test
    public void meetingRoomServiceTest_findFreeMeetingRoom_RoomsClosed(){
        int officeId = 2;
        LocalDate dateOfMeeting = LocalDate.of(2022,07,27);
        LocalTime startTime = LocalTime.of(11 ,00);
        LocalTime endTime = LocalTime.of(12, 00);
        Mockito.when(meetingRoomRepository.findByOffice(2)).thenReturn(Arrays.asList());
        int responseFromService = meetingRoomService.findFreeMeetingRoom(officeId,dateOfMeeting,startTime,endTime);
        Assertions.assertEquals(0,responseFromService);
    }
    @Test
    public void meetingRoomServiceTest_findFreeMeetingRoom_RoomsNotAvailable(){
        int officeId = 2;
        LocalDate dateOfMeeting = LocalDate.of(2022,07,27);
        LocalTime startTime = LocalTime.of(11 ,00);
        LocalTime endTime = LocalTime.of(12, 00);
        Mockito.when(meetingRoomRepository.findByOffice(2)).thenReturn(Arrays.asList(1,2,3));
        int responseFromService = meetingRoomService.findFreeMeetingRoom(officeId,dateOfMeeting,startTime,endTime);
        Assertions.assertEquals(0,responseFromService);
    }
    @Test
    public void meetingRoomServiceTest_findFreeMeetingRoom_RoomAvailable(){
        int officeId = 2;
        LocalDate dateOfMeeting = LocalDate.of(2022,07,27);
        LocalTime startTime = LocalTime.of(11 ,00);
        LocalTime endTime = LocalTime.of(12, 00);
        Mockito.when(meetingRoomRepository.findByOffice(2)).thenReturn(Arrays.asList(1,2));
        int responseFromService = meetingRoomService.findFreeMeetingRoom(officeId,dateOfMeeting,startTime,endTime);
        Assertions.assertTrue(responseFromService>0);
    }
    @Test
    public void meetingRoomServiceTest_meetingRoomAvailable_NotAvailable(){
        int meetingRoomId = 10;
        String roomName = "reon-dev";
        LocalDate dateOfMeeting = LocalDate.of(2022,8,27);
        LocalTime startTime = LocalTime.of(11 ,00);
        LocalTime endTime = LocalTime.of(12, 00);
        Mockito.when(meetingRoomRepository.findByName(Mockito.anyString())).
                thenReturn(Optional.of(new MeetingRoom(roomName,2,true)));
        boolean responseFromService = meetingRoomService.meetingRoomAvailable(roomName,dateOfMeeting,startTime,endTime);
        assertFalse(responseFromService);
    }
    @Test
    public void meetingRoomServiceTest_meetingRoomAvailable_IsAvailable(){
        int meetingRoomId = 10;
        String roomName = "reon-dev";
        LocalDate dateOfMeeting = LocalDate.of(2022,07,27);
        LocalTime startTime = LocalTime.of(11 ,00);
        LocalTime endTime = LocalTime.of(12, 00);
        Mockito.when(meetingRoomRepository.findByName(Mockito.anyString())).
                thenReturn(Optional.of(new MeetingRoom(roomName,2,true)));
        boolean responseFromService = meetingRoomService.meetingRoomAvailable(roomName,dateOfMeeting,startTime,endTime);
        assertTrue(responseFromService);
    }
}