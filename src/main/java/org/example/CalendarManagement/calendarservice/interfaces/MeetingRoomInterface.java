package org.example.CalendarManagement.calendarservice.interfaces;

//import org.example.CalendarManagement.calendarpersistence.model.MeetingRoom;

import org.example.CalendarManagement.calendarpersistence.model.MeetingRoom;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.time.LocalTime;

@Component
public interface MeetingRoomInterface {
    Integer findFreeMeetingRoom(int officeID, LocalDate date, LocalTime startTime, LocalTime endTime);
    boolean meetingRoomAvailable(String roomName, LocalDate date, LocalTime startTime, LocalTime endTime);
}

