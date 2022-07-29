package org.example.CalendarManagement.thriftclients.implementation;

import org.apache.thrift.TException;
import org.example.CalendarManagement.thriftclients.interfaces.MeetingServiceClient;
import org.example.CalendarThriftConfiguration.*;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Component
@Profile("test")
public class MockMeetingServiceClient implements MeetingServiceClient {
    @Override
    public boolean cancelMeetingForRemovedEmployee(String employeeId) {
       return  true;
    }
    @Override
    public boolean updateStatusForRemovedEmployee(String employeeId){
        return  true;
    }

    @Override
    public List<String> checkEmployeeAvailability(EmployeeAvailabilityDataRequest employeeAvailabilityDataRequest) {

        if(employeeAvailabilityDataRequest.listOfEmployeeId.size()==4){
            return Arrays.asList("xyz-3");
        }
        if(employeeAvailabilityDataRequest.listOfEmployeeId.size()==3){
            throw new RuntimeException("Thrift down");
        }
        return Arrays.asList();
    }

    @Override
    public int addMeetingDetails(MeetingDetails meetingDetails) {
        return 20128229;
    }


    @Override

    public int findFreeMeetingRoom(FindFreeMeetingRoomDataRequest findFreeMeetingRoomDataRequest) {
        if(findFreeMeetingRoomDataRequest.getMeetingRoomsInOfficeSize()<3){
            return 3;
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
    public List<EmployeeMeetingDetails> getEmployeeMeetingDetails(String employeeId) {
        if(employeeId.equals("abc-15")){
            throw new RuntimeException("mocking internal server error");
        }
        List<EmployeeMeetingDetails> employeeMeetingDetails = new ArrayList<>();
        EmployeeMeetingDetails meetingOfEmployee = new EmployeeMeetingDetails(
                2,
                "accepted",
                "Mock description",
                "mock agenda",
                "xyz-300",
                new Date(30,9,2022),
                new Time(14,00,00),
                new Time(15,30,00),
                true,
                3
        );
        EmployeeMeetingDetails anotherMeetingOfEmployee = new EmployeeMeetingDetails(
                8,
                "cancelled",
                "IT description",
                "IT agenda",
                "xyz-14",
                new Date(30,9,2022),
                new Time(16,00,00),
                new Time(17,45,00),
                true,
                1
        );
        employeeMeetingDetails.add(meetingOfEmployee);
        employeeMeetingDetails.add(anotherMeetingOfEmployee);
        return employeeMeetingDetails;
    }

    @Override
    public String isAlive() {
        return null;
    }

}
