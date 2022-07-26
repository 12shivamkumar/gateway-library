package org.example.CalendarManagement.calendarcontroller;

import org.example.CalendarManagement.api.Response;
import org.example.CalendarManagement.api.request.AddMeetingDataRequest;
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
    MeetingServiceClient meetingServiceClient;

    @PostMapping
    public ResponseEntity<Response> scheduleMeeting(@Valid @RequestBody AddMeetingDataRequest addMeetingDataRequest){

        return null;
    }
}
