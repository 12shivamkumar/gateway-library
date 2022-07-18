package org.example.CalendarManagement.calendarpersistence.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class EmployeeMeeting implements Serializable
{
    @Id
    private String employeeId;

    @Id
    private String meetingId;

    @Column
    private String status;

    @Column
    @NotNull(message = "meeting must have date")
    private LocalDate meetDate;

    @CreationTimestamp
    @Column
    private LocalDateTime createdDateTime;

    public EmployeeMeeting(){}
    public EmployeeMeeting(String employeeId, String meetingId, String status, LocalDate meetDate, LocalDateTime createdDateTime) {
        this.employeeId = employeeId;
        this.meetingId = meetingId;
        this.status = status;
        this.meetDate = meetDate;
        this.createdDateTime = createdDateTime;
    }

    public String getEmployeeId() {
        return employeeId;
    }
    public String getMeetingId() {return meetingId;}
    public String getStatus() {
        return status;
    }
    public LocalDate getMeetDate() {
        return meetDate;
    }
    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    @Override
    public String toString() {
        return "EmployeeMeeting{" +
                "employeeId='" + employeeId + '\'' +
                ", meetingId='" + meetingId + '\'' +
                ", status='" + status + '\'' +
                ", meetDate=" + meetDate +
                ", createdDateTime=" + createdDateTime +
                '}';
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
