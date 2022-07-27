package org.example.CalendarManagement.api.validator;

import org.example.CalendarManagement.calendarpersistence.model.MeetingRoom;
import org.example.CalendarManagement.calendarpersistence.repository.MeetingRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ValidateMeetingRoomExistsInDb {
    @Autowired
    MeetingRoomRepository meetingRoomRepository;

    public ValidateResponse checkMeetingRoomInDb(String meetingRoomName){
        if(meetingRoomName.equals(""))
            return new ValidateResponse("meeting room not given",true);

        Optional<MeetingRoom> meetingRoomResponseFromDb = meetingRoomRepository.findByName(meetingRoomName);
        if(meetingRoomResponseFromDb.isPresent()){
            return new ValidateResponse("meeting room present in db",true);
        }
        else{
            return new ValidateResponse("meeting room does not exist or is closed",false);
        }
    }
}
