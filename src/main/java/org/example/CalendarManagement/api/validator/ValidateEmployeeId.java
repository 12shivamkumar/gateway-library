package org.example.CalendarManagement.api.validator;

import org.example.CalendarManagement.calendarpersistence.model.Employee;
import org.example.CalendarManagement.calendarpersistence.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ValidateEmployeeId {

    @Autowired
    private EmployeeRepository employeeRepository;

    public ValidateResponse checkEmployeeId(String id)
    {
        ValidateResponse validateResponse = null;
        if(id.length()<5)
           return new ValidateResponse("please provide correct employee id" , false);

        Optional<Employee> responseFromDb = employeeRepository.findById(id);

        if(responseFromDb.isPresent())
        {
            validateResponse = new ValidateResponse( "Employee Exists" , true);
        }
        else
        {
            validateResponse = new ValidateResponse("Employee does not exists", false);
        }
      return validateResponse;
    }

}
