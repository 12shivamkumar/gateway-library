package integrationtestclasses.config;

import org.apache.thrift.TException;
import org.example.CalendarManagement.thriftclients.interfaces.MeetingServiceClient;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;


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

}
