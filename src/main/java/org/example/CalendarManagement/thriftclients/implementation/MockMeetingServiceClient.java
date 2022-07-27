package org.example.CalendarManagement.thriftclients.implementation;

import org.example.CalendarManagement.thriftclients.interfaces.MeetingServiceClient;
import org.example.CalendarThriftConfiguration.*;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;


@Component
@Profile("test")
public class MockMeetingServiceClient implements MeetingServiceClient {
    @Override
    public boolean cancelMeetingForRemovedEmployee(String employeeId) {
        System.out.println("cancel meeting method");
        return  true;
    }
    @Override
    public boolean updateStatusForRemovedEmployee(String employeeId){
        System.out.println("update status method");
        return  true;
    }

    @Override
    public List<String> checkEmployeeAvailability(EmployeeAvailabilityDataRequest employeeAvailabilityDataRequest) {

        if(employeeAvailabilityDataRequest.listOfEmployeeId.size()==4){
            return Arrays.asList("xyz-3");
        }
        return Arrays.asList();
    }

    @Override
    public String addMeetingDetails(MeetingDetails meetingDetails) {
        return "20128229";
    }

    @Override
    public boolean addEmployeeMeetingStatus(List<EmployeeStatusDataRequest> employeeStatusDataRequestStatuses) {
        return true;
    }

    @Override
    public Integer findFreeMeetingRoom(FindFreeMeetingRoomDataRequest findFreeMeetingRoomDataRequest) {
        System.out.println(findFreeMeetingRoomDataRequest.getMeetingRoomsInOfficeSize());
        if(findFreeMeetingRoomDataRequest.getMeetingRoomsInOfficeSize()<2){
            return 2;
        }
        return 0;
    }

    @Override
    public boolean meetingRoomAvailable(MeetingRoomAvailableDataRequest meetingRoomAvailableDataRequest) {
        Date date1 = new Date(26,8,2022);
        if(meetingRoomAvailableDataRequest.getDateOfMeeting().equals(date1)){
            return true;
        }
        return false;
    }

    @Override
    public String isAlive() {
        return null;
    }

}