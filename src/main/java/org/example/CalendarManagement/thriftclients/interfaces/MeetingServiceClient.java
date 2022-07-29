package org.example.CalendarManagement.thriftclients.interfaces;

import org.example.CalendarThriftConfiguration.*;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
public interface MeetingServiceClient {

    boolean cancelMeetingForRemovedEmployee(String employeeId);

    boolean updateStatusForRemovedEmployee(String employeeId);

    List<String> checkEmployeeAvailability(EmployeeAvailabilityDataRequest employeeAvailabilityDataRequest);

    int addMeetingDetails(MeetingDetails meetingDetails);

    int findFreeMeetingRoom(FindFreeMeetingRoomDataRequest findFreeMeetingRoomDataRequest);

    boolean meetingRoomAvailable(MeetingRoomAvailableDataRequest meetingRoomAvailableDataRequest);

    List<EmployeeMeetingDetails> getEmployeeMeetingDetails(String employeeId);

    String isAlive();

}
