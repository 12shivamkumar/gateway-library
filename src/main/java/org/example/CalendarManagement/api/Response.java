package org.example.CalendarManagement.api;

import org.example.CalendarManagement.calendarpersistence.model.Employee;

public class Response {
    private String error;
    private Object data;

    public Response(){}

    public Response(String error, Object data) {
        this.error = error;
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public Object getData() {
        return data;
    }

    public void setError(String error) {
        this.error = error;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
