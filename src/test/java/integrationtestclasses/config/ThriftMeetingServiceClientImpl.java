package integrationtestclasses.config;

import org.apache.thrift.TException;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;


@Component
@Profile("test")
public class ThriftMeetingServiceClientImpl implements org.example.CalendarManagement.thriftclients.interfaces.ThriftMeetingServiceClient {
    @Override
    public boolean cancelMeetingForRemovedEmployee(String employeeId) throws TException {

        return  true;
    }
    @Override
    public boolean updateStatusForRemovedEmployee(String employeeId) throws TException {
        return  true;
    }

}
