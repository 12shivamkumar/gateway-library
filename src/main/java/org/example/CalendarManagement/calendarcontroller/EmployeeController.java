package org.example.CalendarManagement.calendarcontroller;


import org.apache.thrift.TException;
import org.example.CalendarManagement.api.Response;
import org.example.CalendarManagement.api.request.AddEmployeeDataRequest;
//import org.example.CalendarManagement.api.request.RemoveEmployeeDataRequest;
//import org.example.CalendarManagement.api.request.RemoveEmployeeDataRequest;
import org.example.CalendarManagement.api.request.RemoveEmployeeDataRequest;
import org.example.CalendarManagement.api.validator.ValidateEmployeeEmail;
//import org.example.CalendarManagement.api.validator.ValidateEmployeeIdentity;
import org.example.CalendarManagement.api.validator.ValidateEmployeeIdentity;
import org.example.CalendarManagement.api.validator.ValidateOfficeId;
import org.example.CalendarManagement.api.validator.ValidateResponse;
import org.example.CalendarManagement.calendarfacade.EmployeeFacade;
import org.example.CalendarManagement.calendarpersistence.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    ValidateOfficeId validateOfficeId;
    @Autowired
    ValidateEmployeeEmail validateEmployeeEmail;

    @Autowired
    ValidateEmployeeIdentity validateEmployeeIdentity;

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
    @DeleteMapping("/{id}")
    public ResponseEntity<Response> removeEmployee(@PathVariable String identity,@RequestParam String findBy)  {

        ValidateResponse validateResponseForEmployeeIdentity= null;
        RemoveEmployeeDataRequest removeEmployeeDataRequest = new RemoveEmployeeDataRequest(identity);
        if(findBy.equals("id")){
            validateResponseForEmployeeIdentity = validateEmployeeIdentity.checkEmployeeId(identity);

        }
        else if(findBy.equals("email")){
            validateResponseForEmployeeIdentity = validateEmployeeIdentity.checkEmployeeEmail(identity);
        }
        else{
            validateResponseForEmployeeIdentity = new ValidateResponse("find by has to be email or id",false);
        }

        if(!validateResponseForEmployeeIdentity.isValid()){
            return new ResponseEntity<Response>(new Response(validateResponseForEmployeeIdentity.getMessage(),null),HttpStatus.BAD_REQUEST);
        }
        else{
            Response deletedEmployeeResponse = employeeFacade.removeEmployee(removeEmployeeDataRequest,findBy);
            return new ResponseEntity<Response>(deletedEmployeeResponse,HttpStatus.OK);
        }

    }

}
