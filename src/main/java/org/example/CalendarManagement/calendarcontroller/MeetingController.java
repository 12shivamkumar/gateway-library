package org.example.CalendarManagement.calendarcontroller;

import org.example.CalendarManagement.api.Response;
import org.example.CalendarManagement.api.request.AddMeetingDataRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/meeting")
public class MeetingController {

    @PostMapping
    public ResponseEntity<Response> scheduleMeeting(@Valid @RequestBody AddMeetingDataRequest addMeetingDataRequest){

        return null;
    }
}
