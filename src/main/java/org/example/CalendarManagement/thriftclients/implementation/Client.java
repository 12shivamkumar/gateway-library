package org.example.CalendarManagement.thriftclients.implementation;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TSocket;
import org.example.CalendarManagement.thriftclients.interfaces.ClientInterface;
import org.springframework.stereotype.Component;

@Component
public class Client implements ClientInterface
{
    @Override
    public boolean cancelMeetingForRemovedEmployee(String employeeId) throws TException {

        TSocket trans = new TSocket("localhost", 9090);
        TBinaryProtocol protocol = new TBinaryProtocol(trans);

        MeetingSvc.Client client = new MeetingSvc.Client(protocol);

        trans.open();
        boolean thriftResponse = client.cancelMeetingOfRemovedEmployee(employeeId);
        trans.close();

        return thriftResponse;
    }

    @Override
    public boolean updateStatusForRemovedEmployee(String employeeId) throws TException {

        TSocket trans = new TSocket("localhost", 9090);
        TBinaryProtocol protocol = new TBinaryProtocol(trans);
        MeetingSvc.Client client = new MeetingSvc.Client(protocol);

        trans.open();
        boolean thriftResponse = client.updateStatusOfRemovedEmployee(employeeId);
        trans.close();

        return thriftResponse;
    }
}


