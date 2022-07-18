package org.example.CalendarManagement.calendarservice.interfaces;

import java.time.LocalDate;
import java.util.Map;

public interface EmployeeMeetingInterface {
    Map<String, String> getMeetingsByDates(String empID, LocalDate startDate, LocalDate endDate); //MeetingID,Status
    Map<String, String > getMeetingsForToday(String empID);  //MeetingID,Status
    Map<String ,String> getEmpIdAndStatus(String meetingID);    //EmpId, Status
    Void setEmployeeStatus(String empID, String meetingID,String Status);
}
