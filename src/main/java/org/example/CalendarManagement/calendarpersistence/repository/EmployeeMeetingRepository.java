package org.example.CalendarManagement.calendarpersistence.repository;

import org.example.CalendarManagement.calendarpersistence.model.Employee;
import org.example.CalendarManagement.calendarpersistence.model.EmployeeMeeting;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Repository
public interface EmployeeMeetingRepository extends CrudRepository<EmployeeMeeting,String> {

    @Query("UPDATE  employee_meeting em SET em.status = “removed” WHERE em.emp_id = :id")
    void changeStatusOfEmployeeMeeting(@Param("id")String id);
    @Query("SELECT em.meet_id from employee_meeting me WHERE em.status = “removed” WHERE em.emp_id = :id" )
    List<String> meetingsWithStatusRemovedForRemovedEmployee(@Param("id")String id);

}
