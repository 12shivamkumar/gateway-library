package calendarservice.interfaces;

import calendarpersistence.model.Employee;
import org.springframework.stereotype.Service;

import java.util.List;

public interface EmployeeService {
    Employee addEmployee(Employee employee);

    Employee removeEmployee(String empID);
    int getOffice(List<String> empID);
    List<String> findEmployeeByID(List<String> empID);
}
