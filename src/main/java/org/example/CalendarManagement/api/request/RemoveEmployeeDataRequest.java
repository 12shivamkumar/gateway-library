package org.example.CalendarManagement.api.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RemoveEmployeeDataRequest {

    @NotNull(message = "employeeId cannot be null")
    @Size(min = 5 , message = "please provide correct employee id")
    private String employeeId;

    public RemoveEmployeeDataRequest(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeId() {
        return employeeId;
    }
}
