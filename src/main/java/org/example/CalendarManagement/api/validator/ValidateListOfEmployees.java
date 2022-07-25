package org.example.CalendarManagement.api.validator;

import org.example.CalendarManagement.calendarpersistence.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ValidateListOfEmployees {
    @Autowired
    ValidateEmployeeIdentity validateEmployeeIdentity;

    @Autowired
    EmployeeRepository employeeRepository;

    public ValidateResponse checkIfEmployeeExistInSameOffice(List<String> listOfEmployeeId,int officeId){
        int countOfEmployeeInDB = employeeRepository.countByIdIn(listOfEmployeeId);
        if(countOfEmployeeInDB == listOfEmployeeId.size()){
            List<Integer> listOfOfficeId = employeeRepository.findOfficeByEmployeeId(listOfEmployeeId);
            if(listOfOfficeId.size()==1 & listOfOfficeId.get(0) == officeId){
                return new ValidateResponse(" Employees exist in DB and belong to same office",true);
            }
            else {
                return new ValidateResponse("Employee working in different offices",false);
            }
        }
        else {
            return new ValidateResponse("Not all employees exist",false);
        }
    }
}
