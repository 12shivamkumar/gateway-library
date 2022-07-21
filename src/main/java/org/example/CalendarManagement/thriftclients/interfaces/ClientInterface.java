package org.example.CalendarManagement.thriftclients.interfaces;

import org.apache.thrift.TException;
import org.apache.thrift.transport.TTransportException;

public interface ClientInterface {

    boolean cancelMeetingForRemovedEmployee(String employeeId) throws TException;

    boolean updateStatusForRemovedEmployee(String employeeId) throws TException;

}
