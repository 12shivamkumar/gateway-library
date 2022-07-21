package org.example.CalendarManagement.calendarpersistence.repository;

import org.example.CalendarManagement.calendarpersistence.model.Meeting;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeetingRepository extends CrudRepository<Meeting,String> {
    @Query("UPDATE meeting m SET m.is_available = 0 WHERE owner_name = (select name from employee e where e.id =:id)")
    void cancelMeetingsOfDeletedEmployee(@Param("id")String id);
    @Query("SELECT m.id from meeting m WHERE m.is_available= 0 AND owner_name = (select name from employee e where e.id =:id)")
    List<String> cancelledMeetingsWhoseOwnerWasRemoved(@Param("id") String id);

}
