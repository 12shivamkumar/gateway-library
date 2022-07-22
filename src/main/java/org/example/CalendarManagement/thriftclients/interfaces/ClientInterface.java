package org.example.CalendarManagement.thriftclients.interfaces;

import org.apache.thrift.TException;
import org.apache.thrift.transport.TTransportException;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("!test")
public interface ClientInterface {

    boolean cancelMeetingForRemovedEmployee(String employeeId) throws TException;

    boolean updateStatusForRemovedEmployee(String employeeId) throws TException;

}
