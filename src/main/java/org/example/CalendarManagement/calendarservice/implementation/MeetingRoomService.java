package org.example.CalendarManagement.calendarservice.implementation;

import org.example.CalendarManagement.calendarpersistence.model.MeetingRoom;
import org.example.CalendarManagement.calendarpersistence.repository.MeetingRoomRepository;
import org.example.CalendarManagement.calendarservice.interfaces.MeetingRoomInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class MeetingRoomService implements MeetingRoomInterface {
    @Autowired
    private MeetingRoomRepository meetingRoomRepository;
    @Override
    public MeetingRoom addMeetingRoom(MeetingRoom meetingRoom) {
        meetingRoomRepository.save(meetingRoom);
        return meetingRoom;
    }

    @Override
    public void setMeetingRoomByID(int roomID, Boolean open) {

    }

    @Override
    public Void setMeetingRoomByName(String roomName, Boolean open) {
        return null;
    }

    @Override
    public MeetingRoom findMeetingRoomByRoomID(int roomID) {
        return null;
    }

    @Override
    public int findMeetingRoomIDByRoomName(String roomName) {
        return 0;
    }

    @Override
    public String findMeetingRoomNameByRoomId(int ID) {
        return null;
    }

    @Override
    public int findFreeMeetingRoom(int officeID, LocalDate date, LocalTime startTime, LocalTime endTime) {
        return 0;
    }
}
