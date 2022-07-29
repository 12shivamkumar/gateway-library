package org.example.CalendarManagement.thriftobjectmappers;

import org.example.CalendarThriftConfiguration.EmployeeMeetingDetails;

import java.util.ArrayList;
import java.util.List;

public class EmployeeMeetingDetailsToMeetingsOfEmployee {
    public static List<MeetingsOfEmployee> map(List<EmployeeMeetingDetails> employeeMeetingDetails){
        List<MeetingsOfEmployee> meetingsOfEmployee = new ArrayList<>();
        for(EmployeeMeetingDetails meetings: employeeMeetingDetails){
            DateTimeToLocalDateTime localDateTimeFormatt = DateTimeToLocalDateTime.map(meetings.getDateOfMeeting(),meetings.getStartTime(),meetings.getEndTime());
            MeetingsOfEmployee formattedMeetings = new MeetingsOfEmployee(
                    meetings.getMeetId(),
                    meetings.getStatus(),
                    meetings.getDescription(),
                    meetings.getAgenda(),
                    meetings.getOwnerId(),
                    localDateTimeFormatt.getDateOfMeeting(),
                    localDateTimeFormatt.getStartTime(),
                    localDateTimeFormatt.getEndTime(),
                    meetings.isIsAvailable(),
                    meetings.getRoomId()
            );
            meetingsOfEmployee.add(formattedMeetings);
        }
        return meetingsOfEmployee;
    }
}
