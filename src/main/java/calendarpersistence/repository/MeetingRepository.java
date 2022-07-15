package calendarpersistence.repository;

import calendarpersistence.model.Employee;
import calendarpersistence.model.Meeting;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface MeetingRepository {
    Boolean findMeetingsThatOverlap(List<Employee> employeeList, LocalDate date, LocalTime startTime, LocalTime endTime);
    List<Meeting> getMeetingForRoom(int roomID, LocalDate date);
    Meeting setMeeting(String owner, String agenda, String description, List<Employee> employeeList, LocalDate date, LocalTime startTime, LocalTime endTime, int roomID);
    Boolean isRoomFree(int roomID, LocalDate date,LocalTime startTime,LocalTime endTime);
    Boolean cancelMeeting(String meetingID);
    List<Meeting> findMeetingsByID(List<String> meetingIDList);
    Meeting findMeetingByID(String meetingID);

}
