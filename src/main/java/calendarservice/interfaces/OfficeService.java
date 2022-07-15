package calendarservice.interfaces;

import calendarpersistence.model.Office;

import java.util.List;

public interface OfficeService {
    List<Office> findAllOffices();
}
