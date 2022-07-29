package org.example.CalendarManagement.api.validator;

import org.example.CalendarManagement.api.Response;
import org.example.CalendarManagement.api.request.AddMeetingDataRequest;
import org.example.CalendarManagement.calendarpersistence.model.MeetingRoom;
import org.example.CalendarManagement.calendarpersistence.repository.EmployeeRepository;
import org.example.CalendarManagement.calendarpersistence.repository.MeetingRoomRepository;
import org.example.CalendarManagement.calendarservice.implementation.MeetingRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ValidateMeetingRoomAvailability {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    MeetingRoomService meetingRoomService;

    @Autowired
    MeetingRoomRepository meetingRoomRepository;

    public ValidateResponse checkMeetingRoomAvailability(AddMeetingDataRequest addMeetingDataRequest)
    {
        ValidateResponse response = null;
        Optional<Integer> meetingRoomId;
        if(!addMeetingDataRequest.getRoomName().isPresent())
        {
            int officeId = employeeRepository.findOfficeIdById(addMeetingDataRequest.getOwnerId());
            meetingRoomId =  meetingRoomService.findFreeMeetingRoom(officeId,addMeetingDataRequest.getDateOfMeeting(),addMeetingDataRequest.getStartTime(),addMeetingDataRequest.getEndTime());

            if(!meetingRoomId.isPresent())
            {
                response = new ValidateResponse("No Meeting room is available", false);
                return response;
            }
            response = new ValidateResponse(meetingRoomId.get()+"", true);
        }
        else
        {
            boolean meetingRoomAvailableResponse = meetingRoomService.meetingRoomAvailable(addMeetingDataRequest.getRoomName().get(),
                    addMeetingDataRequest.getDateOfMeeting(),addMeetingDataRequest.getStartTime(),addMeetingDataRequest.getEndTime());

            if(!meetingRoomAvailableResponse)
            {
                response = new ValidateResponse("Given Meeting room is not available",false);
                return response;
            }
            Optional<MeetingRoom> meetingRoom = meetingRoomRepository.findByName(addMeetingDataRequest.getRoomName().get());

             int meetingRoomIds = meetingRoom.get().getRoomId();
            response = new ValidateResponse(meetingRoomIds+"", true);
        }
       return response;
    }

}
