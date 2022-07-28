package org.example.CalendarManagement.calendarservice.implementation;

import org.example.CalendarManagement.calendarpersistence.model.MeetingRoom;
import org.example.CalendarManagement.calendarpersistence.repository.MeetingRoomRepository;
import org.example.CalendarManagement.thriftclients.interfaces.MeetingServiceClient;
import org.example.CalendarThriftConfiguration.Date;
import org.example.CalendarThriftConfiguration.FindFreeMeetingRoomDataRequest;
import org.example.CalendarThriftConfiguration.MeetingRoomAvailableDataRequest;
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
    MeetingServiceClient meetingServiceClient;
    @Mock
    MeetingRoomRepository meetingRoomRepository;
    @InjectMocks
    MeetingRoomService meetingRoomService;

    @Test
    public void findFreeMeetingRoom_RoomsClosed(){
        int officeId = 2;
        LocalDate dateOfMeeting = LocalDate.of(2022,07,27);
        LocalTime startTime = LocalTime.of(11 ,00);
        LocalTime endTime = LocalTime.of(12, 00);
        Mockito.when(meetingRoomRepository.findByOffice(2)).thenReturn(Arrays.asList());
        Optional<Integer> responseFromService = meetingRoomService.findFreeMeetingRoom(officeId,dateOfMeeting,startTime,endTime);
        Assertions.assertFalse(responseFromService.isPresent());
    }
    @Test
    public void findFreeMeetingRoom_RoomsNotAvailable(){
        int officeId = 2;
        LocalDate dateOfMeeting = LocalDate.of(2022,07,27);
        LocalTime startTime = LocalTime.of(11 ,00);
        LocalTime endTime = LocalTime.of(12, 00);
        Mockito.when(meetingRoomRepository.findByOffice(2)).thenReturn(Arrays.asList(1,2,3));
        Mockito.when(meetingServiceClient.findFreeMeetingRoom(Mockito.any(FindFreeMeetingRoomDataRequest.class))).
                thenReturn(0);
        Optional<Integer> responseFromService = meetingRoomService.findFreeMeetingRoom(officeId,dateOfMeeting,startTime,endTime);
        Assertions.assertFalse(responseFromService.isPresent());
    }
    @Test
    public void findFreeMeetingRoom_RoomAvailable(){
        int officeId = 2;
        LocalDate dateOfMeeting = LocalDate.of(2022,07,27);
        LocalTime startTime = LocalTime.of(11 ,00);
        LocalTime endTime = LocalTime.of(12, 00);
        Mockito.when(meetingRoomRepository.findByOffice(2)).thenReturn(Arrays.asList(1,2));
        Mockito.when(meetingServiceClient.findFreeMeetingRoom(Mockito.any(FindFreeMeetingRoomDataRequest.class))).
                thenReturn(2);
        Optional<Integer> responseFromService = meetingRoomService.findFreeMeetingRoom(officeId,dateOfMeeting,startTime,endTime);
        Assertions.assertTrue(responseFromService.isPresent());
    }

    @Test
    public void meetingRoomAvailable_roomClosed(){
        int meetingRoomId = 10;
        String roomName = "reon-dev";
        LocalDate dateOfMeeting = LocalDate.of(2022,8,27);
        LocalTime startTime = LocalTime.of(11 ,00);
        LocalTime endTime = LocalTime.of(12, 00);
        Mockito.when(meetingRoomRepository.findByName(Mockito.anyString())).
                thenReturn(Optional.of(new MeetingRoom(roomName,2,false)));
        boolean responseFromService = meetingRoomService.meetingRoomAvailable(roomName,dateOfMeeting,startTime,endTime);
        assertFalse(responseFromService);
    }

    @Test
    public void meetingRoomAvailable_NotAvailable(){
        int meetingRoomId = 10;
        String roomName = "reon-dev";
        LocalDate dateOfMeeting = LocalDate.of(2022,8,27);
        LocalTime startTime = LocalTime.of(11 ,00);
        LocalTime endTime = LocalTime.of(12, 00);
        Mockito.when(meetingRoomRepository.findByName(Mockito.anyString())).
                thenReturn(Optional.of(new MeetingRoom(roomName,2,true)));
        Mockito.when(meetingServiceClient.meetingRoomAvailable(Mockito.any(MeetingRoomAvailableDataRequest.class))).thenReturn(false);
        boolean responseFromService = meetingRoomService.meetingRoomAvailable(roomName,dateOfMeeting,startTime,endTime);
        assertFalse(responseFromService);
    }
    @Test
    public void meetingRoomAvailable_IsAvailable(){
        int meetingRoomId = 10;
        String roomName = "reon-dev";
        LocalDate dateOfMeeting = LocalDate.of(2022,07,27);
        LocalTime startTime = LocalTime.of(11 ,00);
        LocalTime endTime = LocalTime.of(12, 00);
        Mockito.when(meetingRoomRepository.findByName(Mockito.anyString())).
                thenReturn(Optional.of(new MeetingRoom(roomName,2,true)));
        Mockito.when(meetingServiceClient.meetingRoomAvailable(Mockito.any(MeetingRoomAvailableDataRequest.class))).thenReturn(true);
        boolean responseFromService = meetingRoomService.meetingRoomAvailable(roomName,dateOfMeeting,startTime,endTime);
        assertTrue(responseFromService);
    }
}