package calendarservice.interfaces;

import calendarpersistence.model.MeetingRoom;

import java.time.LocalDate;
import java.time.LocalTime;

public interface MeetingRoomService {
    MeetingRoom addMeetingRoom(MeetingRoom meetingRoom);
    void setMeetingRoomByID(int roomID,Boolean open);
    Void setMeetingRoomByName(String roomName,Boolean open);
    MeetingRoom findMeetingRoomByRoomID(int roomID);
    int findMeetingRoomIDByRoomName(String roomName);
    String findMeetingRoomNameByRoomId(int ID);
    int findFreeMeetingRoom(int officeID, LocalDate date, LocalTime startTime, LocalTime endTime);
}
