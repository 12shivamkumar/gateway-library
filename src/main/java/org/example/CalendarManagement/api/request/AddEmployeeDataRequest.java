package org.example.CalendarManagement.api.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class AddEmployeeDataRequest {

    @NotNull(message = "Employee Id is must")
   // @Pattern(regexp = "^([cap])-([0-9])$")
    private String employeeId;

    @NotNull(message = "Employee Name cannot be Null")
    private String name;


    @NotNull(message = "Employee Email Cannot be Null")
   // @Pattern(regexp = "^[a-zA-Z0-9_\\-]+@[cap]+\\.[com]$")
    private String email;

    @NotNull(message = "Employee must belongs to an office")
    private int officeId;

    public AddEmployeeDataRequest(String employeeId, String name, String email, int officeId) {
        this.employeeId = employeeId;
        this.name = name;
        this.email = email;
        this.officeId = officeId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int getOfficeId() {
        return officeId;
    }
}
