package org.example.CalendarManagement.calendarfacade;

import org.example.CalendarManagement.api.Response;
import org.example.CalendarManagement.api.request.AddMeetingDataRequest;
import org.example.CalendarManagement.calendarservice.implementation.MeetingRoomService;
import org.example.CalendarManagement.thriftclients.interfaces.MeetingServiceClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class MeetingFacadeTest {
    @Mock
    MeetingRoomService meetingRoomService;
    @Mock
    MeetingServiceClient meetingServiceClient;
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
        Response responseFromFacade = meetingFacade.scheduleMeeting(addMeetingDataRequest);
        assertNotNull(responseFromFacade);
    }


}