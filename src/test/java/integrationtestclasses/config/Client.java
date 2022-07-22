package integrationtestclasses.config;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.example.CalendarManagement.thriftclients.interfaces.ClientInterface;
import org.example.CalendarThriftConfiguration.MeetingSvc;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("test")
@Component
public class Client implements ClientInterface {
    @Override
    public boolean cancelMeetingForRemovedEmployee(String employeeId) throws TException {

        return  true;
    }
    @Override
    public boolean updateStatusForRemovedEmployee(String employeeId) throws TException {


        return  true;
    }

}
