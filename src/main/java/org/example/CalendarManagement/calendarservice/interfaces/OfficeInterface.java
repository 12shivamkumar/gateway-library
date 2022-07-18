package org.example.CalendarManagement.calendarservice.interfaces;

import org.example.CalendarManagement.calendarpersistence.model.Office;

import java.util.List;

public interface OfficeInterface {
    List<Office> findAll();
}
