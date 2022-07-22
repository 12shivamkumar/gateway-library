package org.example.CalendarManagement.thriftclients.implementation;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.example.CalendarManagement.thriftclients.interfaces.ClientInterface;
import org.example.CalendarThriftConfiguration.MeetingSvc;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("!test")
public class Client implements ClientInterface
{
    @Override
    public boolean cancelMeetingForRemovedEmployee(String employeeId) throws TException {

        TTransport transport;

        transport = new TSocket("localhost" , 9090);

        transport.open();

        TProtocol protocol = new TBinaryProtocol(transport);

        MeetingSvc.Client client = new MeetingSvc.Client(protocol);

        boolean thriftResponse = client.cancelMeetingOfRemovedEmployee(employeeId);

        transport.close();

        return  thriftResponse;
    }

    @Override
    public boolean updateStatusForRemovedEmployee(String employeeId) throws TException {

        TTransport transport;

        transport = new TSocket("localhost" , 9090);

        transport.open();

        TProtocol protocol = new TBinaryProtocol(transport);

        MeetingSvc.Client client = new MeetingSvc.Client(protocol);

        boolean thriftResponse = client.updateStatusOfRemovedEmployee(employeeId);

        transport.close();

        return  thriftResponse;
    }
}


