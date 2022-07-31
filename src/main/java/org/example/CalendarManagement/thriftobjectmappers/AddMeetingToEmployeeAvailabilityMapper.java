package org.example.CalendarManagement.thriftobjectmappers;

import org.example.CalendarManagement.api.request.AddMeetingDataRequest;
import org.example.CalendarThriftConfiguration.Date;
import org.example.CalendarThriftConfiguration.EmployeeAvailabilityDataRequest;
import org.example.CalendarThriftConfiguration.Time;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class AddMeetingToEmployeeAvailabilityMapper {

    public static EmployeeAvailabilityDataRequest map(AddMeetingDataRequest addMeetingDataRequest){
        Date dateOfMeeting = new Date(addMeetingDataRequest.getDateOfMeeting().getDayOfMonth(),addMeetingDataRequest.getDateOfMeeting().getMonthValue(),addMeetingDataRequest.getDateOfMeeting().getYear());
        Time meetingStartTime = new Time(addMeetingDataRequest.getStartTime().getHour(),addMeetingDataRequest.getStartTime().getMinute(),addMeetingDataRequest.getStartTime().getSecond());
        Time meetingEndTime = new Time(addMeetingDataRequest.getEndTime().getHour(),addMeetingDataRequest.getEndTime().getMinute(),addMeetingDataRequest.getEndTime().getSecond());
        List<String> listOfEmployee = new ArrayList<>(addMeetingDataRequest.getListOfEmployeeId());
        listOfEmployee.add(addMeetingDataRequest.getOwnerId());
        return new EmployeeAvailabilityDataRequest(
                listOfEmployee,
                meetingStartTime,
                meetingEndTime,
                dateOfMeeting
        );
    }
}
