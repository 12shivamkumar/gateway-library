package org.example.CalendarManagement.calendarpersistence.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
public class Meeting

{


    @Id
    @Column(name = "id")
    private String id;

    @Column
    private String description;

    @Column
    @NotNull(message = "meeting must have an agenda")
    private String agenda;

    @Column(name = "owner_id")
    @NotNull(message = "Employee ID cannot be null")
    private String employeeId;

    @Column(name = "date_of_meeting")
    @NotNull(message = "Need to provide meeting date")
    private LocalDate dateOfMeeting;

    @Column(name = "start_time")
    @NotNull(message = "Need to provide meeting start time")
    private LocalTime startTime;

    @Column(name = "end_time")
    @NotNull(message = "Need to provide meeting end time")
    private LocalTime endTime;

    @Column(name = "is_available")
    private boolean isAvailable = true;

    @CreationTimestamp
    @Column(name = "created_time")
    private LocalDateTime createdDateTime;

    @UpdateTimestamp
    @Column(name = "updated_time")
    private LocalDateTime updatedDateTime;

    @Column(name = "room_id")
    @NotNull(message = "meeting must belongs to a room")
    private int roomId;

   /* public Meeting(String id, String description, String agenda, String employeeId, LocalDate dateOfMeeting, LocalTime startTime, LocalTime endTime, int roomId) {
        this.id = id;
        this.description = description;
        this.agenda = agenda;
        this.employeeId = employeeId;
        this.dateOfMeeting = dateOfMeeting;
        this.startTime = startTime;
        this.endTime = endTime;
        this.roomId = roomId;
    }

    public String getId() {
        return id;
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

    public LocalDateTime getUpdatedDateTime() {
        return updatedDateTime;
    }

    public int getRoomId() {
        return roomId;
    }*/
}
