package org.example.CalendarManagement.CalendarFacade;

import org.example.CalendarManagement.api.request.AddEmployeeDataRequest;
import org.example.CalendarManagement.api.validator.ValidateOfficeId;
import org.example.CalendarManagement.api.validator.ValidateResponse;
import org.example.CalendarManagement.calendarpersistence.model.Employee;
import org.example.CalendarManagement.calendarservice.implementation.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/employee")
public class EmployeeFacade {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> saveEmployee(@Valid @RequestBody AddEmployeeDataRequest request)
    {
//        ValidateOfficeId validateOfficeId = new ValidateOfficeId();
//        ValidateResponse validateResponse = validateOfficeId.checkOfficeId(request);

     //   if(validateResponse.isValid()) {
            Employee employee = new Employee(request.getEmployeeId(), request.getName(), request.getOfficeId()
            , request.getEmail(),false, LocalDateTime.now());
            System.out.println("Saving in the repo....");
            employeeService.addEmployee(employee);
            return new ResponseEntity<>(employee,HttpStatus.OK);
//        }
//        else {
//            String message = validateResponse.getMessage();
//            return new ResponseEntity<>(message,HttpStatus.BAD_REQUEST);
//        }
    }
}
