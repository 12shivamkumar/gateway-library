package org.example.CalendarManagement.RestExceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class RestExceptionHandler
{
    @ExceptionHandler({MethodArgumentNotValidException.class})
    protected ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request){
        List<String> errors = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach((error)->{
            errors.add(error.getDefaultMessage());
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({EmployeeAlreadyExistsException.class})
    protected ResponseEntity<Object> handleNotFound(Exception ex, WebRequest request)
    {
        return new ResponseEntity<>("Employee already exists", HttpStatus.BAD_REQUEST);
    }
}
