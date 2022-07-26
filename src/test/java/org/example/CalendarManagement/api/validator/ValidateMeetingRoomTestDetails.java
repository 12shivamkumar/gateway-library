package org.example.CalendarManagement.api.validator;

import org.example.CalendarManagement.calendarpersistence.model.MeetingRoom;
import org.example.CalendarManagement.calendarpersistence.repository.MeetingRoomRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ValidateMeetingRoomTestDetails {

    @Mock
    MeetingRoomRepository meetingRoomRepository;

    @InjectMocks
    ValidateMeetingRoom validateMeetingRoom;

    @Test
    public void validateMeetingRoomTest_meetingRoomExists(){
        String meetingRoomName = "reon-team";
        Mockito.when(meetingRoomRepository.findByName(Mockito.anyString())).
                thenReturn(Optional.of(new MeetingRoom("reon-team",3,true)));
        ValidateResponse validateResponseForExistingMeetingRoom = validateMeetingRoom.checkMeetingRoomInDb(meetingRoomName);
        Assertions.assertNotNull(validateResponseForExistingMeetingRoom);
        Assertions.assertTrue(validateResponseForExistingMeetingRoom.isValid());
    }
    @Test
    public void validateMeetingRoomTest_meetingRoomDoesNotExists(){
        String meetingRoomName = "reon-team";
        Mockito.when(meetingRoomRepository.findByName(Mockito.anyString())).thenReturn(Optional.empty());
        ValidateResponse validateResponseForMeetingRoomNotInDb = validateMeetingRoom.checkMeetingRoomInDb(meetingRoomName);
        Assertions.assertNotNull(validateResponseForMeetingRoomNotInDb);
        Assertions.assertFalse(validateResponseForMeetingRoomNotInDb.isValid());
    }

}