package org.example.CalendarManagement.calendarpersistence.repository;

import org.example.CalendarManagement.calendarpersistence.model.Employee;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee,String>
{
    @Query(value = "SELECT  * FROM employee e WHERE e.id = :id AND e.is_deleted = false" , nativeQuery = true)
    Optional<Employee> findById(String id);

    Optional<Employee> findByEmail(String email);

    @Query(value = "SELECT e.office_id FROM employee e WHERE e.id IN :listOfEmployeeId AND e.is_deleted=false" ,nativeQuery = true)
    List<Integer> findOfficeByEmployeeId(@Param("listOfEmployeeId") List<String> listOfEmployeeId);

    @Query(value = "SELECT COUNT(e.id) FROM employee e WHERE e.id IN :listOfEmployeeId AND e.is_deleted=false" , nativeQuery = true)
    Integer countByIdIn(@Param("listOfEmployeeId") List<String> listOfEmployeeId);

    @Query(value = "SELECT e.office_id FROM employee e WHERE e.id = :id" , nativeQuery = true)
    Integer findOfficeIdById(String id);
}

