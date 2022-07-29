package org.example.CalendarManagement.thriftobjectmappers;

import org.example.CalendarThriftConfiguration.Date;
import org.example.CalendarThriftConfiguration.Time;

import java.time.LocalDate;
import java.time.LocalTime;

public class MeetingsOfEmployee {
    public int meetId;

    public String status;
    public String description;
    public String agenda;
    public String ownerId;
    public LocalDate dateOfMeeting;
    public LocalTime startTime;
    public LocalTime endTime;
    public boolean isAvailable;
    public int roomId;

    public MeetingsOfEmployee(int meetId, String status, String description, String agenda, String ownerId, LocalDate dateOfMeeting, LocalTime startTime, LocalTime endTime, boolean isAvailable, int roomId) {
        this.meetId = meetId;
        this.status = status;
        this.description = description;
        this.agenda = agenda;
        this.ownerId = ownerId;
        this.dateOfMeeting = dateOfMeeting;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isAvailable = isAvailable;
        this.roomId = roomId;
    }

    public int getMeetId() {
        return meetId;
    }

    public String getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public String getAgenda() {
        return agenda;
    }

    public String getOwnerId() {
        return ownerId;
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

    /*public boolean isAvailable() {
        return isAvailable;
    }*/

    public int getRoomId() {
        return roomId;
    }
}
