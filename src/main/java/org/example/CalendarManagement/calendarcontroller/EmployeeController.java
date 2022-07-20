package org.example.CalendarManagement.calendarcontroller;


import org.example.CalendarManagement.api.Response;
import org.example.CalendarManagement.api.request.AddEmployeeDataRequest;
import org.example.CalendarManagement.api.validator.ValidateEmployeeEmail;
import org.example.CalendarManagement.api.validator.ValidateOfficeId;
import org.example.CalendarManagement.api.validator.ValidateResponse;
import org.example.CalendarManagement.calendarfacade.EmployeeFacade;
import org.example.CalendarManagement.calendarpersistence.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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
    public ResponseEntity<Response> saveEmployee(@Valid @RequestBody AddEmployeeDataRequest request)
    {

        ValidateResponse validateResponseEmployeeEmailDuplicate = validateEmployeeEmail.checkEmployeeEmailExist(request.getEmail());


        if(!validateResponseEmployeeEmailDuplicate.isValid()) {
            Response addEmployeeResponse = new Response(validateResponseEmployeeEmailDuplicate.getMessage(), null);
            return new ResponseEntity<Response>(addEmployeeResponse, HttpStatus.BAD_REQUEST);
        }
        ValidateResponse validationResponseOfficeIdInDb = validateOfficeId.checkOfficeId(request.getOfficeId());

        if(!validationResponseOfficeIdInDb.isValid()){
            Response addEmployeeResponse = new Response(validationResponseOfficeIdInDb.getMessage(), null);

            return new ResponseEntity<Response>(addEmployeeResponse,HttpStatus.BAD_REQUEST);
        }
        Employee saveEmployee = employeeFacade.saveEmployee(request);
        Response addEmployeeResponse = new Response("",saveEmployee);
        return new ResponseEntity<Response>(addEmployeeResponse,HttpStatus.CREATED);
    }
}
