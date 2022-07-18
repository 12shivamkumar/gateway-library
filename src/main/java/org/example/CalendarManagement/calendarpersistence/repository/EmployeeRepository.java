package org.example.CalendarManagement.calendarpersistence.repository;

import org.example.CalendarManagement.calendarpersistence.model.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee,String>
{

}

