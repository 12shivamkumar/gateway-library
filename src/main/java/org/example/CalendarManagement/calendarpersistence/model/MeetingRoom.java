package org.example.CalendarManagement.calendarpersistence.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class MeetingRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int roomId;

    @Column(unique = true)
    @NotNull(message = "room name cannot be null")
    private String roomName;

    @Column
    @Min(value = 0,message = "Room must belong to an office")
    private int officeId;

    @Column
    @NotNull
    private boolean isOpen;

    @CreationTimestamp
    @Column
    private LocalDateTime createdDateTime;

    public MeetingRoom(){}

    public MeetingRoom( String roomName, int officeId,boolean isOpen) {
        this.roomName = roomName;
        this.officeId = officeId;
        this.isOpen = isOpen;

    }

    public int getRoomId() {
        return roomId;
    }
    public String getRoomName() {return roomName;}
    public int getOfficeId() {return officeId;}
    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }
    @Override
    public String toString() {
        return "MeetingRoom{" +
                "roomId=" + roomId +
                ", roomName='" + roomName + '\'' +
                ", officeId=" + officeId +
                ", createdDateTime=" + createdDateTime +
                '}';
    }
}
