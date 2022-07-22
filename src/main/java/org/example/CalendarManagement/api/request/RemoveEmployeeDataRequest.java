package org.example.CalendarManagement.api.request;

import javax.validation.constraints.NotNull;

public class RemoveEmployeeDataRequest {

    private String identity;

    public RemoveEmployeeDataRequest(String identity) {
        this.identity = identity;
    }

    public String getIdentity() {
        return identity;
    }
}
