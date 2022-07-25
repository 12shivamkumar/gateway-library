package org.example.CalendarManagement.thriftclients.interfaces;

import org.example.CalendarThriftConfiguration.EmployeeAvailabilityDataRequest;
import org.example.CalendarThriftConfiguration.EmployeeStatusDataRequest;
import org.example.CalendarThriftConfiguration.MeetingDetails;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile("!test")
public interface MeetingServiceClient {

    boolean cancelMeetingForRemovedEmployee(String employeeId);

    boolean updateStatusForRemovedEmployee(String employeeId);

    List<String> checkEmployeeAvailability(EmployeeAvailabilityDataRequest employeeAvailabilityDataRequest);

    boolean addMeetingDetails(MeetingDetails meetingDetails);

    boolean addEmployeeMeetingStatus(List<EmployeeStatusDataRequest> employeeStatusDataRequestStatuses);

}
