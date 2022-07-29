package org.example.CalendarManagement.api.request;

import javax.persistence.Column;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class AddEmployeeDataRequest {

    @NotNull(message = "Employee Id is must")
   // @Pattern(regexp = "^([cap])-([0-9])$")
    @Size(min = 5 , message = "please provide correct employee id")
    private String employeeId;

    @NotNull(message = "Employee Name cannot be Null")
    @Size(min = 2 , message = "please provide correct name")
    private String name;


    @NotNull(message = "Employee Email Cannot be Null")
    @Size(min = 10 , message = "please provide correct email")
   // @Pattern(regexp = "^[a-zA-Z0-9_\\-]+@[cap]+\\.[com]$")
    private String email;

    @NotNull(message = "Employee must belongs to an office")
    @Min(value = 1 , message = "Employee must belong to any office")
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
