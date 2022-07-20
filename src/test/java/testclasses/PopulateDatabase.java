package testclasses;

import org.example.CalendarManagement.calendarpersistence.model.Office;
import org.example.CalendarManagement.calendarpersistence.repository.OfficeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/*
@Component
public class PopulateDatabase {
    @Autowired
    OfficeRepository officeRepository;
    @PostConstruct
    public void postConstruct(){
        Office office = new Office(1,"XYZ Banglore","hsr layout");
        Office office2 = new Office(2,"XYZ Hyderabad","Madhapur");
        officeRepository.save(office);
        officeRepository.save(office2);

    }
}
*/
