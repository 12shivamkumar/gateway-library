package org.example.CalendarManagement.calendarservice.implementation;

import org.example.CalendarManagement.calendarpersistence.model.Office;
import org.example.CalendarManagement.calendarpersistence.repository.OfficeRepository;
import org.example.CalendarManagement.calendarservice.interfaces.OfficeInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class OfficeService implements OfficeInterface {
@Autowired
OfficeRepository officeRepository;

    @Override
    public Iterable findAll() {
        return officeRepository.findAll();
    }


    public Office addOffice(Office office){
        officeRepository.save(office);
        return office;
    }
}
