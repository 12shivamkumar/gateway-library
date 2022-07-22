package org.example.CalendarManagement.calendarpersistence.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Entity
public class EmployeeMeeting implements Serializable
{
    @EmbeddedId
    private EmployeeMeetingRelationship id;

    @Column
    private String status;

    @Column(name = "date")
    @NotNull(message = "meeting must have date")
    private LocalDate meetDate;

    @Column(name = "created_timestamp")
    @CreationTimestamp
    private LocalDateTime createdTimeStamp;

    @Column(name = "updated_timestamp")
    @UpdateTimestamp
    private LocalDateTime updatedTimeStamp;
    public EmployeeMeeting(){}

    /*public EmployeeMeeting(String employeeId, String meetingId, String status, LocalDate meetDate) {
        this.employeeId = employeeId;
        this.meetingId = meetingId;
        this.status = status;
        this.meetDate = meetDate;
    }*/

    /*public String getEmployeeId() {
        return employeeId;
    }
    public String getMeetingId() {return meetingId;}
    public String getStatus() {
        return status;
    }
    public LocalDate getMeetDate() {
        return meetDate;
    }


    @Override
    public String toString() {
        return "EmployeeMeeting{" +
                "employeeId='" + employeeId + '\'' +
                ", meetingId='" + meetingId + '\'' +
                ", status='" + status + '\'' +
                ", meetDate=" + meetDate +
                '}';
    }
*/

}
