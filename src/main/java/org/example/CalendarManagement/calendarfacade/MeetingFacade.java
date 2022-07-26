package org.example.CalendarManagement.calendarfacade;

import org.example.CalendarManagement.api.Response;
import org.example.CalendarManagement.api.request.AddMeetingDataRequest;
import org.example.CalendarManagement.calendarservice.implementation.MeetingRoomService;
import org.example.CalendarManagement.thriftclients.interfaces.MeetingServiceClient;
import org.example.CalendarThriftConfiguration.Date;
import org.example.CalendarThriftConfiguration.EmployeeAvailabilityDataRequest;
import org.example.CalendarThriftConfiguration.Time;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MeetingFacade {
    @Autowired
    MeetingRoomService meetingRoomService;
    @Autowired
    MeetingServiceClient meetingServiceClient;

    public Response scheduleMeeting(AddMeetingDataRequest addMeetingDataRequest){
        Response response =null;
        Date dateOfMeeting = new Date(addMeetingDataRequest.getDateOfMeeting().getDayOfMonth(),addMeetingDataRequest.getDateOfMeeting().getMonthValue(),addMeetingDataRequest.getDateOfMeeting().getYear());
        Time meetingStartTime = new Time(addMeetingDataRequest.getStartTime().getHour(),addMeetingDataRequest.getStartTime().getMinute(),addMeetingDataRequest.getStartTime().getSecond());
        Time meetingEndTime = new Time(addMeetingDataRequest.getEndTime().getHour(),addMeetingDataRequest.getEndTime().getMinute(),addMeetingDataRequest.getEndTime().getSecond());
        EmployeeAvailabilityDataRequest employeeAvailabilityDataRequest = new EmployeeAvailabilityDataRequest(
                addMeetingDataRequest.getListOfEmployeeId(),
                meetingStartTime,
                meetingEndTime,
                dateOfMeeting
        );
        List<String> employeesNotAvailable = meetingServiceClient.checkEmployeeAvailability(employeeAvailabilityDataRequest);
        if(employeesNotAvailable.size()>0){
            response = new Response("these employees are not available", employeesNotAvailable);
            return response;
        }
        return response;
    }


}
