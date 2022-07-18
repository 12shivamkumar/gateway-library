package org.example.CalendarManagement.RestExceptionHandler;

public class EmployeeAlreadyExistsException extends RuntimeException
{
    public EmployeeAlreadyExistsException() {
    }

    public EmployeeAlreadyExistsException(String message) {
        super(message);
    }

    public EmployeeAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmployeeAlreadyExistsException(Throwable cause) {
        super(cause);
    }
}
