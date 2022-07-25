package integrationtestclasses.config;

import org.example.CalendarManagement.thriftclients.interfaces.MeetingServiceClient;
import org.example.CalendarThriftConfiguration.EmployeeAvailabilityDataRequest;
import org.example.CalendarThriftConfiguration.EmployeeStatusDataRequest;
import org.example.CalendarThriftConfiguration.MeetingDetails;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

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
        return null;
    }

    @Override
    public boolean addMeetingDetails(MeetingDetails meetingDetails) {
        return false;
    }

    @Override
    public boolean addEmployeeMeetingStatus(List<EmployeeStatusDataRequest> employeeStatusDataRequestStatuses) {
        return false;
    }

}
