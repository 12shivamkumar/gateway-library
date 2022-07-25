package org.example.CalendarManagement.api.request;

import org.springframework.stereotype.Component;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class AddMeetingDataRequest {
    @NotNull(message = "owner id cannot be nulll")
    private String ownerId;
    @NotNull(message = "agenda must exist")
    private String agenda;

    private String description;
    @NotNull(message = "There must be atleast one employee")
    private List<String> listOfEmployeeId;

    @NotNull(message = "date of meeting cannot be null")
    private LocalDate dateOfMeeting;
    @NotNull(message = "meeting start time cannot be null")
    private LocalTime startTime;
    @NotNull(message = "meeting end time cannot be null")
    private LocalTime endTime;

    private String roomName;

    public AddMeetingDataRequest(String ownerId, String agenda, String description, List<String> listOfEmployeeId, LocalDate dateOfMeeting, LocalTime startTime, LocalTime endTime, String roomName) {
        this.ownerId = ownerId;
        this.agenda = agenda;
        this.description = description;
        this.listOfEmployeeId = listOfEmployeeId;
        this.dateOfMeeting = dateOfMeeting;
        this.startTime = startTime;
        this.endTime = endTime;
        this.roomName = roomName;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public String getAgenda() {
        return agenda;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getListOfEmployeeId() {
        return listOfEmployeeId;
    }

    public LocalDate getDateOfMeeting() {
        return dateOfMeeting;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public String getRoomName() {
        return roomName;
    }
}
