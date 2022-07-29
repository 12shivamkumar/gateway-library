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

@ExtendWith(MockitoExtension.class)
class ValidateMeetingRoomExistsInDbTest {

    @Mock
    MeetingRoomRepository meetingRoomRepository;

    @InjectMocks
    ValidateMeetingRoomExistsInDb validateMeetingRoomExistsInDb;

    @Test
    public void meetingRoomNotGiven()
    {
        Optional<String> meetingRoom = Optional.empty();
        ValidateResponse validateResponseForExistingMeetingRoom = validateMeetingRoomExistsInDb.checkMeetingRoomInDb(meetingRoom);
        Assertions.assertNotNull(validateResponseForExistingMeetingRoom);
        Assertions.assertTrue(validateResponseForExistingMeetingRoom.isValid());
    }

    @Test
    public void meetingRoomExists(){
        Optional<String> meetingRoomName = Optional.of("reon-team");
        Mockito.when(meetingRoomRepository.findByName(Mockito.anyString())).
                thenReturn(Optional.of(new MeetingRoom("reon-team",3,true)));
        ValidateResponse validateResponseForExistingMeetingRoom = validateMeetingRoomExistsInDb.checkMeetingRoomInDb(meetingRoomName);
        Assertions.assertNotNull(validateResponseForExistingMeetingRoom);
        Assertions.assertTrue(validateResponseForExistingMeetingRoom.isValid());
    }
    @Test
    public void meetingRoomDoesNotExists(){
        Optional<String> meetingRoomName = Optional.of("reon-team");
        Mockito.when(meetingRoomRepository.findByName(Mockito.anyString())).thenReturn(Optional.empty());
        ValidateResponse validateResponseForMeetingRoomNotInDb = validateMeetingRoomExistsInDb.checkMeetingRoomInDb(meetingRoomName);
        Assertions.assertNotNull(validateResponseForMeetingRoomNotInDb);
        Assertions.assertFalse(validateResponseForMeetingRoomNotInDb.isValid());
    }

}