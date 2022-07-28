
package org.example.CalendarManagement.calendarservice.implementation;


import org.example.CalendarManagement.calendarpersistence.model.MeetingRoom;
import org.example.CalendarManagement.calendarpersistence.repository.MeetingRoomRepository;
import org.example.CalendarManagement.calendarservice.interfaces.MeetingRoomInterface;
import org.example.CalendarManagement.thriftclients.interfaces.MeetingServiceClient;
import org.example.CalendarThriftConfiguration.Date;
import org.example.CalendarThriftConfiguration.FindFreeMeetingRoomDataRequest;
import org.example.CalendarThriftConfiguration.MeetingRoomAvailableDataRequest;
import org.example.CalendarThriftConfiguration.Time;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class MeetingRoomService implements MeetingRoomInterface {
    @Autowired
    private MeetingRoomRepository meetingRoomRepository;
    @Autowired
    private MeetingServiceClient meetingServiceClient;

    @Override
    public Optional<Integer> findFreeMeetingRoom(int officeID, LocalDate date, LocalTime startTime, LocalTime endTime) {
        List<Integer> listOfRoomsInOffice = meetingRoomRepository.findByOffice(officeID);
        if(listOfRoomsInOffice.size()==0){
            return Optional.empty();
        }
        Date dateOfMeeting = new Date(date.getDayOfMonth(),date.getMonthValue(),date.getYear());
        Time meetingStartTime = new Time(startTime.getHour(),startTime.getMinute(),startTime.getSecond());
        Time meetingEndTime =new Time(endTime.getHour(),endTime.getMinute(), endTime.getSecond());
        FindFreeMeetingRoomDataRequest  findFreeMeetingRoomDataRequest = new FindFreeMeetingRoomDataRequest(listOfRoomsInOffice,dateOfMeeting,meetingStartTime,meetingEndTime);
        Integer freeMeetingRoom = meetingServiceClient.findFreeMeetingRoom(findFreeMeetingRoomDataRequest);
        if(freeMeetingRoom==0) {
            return Optional.empty();
        }
        return Optional.of(freeMeetingRoom);
    }

    @Override
    public boolean meetingRoomAvailable(String roomName, LocalDate date, LocalTime startTime, LocalTime endTime) {
        Optional<MeetingRoom> meetingRoom = meetingRoomRepository.findByName(roomName);
        if(meetingRoom.isPresent()){
            if(meetingRoom.get().isOpen())
            {
                int roomId = meetingRoom.get().getRoomId();
                Date dateOfMeeting = new Date(date.getDayOfMonth(),date.getMonthValue(),date.getYear());
                Time meetingStartTime = new Time(startTime.getHour(),startTime.getMinute(),startTime.getSecond());
                Time meetingEndTime =new Time(endTime.getHour(),endTime.getMinute(), endTime.getSecond());
                MeetingRoomAvailableDataRequest meetingRoomAvailableDataRequest = new MeetingRoomAvailableDataRequest(roomId,dateOfMeeting,meetingStartTime,meetingEndTime);
                boolean meetingRoomAvailability = meetingServiceClient.meetingRoomAvailable(meetingRoomAvailableDataRequest);
                return meetingRoomAvailability;
            }
        }
        return false;
    }

}

