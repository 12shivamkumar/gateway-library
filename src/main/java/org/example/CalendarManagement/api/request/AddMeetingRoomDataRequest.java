package org.example.CalendarManagement.api.request;

import javax.persistence.Column;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class AddMeetingRoomDataRequest {

    @NotNull(message = "room name cannot be null")
    private String roomName;


    @Min(value = 0,message = "Room must belong to an office")
    private int officeId;

    @NotNull
    private boolean isOpen;

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

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public AddMeetingRoomDataRequest(String roomName, int officeId, boolean isOpen) {
        this.roomName = roomName;
        this.officeId = officeId;
        this.isOpen = isOpen;
    }
}
