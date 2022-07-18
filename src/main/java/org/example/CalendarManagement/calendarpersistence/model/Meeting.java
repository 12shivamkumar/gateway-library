package org.example.CalendarManagement.calendarpersistence.model;

import org.hibernate.annotations.CreationTimestamp;
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
    private String meetId;

    @Column
    private String description;

    @Column
    @NotNull(message = "meeting must have an agenda")
    private String agenda;

    @Column
    @NotNull(message = "Employee ID cannot be null")
    private String employeeId;

    @Column
    @NotNull(message = "Need to provide meeting date")
    private LocalDate dateOfMeeting;

    @Column
    @NotNull(message = "Need to provide meeting start time")
    private LocalTime startTime;

    @Column()
    @NotNull(message = "Need to provide meeting end time")
    private LocalTime endTime;

    @Column()
    private boolean isAvailable = true;

    @CreationTimestamp
    @Column
    @NotNull()
    private LocalDateTime createdDateTime;

    @Column
    @NotNull(message = "meeting must belongs to a room")
    private int roomId;
}
