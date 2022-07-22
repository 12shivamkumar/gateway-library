package org.example.CalendarManagement.calendarpersistence.model;


import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;
import java.io.Serializable;

@Embeddable
public class EmployeeMeetingRelationship implements Serializable {

    private String employeeId;

    private String meetingId;

    public EmployeeMeetingRelationship() {

    }
//
//    public EmployeeMeetingRelationship(String employeeId, String meetingId) {
//        this.employeeId = employeeId;
//        this.meetingId = meetingId;
//    }
//
//    public String getEmployeeId() {
//        return employeeId;
//    }
//
//    public String getMeetingId() {
//        return meetingId;
//    }
}
