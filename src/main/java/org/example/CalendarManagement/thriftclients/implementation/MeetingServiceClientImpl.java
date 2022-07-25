package org.example.CalendarManagement.thriftclients.implementation;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.example.CalendarManagement.thriftclients.interfaces.MeetingServiceClient;
import org.example.CalendarThriftConfiguration.EmployeeAvailabilityDataRequest;
import org.example.CalendarThriftConfiguration.EmployeeStatusDataRequest;
import org.example.CalendarThriftConfiguration.MeetingDetails;
import org.example.CalendarThriftConfiguration.MeetingSvc;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile("!test")
public class MeetingServiceClientImpl implements MeetingServiceClient
{
    @Override
    public boolean cancelMeetingForRemovedEmployee(String employeeId){
        try( TTransport transport = new TSocket("localhost" , 9090))
        {
            transport.open();

            TProtocol protocol = new TBinaryProtocol(transport);

            MeetingSvc.Client client = new MeetingSvc.Client(protocol);

            boolean thriftResponse = client.cancelMeetingOfRemovedEmployee(employeeId);

            transport.close();

            return  thriftResponse;
        }catch (TException exception)
        {
            throw new RuntimeException(exception.getMessage());
        }
    }

    @Override
    public boolean updateStatusForRemovedEmployee(String employeeId){
        try( TTransport transport = new TSocket("localhost" , 9090)) {
            transport.open();

            TProtocol protocol = new TBinaryProtocol(transport);

            MeetingSvc.Client client = new MeetingSvc.Client(protocol);

            boolean thriftResponse = client.updateStatusOfRemovedEmployee(employeeId);

            transport.close();

            return thriftResponse;
        }catch (TException exception)
        {
            throw  new RuntimeException(exception.getMessage());
        }
    }

    @Override
    public List<String> checkEmployeeAvailability(EmployeeAvailabilityDataRequest employeeAvailabilityDataRequest) {
        try( TTransport transport = new TSocket("localhost" , 9090)) {
            transport.open();

            TProtocol protocol = new TBinaryProtocol(transport);

            MeetingSvc.Client client = new MeetingSvc.Client(protocol);

            List<String> listOfEmployeeNotAvailable = client.checkEmployeeAvailability(employeeAvailabilityDataRequest);

            transport.close();

            return listOfEmployeeNotAvailable;
        }catch (TException exception)
        {
            throw  new RuntimeException(exception.getMessage());
        }
    }

    @Override
    public boolean addMeetingDetails(MeetingDetails meetingDetails) {
        try( TTransport transport = new TSocket("localhost" , 9090)) {
            transport.open();

            TProtocol protocol = new TBinaryProtocol(transport);

            MeetingSvc.Client client = new MeetingSvc.Client(protocol);

            Boolean thriftResponse = client.addMeetingDetails(meetingDetails);

            transport.close();

            return thriftResponse;
        }catch (TException exception)
        {
            throw  new RuntimeException(exception.getMessage());
        }
    }

    @Override
    public boolean addEmployeeMeetingStatus(List<EmployeeStatusDataRequest> employeeStatusDataRequest) {
        try( TTransport transport = new TSocket("localhost" , 9090)) {
            transport.open();

            TProtocol protocol = new TBinaryProtocol(transport);

            MeetingSvc.Client client = new MeetingSvc.Client(protocol);

            Boolean thriftResponse = client.addEmployeeMeetingStatus(employeeStatusDataRequest);

            transport.close();

            return thriftResponse;
        }catch (TException exception)
        {
            throw  new RuntimeException(exception.getMessage());
        }
    }
}


