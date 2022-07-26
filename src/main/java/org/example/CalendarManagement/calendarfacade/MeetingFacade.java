package org.example.CalendarManagement.calendarfacade;

import org.example.CalendarManagement.api.Response;
import org.example.CalendarManagement.api.request.AddMeetingDataRequest;
import org.example.CalendarManagement.calendarpersistence.model.MeetingRoom;
import org.example.CalendarManagement.calendarpersistence.repository.EmployeeRepository;
import org.example.CalendarManagement.calendarpersistence.repository.MeetingRoomRepository;
import org.example.CalendarManagement.calendarservice.implementation.MeetingRoomService;
import org.example.CalendarManagement.thriftclients.interfaces.MeetingServiceClient;
import org.example.CalendarThriftConfiguration.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class MeetingFacade {
    @Autowired
    MeetingRoomService meetingRoomService;
    @Autowired
    MeetingServiceClient meetingServiceClient;

    @Autowired
    MeetingRoomRepository meetingRoomRepository;

    @Autowired
    EmployeeRepository employeeRepository;

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

        Integer meetingRoomId = 0;

        if(addMeetingDataRequest.getRoomName().equals(""))
        {
            int officeId = employeeRepository.findOfficeIdById(addMeetingDataRequest.getOwnerId());
            meetingRoomId =  meetingRoomService.findFreeMeetingRoom(officeId,addMeetingDataRequest.getDateOfMeeting(),addMeetingDataRequest.getStartTime(),addMeetingDataRequest.getEndTime());

            if(meetingRoomId == 0)
            {
                response = new Response("No Meeting room is available", meetingRoomId);
                return response;
            }
            response = new Response(null, meetingRoomId);
        }
        else
        {
            boolean meetingRoomAvailableResponse = meetingRoomService.meetingRoomAvailable(addMeetingDataRequest.getRoomName(),
                    addMeetingDataRequest.getDateOfMeeting(),addMeetingDataRequest.getStartTime(),addMeetingDataRequest.getEndTime());

            if(!meetingRoomAvailableResponse)
            {
                response = new Response("Given Meeting room is not available",false);
                return response;
            }
            Optional<MeetingRoom> meetingRoom = meetingRoomRepository.findByName(addMeetingDataRequest.getRoomName());
            meetingRoomId = meetingRoom.get().getRoomId();
            response = new Response(null, meetingRoom.get().getRoomName());
        }


        MeetingDetails meetingDetails = new MeetingDetails();
        meetingDetails.setDescription(addMeetingDataRequest.getDescription());
        meetingDetails.setAgenda(addMeetingDataRequest.getAgenda());
        meetingDetails.setOwnerId(addMeetingDataRequest.getOwnerId());
        meetingDetails.setDateOfMeeting(new Date(addMeetingDataRequest.getDateOfMeeting().getDayOfMonth(),addMeetingDataRequest.getDateOfMeeting().getMonthValue(),addMeetingDataRequest.getDateOfMeeting().getYear()));
        meetingDetails.setStartTime(new Time(addMeetingDataRequest.getStartTime().getHour(),addMeetingDataRequest.getStartTime().getMinute(),addMeetingDataRequest.getStartTime().getSecond()));
        meetingDetails.setEndTime(new Time(addMeetingDataRequest.getEndTime().getHour(),addMeetingDataRequest.getEndTime().getMinute(),addMeetingDataRequest.getEndTime().getSecond()));
        meetingDetails.setIsAvailable(true);
        meetingDetails.setRoomId(meetingRoomId);

        String meetingId = null;

        try {
            meetingId = meetingServiceClient.addMeetingDetails(meetingDetails);
            response = new Response(null, meetingId);
        }catch (RuntimeException ex){
            throw new RuntimeException(ex.getMessage());
        }

        List<EmployeeStatusDataRequest> employeeStatusDataRequestArrayList = new ArrayList<EmployeeStatusDataRequest>();

        List<String> employeeIdList = addMeetingDataRequest.getListOfEmployeeId();

        for (String employeeId : employeeIdList)
        {
            EmployeeStatusDataRequest employeeStatusDataRequest = new EmployeeStatusDataRequest();
            employeeStatusDataRequest.setEmployeeId(employeeId);
            employeeStatusDataRequest.setMeetingId(meetingId);
            employeeStatusDataRequest.setStatus("Pending");
            employeeStatusDataRequest.setDateOfMeeting(dateOfMeeting);

            employeeStatusDataRequestArrayList.add(employeeStatusDataRequest);
        }

        try {
            boolean addEmployeeMeetingStatusResponse  = meetingServiceClient.addEmployeeMeetingStatus(employeeStatusDataRequestArrayList);
            response = new Response(null , true);
        }catch (RuntimeException ex){
            throw new RuntimeException(ex.getMessage());
        }

        return response;
    }
}
