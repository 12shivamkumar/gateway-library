package org.example.CalendarManagement.api.validator;

import java.time.LocalDate;
import java.time.LocalTime;

public class ValidateMeetingDateTime {

    public ValidateResponse checkMeetingDateTime(LocalDate date , LocalTime startTime)
    {
        if(!date.isBefore(LocalDate.now()))
        {
           if(startTime.getHour()>=LocalTime.now().getHour()){
               return new ValidateResponse("meeting can be scheduled" , true);
           }
           else{
                return new ValidateResponse("Invalid TIme for Meeting" , false);
           }
        }else {
            return  new ValidateResponse("Invalid Date for Meeting" , false);
        }
    }

}


