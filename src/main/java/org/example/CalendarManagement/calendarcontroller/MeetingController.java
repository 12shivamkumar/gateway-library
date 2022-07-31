package org.example.CalendarManagement.calendarcontroller;

import org.example.CalendarManagement.api.Response;
import org.example.CalendarManagement.api.request.AddMeetingDataRequest;
import org.example.CalendarManagement.api.validator.*;
import org.example.CalendarManagement.calendarfacade.MeetingFacade;
import org.example.CalendarManagement.calendarpersistence.repository.EmployeeRepository;
import org.example.CalendarManagement.thriftobjectmappers.AddMeetingToEmployeeAvailabilityMapper;
import org.example.CalendarThriftConfiguration.EmployeeAvailabilityDataRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@Validated
@RequestMapping("/meeting")
public class MeetingController {
    @Autowired
    ValidateCompanyPolicies validateCompanyPolicies;

    @Autowired
    ValidateEmployeeId validateEmployeeId;

    @Autowired
    ValidateMeetingDateTime validateMeetingDateTime;

    @Autowired
    ValidateListOfEmployees validateListOfEmployees;

    @Autowired
    ValidateMeetingRoomExistsInDb validateMeetingRoomExistsInDb;

    @Autowired
    ValidateMeetingRoomAvailability validateMeetingRoomAvailability;

    @Autowired
    ValidateEmployeeAvailability validateEmployeeAvailability;

    @Autowired
    MeetingFacade meetingFacade;

    @Autowired
    EmployeeRepository employeeRepository;

    @PostMapping
    public ResponseEntity<Response> scheduleMeeting(@Valid @RequestBody AddMeetingDataRequest addMeetingDataRequest){

        System.out.println(addMeetingDataRequest.toString());

      ValidateResponse validateResponseNoOfEmployeeInMeeting = validateCompanyPolicies.noOfEmployeeInMeeting(addMeetingDataRequest.getListOfEmployeeId());

      if(!validateResponseNoOfEmployeeInMeeting.isValid())
      {
          Response scheduleMeetingResponse = new Response( validateResponseNoOfEmployeeInMeeting.getMessage(), null);
          return  new ResponseEntity<Response>(scheduleMeetingResponse , HttpStatus.BAD_REQUEST);
      }

      ValidateResponse validateResponseMeetingDurationGreaterThanThirtyMinutes = validateCompanyPolicies.meetingDurationGreaterThanThirtyMinutes(addMeetingDataRequest.getStartTime(),addMeetingDataRequest.getEndTime());
      if(!validateResponseMeetingDurationGreaterThanThirtyMinutes.isValid())
      {
          Response scheduleMeetingResponse = new Response( validateResponseMeetingDurationGreaterThanThirtyMinutes.getMessage(), null);
          return  new ResponseEntity<Response>(scheduleMeetingResponse , HttpStatus.BAD_REQUEST);
      }

      ValidateResponse validateResponseMeetingBetweenOfficeHours =  validateCompanyPolicies.meetingBetweenOfficeHours(addMeetingDataRequest.getStartTime(),addMeetingDataRequest.getEndTime());
      if(!validateResponseMeetingBetweenOfficeHours.isValid())
      {
          Response scheduleMeetingResponse = new Response( validateResponseMeetingBetweenOfficeHours.getMessage(), null);
          return  new ResponseEntity<Response>(scheduleMeetingResponse , HttpStatus.BAD_REQUEST);
      }

      ValidateResponse validateResponseValidateMeetingDateTime =  validateMeetingDateTime.checkMeetingDateTime(addMeetingDataRequest.getDateOfMeeting(), addMeetingDataRequest.getStartTime());
      if(!validateResponseValidateMeetingDateTime.isValid())
      {
          Response scheduleMeetingResponse = new Response(validateResponseValidateMeetingDateTime.getMessage() , false);
          return new ResponseEntity<>(scheduleMeetingResponse , HttpStatus.BAD_REQUEST);
      }

      ValidateResponse validateResponseValidateOwnerId = validateEmployeeId.checkEmployeeId(addMeetingDataRequest.getOwnerId());
      if(!validateResponseValidateOwnerId.isValid())
      {
          Response scheduleMeetingResponse = new Response( validateResponseValidateOwnerId.getMessage() , false);
          return new ResponseEntity<>(scheduleMeetingResponse , HttpStatus.BAD_REQUEST);
      }

      ValidateResponse validateResponseValidateListOfEmployee  =validateListOfEmployees.checkIfEmployeeExistInSameOffice(addMeetingDataRequest.getListOfEmployeeId(), employeeRepository.findOfficeIdById(addMeetingDataRequest.getOwnerId()), addMeetingDataRequest.getOwnerId());
      if(!validateResponseValidateListOfEmployee.isValid())
      {
          Response scheduleMeetingResponse = new Response( validateResponseValidateListOfEmployee.getMessage(), false);
          return new ResponseEntity<>(scheduleMeetingResponse , HttpStatus.BAD_REQUEST);
      }

      ValidateResponse validateResponseValidateMeetingRoomNameExistsInDb = validateMeetingRoomExistsInDb.checkMeetingRoomInDb(addMeetingDataRequest.getRoomName());
      if (!validateResponseValidateMeetingRoomNameExistsInDb.isValid()) {
          Response scheduleMeetingResponse = new Response(validateResponseValidateMeetingRoomNameExistsInDb.getMessage(), false);
          return new ResponseEntity<>(scheduleMeetingResponse, HttpStatus.BAD_REQUEST);
      }

      ValidateResponse validateResponseValidateMeetingRoomAvailability = validateMeetingRoomAvailability.checkMeetingRoomAvailability(addMeetingDataRequest);

      if (!validateResponseValidateMeetingRoomAvailability.isValid()) {
          Response scheduleMeetingResponse = new Response(validateResponseValidateMeetingRoomAvailability.getMessage(), false);
          return new ResponseEntity<>(scheduleMeetingResponse, HttpStatus.BAD_REQUEST);
      }

      EmployeeAvailabilityDataRequest employeeAvailabilityDataRequest = AddMeetingToEmployeeAvailabilityMapper.map(addMeetingDataRequest);
      ValidateResponse validateResponseValidateEmployeeAvailability = validateEmployeeAvailability.checkEmployeeAvailability(employeeAvailabilityDataRequest);
      if(!validateResponseValidateEmployeeAvailability.isValid())
      {
          Response scheduleMeetingResponse = new Response(validateResponseValidateEmployeeAvailability.getMessage(), false);
          return new ResponseEntity<>(scheduleMeetingResponse, HttpStatus.BAD_REQUEST);
      }

      int roomId = Integer.parseInt(validateResponseValidateMeetingRoomAvailability.getMessage());
      Response validateResponseScheduleMeeting = meetingFacade.scheduleMeeting(addMeetingDataRequest , roomId);

      Response scheduleMeetingResponse = new Response(null , validateResponseScheduleMeeting.getData());
      return new ResponseEntity<>(scheduleMeetingResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getMeetingsOfEmployee(@NotNull(message = "employeeId cannot be null")@PathVariable(name = "id") String employeeId){
        ValidateResponse validEmployeeId = validateEmployeeId.checkEmployeeId(employeeId);
        if(!validEmployeeId.isValid()){
            Response invalidRequestResponse = new Response(validEmployeeId.getMessage(),null);
            return new ResponseEntity<>(invalidRequestResponse,HttpStatus.BAD_REQUEST);
        }
        Response getMeetings = meetingFacade.getMeetings(employeeId);
        return new ResponseEntity<Response>(getMeetings,HttpStatus.OK);
    }
}