package org.example.CalendarManagement.calendarpersistence.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class MeetingRoom {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int roomId;

    @Column(unique = true,name = "room_name")
    @NotNull(message = "room name cannot be null")
    private String roomName;

    @Column(name = "office_id")
    @Min(value = 0,message = "Room must belong to an office")
    private int officeId;

    @Column(name = "is_open")
    @NotNull
    private boolean isOpen;

    @CreationTimestamp
    @Column(name = "created_date")
    private LocalDateTime createdDateTime;

    public MeetingRoom(){}

    public boolean isOpen() {
        return isOpen;
    }

    public MeetingRoom(String roomName, int officeId, boolean isOpen) {
        this.roomName = roomName;
        this.officeId = officeId;
        this.isOpen = isOpen;
    }
    public int getRoomId() {
        return roomId;
    }
}
