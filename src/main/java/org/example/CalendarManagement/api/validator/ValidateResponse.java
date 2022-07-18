package org.example.CalendarManagement.api.validator;

public class ValidateResponse {
    private String message;

    private boolean isValid;

    public ValidateResponse(String message, boolean isValid) {
        this.message = message;
        this.isValid = isValid;
    }

    public String getMessage() {
        return message;
    }

    public boolean isValid() {
        return isValid;
    }
}
