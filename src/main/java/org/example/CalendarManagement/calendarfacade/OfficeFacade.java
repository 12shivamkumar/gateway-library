package org.example.CalendarManagement.calendarfacade;

import org.example.CalendarManagement.calendarpersistence.model.Office;
import org.example.CalendarManagement.calendarservice.implementation.OfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/office")
public class OfficeFacade {
    @Autowired
    OfficeService officeService;
    @PostMapping
    public ResponseEntity<Object> addOfice(@Valid@RequestBody Office office){
        officeService.addOffice(office);
        return new ResponseEntity<>("Office created", HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<Object> findAll(){
        return new ResponseEntity<>(officeService.findAll(),HttpStatus.OK);
    }
}
