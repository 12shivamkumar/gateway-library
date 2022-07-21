package org.example.CalendarManagement.api.request;

import javax.validation.constraints.NotNull;

public class RemoveEmployeeDataRequest {

    @NotNull(message = "Employee Identity is must")
    private String identity;

    public RemoveEmployeeDataRequest(String identity) {
        this.identity = identity;
    }

    public String getIdentity() {
        return identity;
    }
}
