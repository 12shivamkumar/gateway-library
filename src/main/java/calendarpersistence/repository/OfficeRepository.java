package calendarpersistence.repository;

import calendarpersistence.model.Office;

import java.util.List;

public interface OfficeRepository {
    List<Office> findAll();
}
