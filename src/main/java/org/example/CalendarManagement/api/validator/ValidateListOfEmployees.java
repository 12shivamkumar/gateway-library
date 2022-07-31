package org.example.CalendarManagement.api.validator;

import org.example.CalendarManagement.calendarpersistence.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@DependsOn("validateEmployeeId")
public class ValidateListOfEmployees {
    @Autowired
    ValidateEmployeeId validateEmployeeId;

    @Autowired
    EmployeeRepository employeeRepository;

    public ValidateResponse checkIfEmployeeExistInSameOffice(List<String> listOfEmployeeId,int officeId){
        Set<String> listOfEmployeeDuplicateCheck = new HashSet<>(listOfEmployeeId);
        if(listOfEmployeeDuplicateCheck.size()!=listOfEmployeeId.size()){
            return new ValidateResponse(" Duplicate employee found",false);
        }
        for(String employeeId:listOfEmployeeId){
            ValidateResponse validateResponse = validateEmployeeId.checkEmployeeId(employeeId);
            if(!validateResponse.isValid()){
                return new ValidateResponse("Not all employees exist in db",false);
            }
        }
        int countOfEmployeeInDB = employeeRepository.countByIdIn(listOfEmployeeId);
        if(countOfEmployeeInDB == listOfEmployeeId.size())
        {
            Set<Integer> listOfOfficeId = new HashSet<>(employeeRepository.findOfficeByEmployeeId(listOfEmployeeId));

            if(listOfOfficeId.size()== 1 && listOfOfficeId.contains(officeId)){
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
