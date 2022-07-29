package org.example.CalendarManagement.thriftobjectmappers;

import org.example.CalendarThriftConfiguration.Date;
import org.example.CalendarThriftConfiguration.Time;

import java.time.LocalDate;
import java.time.LocalTime;

public class DateTimeToLocalDateTime {
    LocalDate dateOfMeeting;
    LocalTime startTime;
    LocalTime endTime;

    public DateTimeToLocalDateTime(LocalDate dateOfMeeting, LocalTime startTime, LocalTime endTime) {
        this.dateOfMeeting = dateOfMeeting;
        this.startTime = startTime;
        this.endTime = endTime;
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

    public static DateTimeToLocalDateTime map(Date meetDate, Time meetStart, Time meetEnd){
        LocalDate date = LocalDate.of(meetDate.getYear(),meetDate.getMonth(),meetDate.getDayOfMonth());
        LocalTime sTime = LocalTime.of(meetStart.getHours(),meetStart.getMins(),meetStart.getSeconds());
        LocalTime eTime = LocalTime.of(meetEnd.getHours(),meetEnd.getMins(),meetEnd.getSeconds());
        return new DateTimeToLocalDateTime(date,sTime,eTime);

    }
}
