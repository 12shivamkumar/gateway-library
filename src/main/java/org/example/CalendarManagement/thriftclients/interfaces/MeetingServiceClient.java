package org.example.CalendarManagement.thriftclients.interfaces;

import org.apache.thrift.TException;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("!test")
public interface MeetingServiceClient {

    boolean cancelMeetingForRemovedEmployee(String employeeId);

    boolean updateStatusForRemovedEmployee(String employeeId);

}
