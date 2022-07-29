package org.example.CalendarManagement.api.request;

import javax.validation.constraints.NotNull;

public class RemoveEmployeeDataRequest {

    @NotNull(message = "employeeId cannot be null")
    private String employeeId;

    public RemoveEmployeeDataRequest(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeId() {
        return employeeId;
    }
}
