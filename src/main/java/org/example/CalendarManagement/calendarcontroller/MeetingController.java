package org.example.CalendarManagement.calendarcontroller;

import org.example.CalendarManagement.api.Response;
import org.example.CalendarManagement.api.request.AddMeetingDataRequest;
import org.example.CalendarManagement.api.validator.*;
import org.example.CalendarManagement.calendarfacade.MeetingFacade;
import org.example.CalendarManagement.calendarpersistence.repository.EmployeeRepository;
import org.example.CalendarManagement.thriftclients.interfaces.MeetingServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/meeting")
public class MeetingController {
    @Autowired
    ValidateCompanyPolicies validateCompanyPolicies;

    @Autowired
    ValidateEmployeeId validateOwnerId;

    @Autowired
    ValidateMeetingDateTime validateMeetingDateTime;

    @Autowired
    ValidateListOfEmployees validateListOfEmployees;

    @Autowired
    ValidateMeetingRoom validateMeetingRoom;

    @Autowired
    MeetingFacade meetingFacade;

    @Autowired
    EmployeeRepository employeeRepository;

    @PostMapping
    public ResponseEntity<Response> scheduleMeeting(@Valid @RequestBody AddMeetingDataRequest addMeetingDataRequest){

        ValidateResponse validateResponseNoOfEmployeeInMeeting = validateCompanyPolicies.noOfEmployeeInMeeting(addMeetingDataRequest.getListOfEmployeeId());
      if(!validateResponseNoOfEmployeeInMeeting.isValid())
      {
          Response scheduleMeetingResponse = new Response( "Employees more than six are present So meeting is not Productive", null);
          return  new ResponseEntity<Response>(scheduleMeetingResponse , HttpStatus.BAD_REQUEST);
      }

      ValidateResponse validateResponseMeetingDurationGreaterThanThirtyMinutes = validateCompanyPolicies.meetingDurationGreaterThanThirtyMinutes(addMeetingDataRequest.getStartTime(),addMeetingDataRequest.getEndTime());
      if(!validateResponseMeetingDurationGreaterThanThirtyMinutes.isValid())
      {
          Response scheduleMeetingResponse = new Response( "meeting won't be productive because meeting duration is less than 30 minutes", null);
          return  new ResponseEntity<Response>(scheduleMeetingResponse , HttpStatus.BAD_REQUEST);
      }

      ValidateResponse validateResponseMeetingBetweenOfficeHours =  validateCompanyPolicies.meetingBetweenOfficeHours(addMeetingDataRequest.getStartTime(),addMeetingDataRequest.getEndTime());
      if(!validateResponseMeetingBetweenOfficeHours.isValid())
      {
          Response scheduleMeetingResponse = new Response( "meeting won't be productive because meeting is outside office hours", null);
          return  new ResponseEntity<Response>(scheduleMeetingResponse , HttpStatus.BAD_REQUEST);
      }

      ValidateResponse validateResponseValidateMeetingDateTime =  validateMeetingDateTime.checkMeetingDateTime(addMeetingDataRequest.getDateOfMeeting(), addMeetingDataRequest.getStartTime());
      if(!validateResponseValidateMeetingDateTime.isValid())
      {
          Response scheduleMeetingResponse = new Response("Invalid input for scheduling a meeting" , false);
          return new ResponseEntity<>(scheduleMeetingResponse , HttpStatus.BAD_REQUEST);
      }

      ValidateResponse validateResponseValidateOwnerId = validateOwnerId.checkEmployeeId(addMeetingDataRequest.getOwnerId());
      if(!validateResponseValidateOwnerId.isValid())
      {
          Response scheduleMeetingResponse = new Response( "Owner does not exists" , false);
          return new ResponseEntity<>(scheduleMeetingResponse , HttpStatus.BAD_REQUEST);
      }

      ValidateResponse validateResponseValidateListOfEmployee  =validateListOfEmployees.checkIfEmployeeExistInSameOffice(addMeetingDataRequest.getListOfEmployeeId(), employeeRepository.findOfficeIdById(addMeetingDataRequest.getOwnerId()));
      if(!validateResponseValidateListOfEmployee.isValid())
      {
          Response scheduleMeetingResponse = new Response( "All employees does not belongs to same office" , false);
          return new ResponseEntity<>(scheduleMeetingResponse , HttpStatus.BAD_REQUEST);
      }

      ValidateResponse validateResponseValidateMeetingRoomName = validateMeetingRoom.checkMeetingRoomInDb(addMeetingDataRequest.getRoomName());
      if(!validateResponseValidateMeetingRoomName.isValid())
      {
          Response scheduleMeetingResponse = new Response( "meeting room does not exist or is closed" , false);
          return new ResponseEntity<>(scheduleMeetingResponse , HttpStatus.BAD_REQUEST);
      }

      Response validateResponseScheduleMeeting = meetingFacade.scheduleMeeting(addMeetingDataRequest);

      String meetingId = (String) validateResponseScheduleMeeting.getData();

      if(meetingId.equals(""))
      {
          Response scheduleMeetingResponse = new Response( "meeting cannot be scheduled" , false);
          return new ResponseEntity<>(scheduleMeetingResponse , HttpStatus.BAD_REQUEST);
      }

      Response scheduleMeetingResponse = new Response(null , meetingId);
      return new ResponseEntity<>(scheduleMeetingResponse, HttpStatus.CREATED);
    }
}

//1. company policy - time b/w 10 to 6 , no. of employees < 6 ,duration greater than 30
//2. date and time greater than now
//3. owner id in Db
//4. all employees in same office
//5. meeting room in db if available
