package org.example.CalendarManagement.api.validator;

import org.example.CalendarManagement.api.request.AddEmployeeDataRequest;
import org.example.CalendarManagement.calendarpersistence.model.Office;
import org.example.CalendarManagement.calendarpersistence.repository.OfficeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ValidateOfficeId {

    @Autowired
    private OfficeRepository officeRepository;
    public ValidateResponse checkOfficeId(AddEmployeeDataRequest request)
    {
        ValidateResponse validateResponse = null;

        System.out.println(request.getOfficeId());

        Optional<Office> responseFromDb = officeRepository.findById(request.getOfficeId());

        boolean isPresent = responseFromDb.isPresent();

        System.out.println(isPresent);

        if(isPresent)
        {
            validateResponse = new ValidateResponse("Office Information is Present" , isPresent);
            return validateResponse;
        }
        else
        {
            validateResponse = new ValidateResponse("Office Information is Not Present" , isPresent);
            return validateResponse;
        }
    }

}
