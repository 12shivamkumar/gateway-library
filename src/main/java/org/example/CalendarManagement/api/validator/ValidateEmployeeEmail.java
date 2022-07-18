package org.example.CalendarManagement.api.validator;

import org.example.CalendarManagement.calendarpersistence.model.Employee;
import org.example.CalendarManagement.calendarpersistence.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ValidateEmployeeEmail {
    @Autowired
    EmployeeRepository employeeRepository;
    public ValidateResponse checkEmployeeEmailExist(String email){
        ValidateResponse responseForEmployeeEmail = null;
        Optional<Employee> employee =employeeRepository.findByEmail(email);
        if(employee.isPresent()){
            responseForEmployeeEmail =new ValidateResponse("Employee with this email exists",true);
        }
        else {
            responseForEmployeeEmail = new ValidateResponse("Employee with this email does not exist",false);
        }
        return responseForEmployeeEmail;
    }
}
