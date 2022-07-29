package org.example.CalendarManagement.calendarfacade;

import org.example.CalendarManagement.api.Response;
import org.example.CalendarManagement.api.request.AddMeetingDataRequest;
import org.example.CalendarManagement.calendarpersistence.model.MeetingRoom;
import org.example.CalendarManagement.calendarpersistence.repository.EmployeeRepository;
import org.example.CalendarManagement.calendarpersistence.repository.MeetingRoomRepository;
import org.example.CalendarManagement.calendarservice.implementation.MeetingRoomService;
import org.example.CalendarManagement.thriftclients.interfaces.MeetingServiceClient;
import org.example.CalendarManagement.thriftobjectmappers.AddMeetingDataRequestToMeetingDetailsMapper;
import org.example.CalendarManagement.thriftobjectmappers.EmployeeMeetingDetailsToMeetingsOfEmployee;
import org.example.CalendarManagement.thriftobjectmappers.MeetingsOfEmployee;
import org.example.CalendarThriftConfiguration.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class MeetingFacade {
    Logger logger = LoggerFactory.getLogger(EmployeeFacade.class);

    @Autowired
    MeetingRoomService meetingRoomService;
    @Autowired
    MeetingServiceClient meetingServiceClient;

    @Autowired
    MeetingRoomRepository meetingRoomRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Transactional
    public Response scheduleMeeting(AddMeetingDataRequest addMeetingDataRequest , int roomId){
        Response response =null;

        MeetingDetails meetingDetails = AddMeetingDataRequestToMeetingDetailsMapper.map(addMeetingDataRequest,roomId);

        int meetingId = 0;

        try {

            meetingId = meetingServiceClient.addMeetingDetails(meetingDetails);

            response = new Response(null, meetingId);
        }catch (Exception ex){
            logger.error(ex.getMessage());
            throw new RuntimeException(ex.getMessage());
        }
        return response;
    }

    public Response getMeetings(String employeeId) {
        try {
            List<EmployeeMeetingDetails> meetingsOfEmployeeForToday = meetingServiceClient.getEmployeeMeetingDetails(employeeId);
            List<MeetingsOfEmployee> meetingsOfEmployee = EmployeeMeetingDetailsToMeetingsOfEmployee.map(meetingsOfEmployeeForToday);
            return new Response(null, meetingsOfEmployee);
        } catch (Exception ex) {
            logger.error("get meetings failed due to exception", ex);
            throw new RuntimeException(ex.getMessage());
        }
    }
}
