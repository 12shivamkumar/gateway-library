package org.example.CalendarManagement.calendarcontroller;


import org.example.CalendarManagement.api.request.AddEmployeeDataRequest;
import org.example.CalendarManagement.api.validator.ValidateEmployeeEmail;
import org.example.CalendarManagement.api.validator.ValidateOfficeId;
import org.example.CalendarManagement.api.validator.ValidateResponse;
import org.example.CalendarManagement.calendarfacade.EmployeeFacade;
import org.example.CalendarManagement.calendarpersistence.model.Employee;
import org.example.CalendarManagement.calendarservice.implementation.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    ValidateOfficeId validateOfficeId;
    @Autowired
    ValidateEmployeeEmail validateEmployeeEmail;

    @Autowired
    EmployeeFacade employeeFacade;

    @PostMapping
    public ResponseEntity<Object> saveEmployee(@Valid @RequestBody AddEmployeeDataRequest request)
    {

        ValidateResponse validationResponseOfficeIdInDb = validateOfficeId.checkOfficeId(request.getOfficeId());
        ValidateResponse validateResponseEmployeeEmailDuplicate = validateEmployeeEmail.checkEmployeeEmailExist(request.getEmail());


        if(!validateResponseEmployeeEmailDuplicate.isValid())
            return new ResponseEntity<>(validateResponseEmployeeEmailDuplicate.getMessage(),HttpStatus.BAD_REQUEST);

        if(!validationResponseOfficeIdInDb.isValid())
            return new ResponseEntity<>(validationResponseOfficeIdInDb.getMessage(),HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>( employeeFacade.saveEmployee(request) ,HttpStatus.CREATED);
    }
}
