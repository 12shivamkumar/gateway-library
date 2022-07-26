package org.example.CalendarManagement.calendarservice.interfaces;

//import org.example.CalendarManagement.calendarpersistence.model.MeetingRoom;

import org.example.CalendarManagement.calendarpersistence.model.MeetingRoom;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
public interface MeetingRoomInterface {


    MeetingRoom findMeetingRoomByRoomID(int roomID);
    int findMeetingRoomIDByRoomName(String roomName);
    int findFreeMeetingRoom(int officeID, LocalDate date, LocalTime startTime, LocalTime endTime);
    boolean meetingRoomAvailable(String roomName, LocalDate date, LocalTime startTime, LocalTime endTime);
}

