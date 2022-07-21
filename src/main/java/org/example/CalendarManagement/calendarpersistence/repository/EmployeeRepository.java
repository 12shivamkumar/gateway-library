package org.example.CalendarManagement.calendarpersistence.repository;

import org.example.CalendarManagement.calendarpersistence.model.Employee;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee,String>
{
    Optional<Employee> findByEmail(String email);

    @Query("UPDATE  employee e SET is_deleted = ”true” WHERE e.id = :id")
    Employee deletedById(@Param("id") String id);

    @Query("UPDATE  employee e SET is_deleted = ”true” WHERE e.email = :email")
    Employee deleteByEmail(@Param("email") String email);
}

