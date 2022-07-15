package calendarpersistence.repository;

import calendarpersistence.model.Office;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface OfficeRepository {
    List<Office> findAll();
}
