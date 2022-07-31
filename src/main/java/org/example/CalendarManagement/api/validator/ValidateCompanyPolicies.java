package org.example.CalendarManagement.api.validator;

import org.example.CalendarManagement.calendarpersistence.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

@Component
public class ValidateCompanyPolicies {
    public ValidateResponse noOfEmployeeInMeeting(List<String> employeeList)
    {
        int noOfEmployeeInMeeting = employeeList.size();
        if(noOfEmployeeInMeeting > 6){
            return  new ValidateResponse("Employees more than six are present So meeting is not Productive" ,false);
        }else {
            return new ValidateResponse("Employee less than or equal to six are present so meeting is productive" , true);
        }
    }

    public ValidateResponse meetingDurationGreaterThanThirtyMinutes(LocalTime startTime, LocalTime endTime)
    {
        Duration duration = Duration.between(startTime , endTime);

        if(duration.toMinutes()>=30) {
            return new ValidateResponse("meeting is productive", true);
        }else if(duration.toMinutes()<0)
        {
            return new ValidateResponse("meeting starting time is after meeting end time", false);
        }
        else{
            return new ValidateResponse("meeting won't be productive" , false);
        }
    }

    public ValidateResponse meetingBetweenOfficeHours(LocalTime startTime, LocalTime endTime)
    {
        LocalTime officeOpen = LocalTime.of(9,59);
        LocalTime officeClose = LocalTime.of(18,01);

        if(startTime.isAfter(officeOpen) && startTime.isBefore(officeClose)
                && endTime.isBefore(officeClose)) {
            return new ValidateResponse("meeting is productive", true);
        }
        else {
            return new ValidateResponse("meeting won't be productive" , false);
        }
    }
}
