package org.example.CalendarManagement.calendarservice.interfaces;

import org.example.CalendarManagement.calendarpersistence.model.Employee;

import java.util.List;

public interface EmployeeInterface {
    Employee addEmployee(Employee employee);
    Employee removeEmployeeById(String id);

    Employee removeEmployeeByEmail(String email);

   /* int getOffice(List<String> empID);
    List<String> findEmployeeByID(List<String> empID);*/
}
