package org.example.CalendarManagement.thriftclients.implementation;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.THttpClient;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.example.CalendarManagement.calendarfacade.EmployeeFacade;
import org.example.CalendarManagement.thriftclients.interfaces.MeetingServiceClient;
import org.example.CalendarThriftConfiguration.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile("!test")
public class MeetingServiceClientImpl implements MeetingServiceClient
{
    Logger logger = LoggerFactory.getLogger(EmployeeFacade.class);
    @Override
    public boolean cancelMeetingForRemovedEmployee(String employeeId){
        try( TTransport transport = new TSocket("localhost",9090)) {
            transport.open();

            TProtocol protocol = new TBinaryProtocol(transport);

            MeetingSvc.Client client = new MeetingSvc.Client(protocol);

            boolean thriftResponse = client.cancelMeetingOfRemovedEmployee(employeeId);

            transport.close();

            return  thriftResponse;
        }catch (TException exception)
        {
            logger.error(exception.getMessage());
            throw new RuntimeException(exception.getMessage());
        }
    }

    @Override
    public boolean updateStatusForRemovedEmployee(String employeeId){
        try( TTransport transport = new TSocket("localhost",9090)) {
            transport.open();

            TProtocol protocol = new TBinaryProtocol(transport);

            MeetingSvc.Client client = new MeetingSvc.Client(protocol);

            boolean thriftResponse = client.updateStatusOfRemovedEmployee(employeeId);

            transport.close();

            return thriftResponse;
        }catch (TException exception)
        {
            logger.error(exception.getMessage());
            throw  new RuntimeException(exception.getMessage());
        }
    }

    @Override
    public List<String> checkEmployeeAvailability(EmployeeAvailabilityDataRequest employeeAvailabilityDataRequest) {
        try( TTransport transport = new TSocket("localhost",9090)) {
            transport.open();

            TProtocol protocol = new TBinaryProtocol(transport);

            MeetingSvc.Client client = new MeetingSvc.Client(protocol);
            List<String> listOfEmployeeNotAvailable = client.checkEmployeeAvailability(employeeAvailabilityDataRequest);
            transport.close();

            return listOfEmployeeNotAvailable;
        }catch (TException exception)
        {
            logger.error(exception.getMessage());
            throw  new RuntimeException(exception.getMessage());
        }
    }

    @Override
    public int addMeetingDetails(MeetingDetails meetingDetails) {
        try( TTransport transport = new TSocket("localhost",9090)) {
            transport.open();

            TProtocol protocol = new TBinaryProtocol(transport);

            MeetingSvc.Client client = new MeetingSvc.Client(protocol);

            int thriftResponse = client.addMeetingDetails(meetingDetails);

            transport.close();

            return thriftResponse;
        }catch (TException exception)
        {
            logger.error(exception.getMessage());
            throw  new RuntimeException(exception.getMessage());
        }
    }

    @Override
    public int findFreeMeetingRoom(FindFreeMeetingRoomDataRequest findFreeMeetingRoomDataRequest) {
        try( TTransport transport = new TSocket("localhost",9090)) {
            transport.open();

            TProtocol protocol = new TBinaryProtocol(transport);

            MeetingSvc.Client client = new MeetingSvc.Client(protocol);

            int thriftResponse = client.findFreeMeetingRoom(findFreeMeetingRoomDataRequest);

            transport.close();

            return thriftResponse;
        }catch (TException exception)
        {
            logger.error(exception.getMessage());
            throw  new RuntimeException(exception.getMessage());
        }
    }

    @Override
    public boolean meetingRoomAvailable(MeetingRoomAvailableDataRequest meetingRoomAvailableDataRequest) {
        try( TTransport transport = new TSocket("localhost",9090)) {
            transport.open();

            TProtocol protocol = new TBinaryProtocol(transport);

            MeetingSvc.Client client = new MeetingSvc.Client(protocol);

            boolean thriftResponse = client.meetingRoomAvailable(meetingRoomAvailableDataRequest);

            transport.close();

            return thriftResponse;
        }catch (TException exception)
        {
            logger.error(exception.getMessage());
            throw  new RuntimeException(exception.getMessage());
        }
    }

    @Override
    public List<EmployeeMeetingDetails> getEmployeeMeetingDetails(String employeeId) {
        try( TTransport transport = new TSocket("localhost",9090)) {
            transport.open();

            TProtocol protocol = new TBinaryProtocol(transport);

            MeetingSvc.Client client = new MeetingSvc.Client(protocol);

            List<EmployeeMeetingDetails> thriftResponse = client.getEmployeeMeetingDetails(employeeId);

            transport.close();

            return thriftResponse;
        }catch (TException exception)
        {
            logger.error(exception.getMessage());
            throw  new RuntimeException(exception.getMessage());
        }
    }

    @Override
    public String isAlive() {
        try( TTransport transport = new TSocket("localhost",9090)) {
            transport.open();

            TProtocol protocol = new TBinaryProtocol(transport);

            MeetingSvc.Client client = new MeetingSvc.Client(protocol);

            String thriftResponse = client.isAlive();

            transport.close();

            return thriftResponse;
        }catch (TException exception)
        {
            logger.error(exception.getMessage());
            throw  new RuntimeException(exception.getMessage());
        }
    }

}


