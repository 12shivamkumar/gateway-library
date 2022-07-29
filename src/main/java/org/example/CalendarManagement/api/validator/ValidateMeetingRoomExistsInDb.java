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

    public ValidateResponse checkMeetingRoomInDb(Optional<String> meetingRoomName){

        if(!meetingRoomName.isPresent())
            return new ValidateResponse("meeting room is not entered by owner search for any room",true);

        String meetingRoomNamePresent = meetingRoomName.get();

        Optional<MeetingRoom> meetingRoomResponseFromDb = meetingRoomRepository.findByName(meetingRoomNamePresent);
        if(meetingRoomResponseFromDb.isPresent()){
            return new ValidateResponse("meeting room present in db",true);
        }
        else{
            return new ValidateResponse("meeting room does not exist or is closed",false);
        }
    }
}
