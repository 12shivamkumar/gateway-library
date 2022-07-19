package org.example.CalendarManagement.calendarpersistence.model;

import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "Meeting")
public class Meeting
{
    @Id
    @Column(name = "MeetId")
    private String meetId;

    @Column(name = "Description")
    private String description;

    @Column(name = "Agenda")
    @NotNull(message = "meeting must have an agenda")
    private String agenda;

    @Column(name = "OwnerId")
    @NotNull(message = "Employee ID cannot be null")
    private String employeeId;

    @Column(name = "DateOfMeeting")
    @NotNull(message = "Need to provide meeting date")
    private LocalDate dateOfMeeting;

    @Column(name = "StartTime")
    @NotNull(message = "Need to provide meeting start time")
    private LocalTime startTime;

    @Column(name = "EndTime")
    @NotNull(message = "Need to provide meeting end time")
    private LocalTime endTime;

    @Column(name = "IsAvailable")
    private boolean isAvailable = true;

    @CreationTimestamp
    @Column(name = "Log")
    private LocalDateTime createdDateTime;

    @Column(name = "RoomId")
    @NotNull(message = "meeting must belongs to a room")
    private int roomId;

    public String getMeetId() {
        return meetId;
    }

    public String getDescription() {
        return description;
    }

    public String getAgenda() {
        return agenda;
    }

    public String getEmployeeId() {
        return employeeId;
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

    public boolean isAvailable() {
        return isAvailable;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public int getRoomId() {
        return roomId;
    }

    @Override
    public String toString() {
        return "Meeting{" +
                "meetId='" + meetId + '\'' +
                ", description='" + description + '\'' +
                ", agenda='" + agenda + '\'' +
                ", employeeId='" + employeeId + '\'' +
                ", dateOfMeeting=" + dateOfMeeting +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", isAvailable=" + isAvailable +
                ", createdDateTime=" + createdDateTime +
                ", roomId=" + roomId +
                '}';
    }
}
