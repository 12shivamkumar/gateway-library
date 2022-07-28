package org.example.CalendarManagement.api.request;

import org.springframework.stereotype.Component;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class AddMeetingDataRequest {
    @NotNull(message = "owner id cannot be nulll")
    private String ownerId;
    @NotNull(message = "agenda must exist")
    private String agenda;

    private String description;
    @NotNull(message = "there must be at least one employee")
    private List<@NotEmpty @Size(min = 1, max=7) String> listOfEmployeeId;

    @NotNull(message = "date of meeting cannot be null")
    private LocalDate dateOfMeeting;
    @NotNull(message = "meeting start time cannot be null")
    private LocalTime startTime;
    @NotNull(message = "meeting end time cannot be null")
    private LocalTime endTime;

    private String roomName;

    public AddMeetingDataRequest() {}

    private AddMeetingDataRequest(Builder builder)
    {
        this.ownerId = builder.ownerId;
        this.agenda = builder.agenda;
        this.description = builder.description;
        this.listOfEmployeeId = builder.listOfEmployeeId;
        this.dateOfMeeting = builder.dateOfMeeting;
        this.startTime = builder.startTime;
        this.endTime = builder.endTime;
        this.roomName = builder.roomName;
    }

    public static class Builder {
        private final String ownerId;
        private final String agenda;
        private final String description;
        private final List<String> listOfEmployeeId;
        private final LocalDate dateOfMeeting;
        private final LocalTime startTime;
        private final LocalTime endTime;
        private final String roomName;

        public Builder(String ownerId, String agenda, String description, List<String> listOfEmployeeId, LocalDate dateOfMeeting, LocalTime startTime, LocalTime endTime, String roomName)
        {
            this.ownerId = ownerId;
            this.agenda = agenda;
            this.description = description;
            this.listOfEmployeeId = listOfEmployeeId;
            this.dateOfMeeting = dateOfMeeting;
            this.startTime = startTime;
            this.endTime = endTime;
            this.roomName = roomName;
        }

        public AddMeetingDataRequest build() {
            return new AddMeetingDataRequest(this);
        }
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
