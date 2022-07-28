package org.example.CalendarManagement.thriftobjectmappers;

import org.example.CalendarManagement.api.request.AddMeetingDataRequest;
import org.example.CalendarThriftConfiguration.Date;
import org.example.CalendarThriftConfiguration.MeetingDetails;
import org.example.CalendarThriftConfiguration.Time;

public class AddMeetingDataRequestToMeetingDetailsMapper {
    public static MeetingDetails map(AddMeetingDataRequest addMeetingDataRequest,int meetingRoomId){
        MeetingDetails meetingDetails = new MeetingDetails();
        addMeetingDataRequest.getListOfEmployeeId().add(addMeetingDataRequest.getOwnerId());
        meetingDetails.setListOfEmployee(addMeetingDataRequest.getListOfEmployeeId());
        meetingDetails.setDescription(addMeetingDataRequest.getDescription());
        meetingDetails.setAgenda(addMeetingDataRequest.getAgenda());
        meetingDetails.setOwnerId(addMeetingDataRequest.getOwnerId());
        meetingDetails.setDateOfMeeting(new Date(addMeetingDataRequest.getDateOfMeeting().getDayOfMonth(),addMeetingDataRequest.getDateOfMeeting().getMonthValue(),addMeetingDataRequest.getDateOfMeeting().getYear()));
        meetingDetails.setStartTime(new Time(addMeetingDataRequest.getStartTime().getHour(),addMeetingDataRequest.getStartTime().getMinute(),addMeetingDataRequest.getStartTime().getSecond()));
        meetingDetails.setEndTime(new Time(addMeetingDataRequest.getEndTime().getHour(),addMeetingDataRequest.getEndTime().getMinute(),addMeetingDataRequest.getEndTime().getSecond()));
        meetingDetails.setIsAvailable(true);
        meetingDetails.setRoomId(meetingRoomId);
        return meetingDetails;
    }
}
