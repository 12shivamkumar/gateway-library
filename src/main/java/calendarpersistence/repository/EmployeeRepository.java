package calendarpersistence.repository;

import calendarpersistence.model.Employee;

import java.util.List;

public interface EmployeeRepository {
    Employee addEmployee(Employee employee);
    Employee removeEmployee(String empID);
    int getOffice(List<String> empID);
    List<String> findEmployeeByID(List<String> empID);
}
