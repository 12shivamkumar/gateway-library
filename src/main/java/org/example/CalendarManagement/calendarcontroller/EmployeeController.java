package org.example.CalendarManagement.calendarcontroller;


import org.example.CalendarManagement.api.Response;
import org.example.CalendarManagement.api.request.AddEmployeeDataRequest;
//import org.example.CalendarManagement.api.request.RemoveEmployeeDataRequest;
//import org.example.CalendarManagement.api.request.RemoveEmployeeDataRequest;
import org.example.CalendarManagement.api.request.RemoveEmployeeDataRequest;
import org.example.CalendarManagement.api.validator.ValidateEmployeeEmail;
//import org.example.CalendarManagement.api.validator.ValidateEmployeeIdentity;
import org.example.CalendarManagement.api.validator.ValidateEmployeeId;
import org.example.CalendarManagement.api.validator.ValidateOfficeId;
import org.example.CalendarManagement.api.validator.ValidateResponse;
import org.example.CalendarManagement.calendarfacade.EmployeeFacade;
import org.example.CalendarManagement.calendarpersistence.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    ValidateOfficeId validateOfficeId;
    @Autowired
    ValidateEmployeeEmail validateEmployeeEmail;

    @Autowired
    ValidateEmployeeId validateEmployeeId;

    @Autowired
    EmployeeFacade employeeFacade;

    @PostMapping
    public ResponseEntity<Response> saveEmployee(@Valid @RequestBody AddEmployeeDataRequest request)
    {
          ValidateResponse validateResponseEmployeeIdExistsInDb =  validateEmployeeId.checkEmployeeId(request.getEmployeeId());

          if(validateResponseEmployeeIdExistsInDb.isValid())
          {
              Response addEmployeeResponse = new Response(validateResponseEmployeeIdExistsInDb.getMessage(), null);
              return new ResponseEntity<Response>(addEmployeeResponse, HttpStatus.BAD_REQUEST);
          }

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
    public ResponseEntity<Response> removeEmployee(@Valid @PathVariable(name = "id") String employeeId)  {

        ValidateResponse validateResponseForEmployeeIdentity= null;
        RemoveEmployeeDataRequest removeEmployeeDataRequest = new RemoveEmployeeDataRequest(employeeId);
        validateResponseForEmployeeIdentity = validateEmployeeId.checkEmployeeId(employeeId);

        if(!validateResponseForEmployeeIdentity.isValid()){
            return new ResponseEntity<Response>(new Response(validateResponseForEmployeeIdentity.getMessage(),null),HttpStatus.BAD_REQUEST);
        }
        else{
            try {
                Response deletedEmployeeResponse = employeeFacade.removeEmployee(removeEmployeeDataRequest);
                return new ResponseEntity<Response>(deletedEmployeeResponse, HttpStatus.OK);
            }catch (RuntimeException exception)
            {
                return  new ResponseEntity<>(new Response(exception.getMessage() ,null) , HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

    }
}
