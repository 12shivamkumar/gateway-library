package org.example.CalendarManagement.calendarfacade;

import org.example.CalendarManagement.api.validator.ValidateOfficeId;
import org.example.CalendarManagement.calendarservice.implementation.MeetingRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/MeetingRoom")
public class MeetingRoomFacade {
    @Autowired
    MeetingRoomService meetingRoomService;
    @Autowired
    ValidateOfficeId validateOfficeId;


}
