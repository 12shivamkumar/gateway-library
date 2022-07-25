package org.example.CalendarManagement.api.request;

public class RemoveEmployeeDataRequest {

    private String employeeId;

    public RemoveEmployeeDataRequest(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeId() {
        return employeeId;
    }
}
