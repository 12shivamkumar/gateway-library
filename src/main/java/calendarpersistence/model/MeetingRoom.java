package calendarpersistence.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
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
    @NotNull(message = "Room must belong to an office")
    private int officeId;

    @CreationTimestamp
    @Column
    private LocalDateTime createdDateTime;

    public MeetingRoom(int roomId, String roomName, int officeId, LocalDateTime createdDateTime) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.officeId = officeId;
        this.createdDateTime = createdDateTime;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public int getOfficeId() {
        return officeId;
    }

    public void setOfficeId(int officeId) {
        this.officeId = officeId;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(LocalDateTime createdDateTime) {
        this.createdDateTime = createdDateTime;
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
