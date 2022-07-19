package org.example.CalendarManagement.calendarpersistence.model;

import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "Employee")
public class Employee {
    @Id
    @Column(name = "EmployeeId")
    private String employeeId;
    @Column(name = "Name")
    @NotNull(message = "employee name cannot be null")
    private String name;

    @Column(name = "OfficeId")
    @NotNull(message = "employee must belongs to office")
    private int officeId;

    @Column(name = "Email", unique = true)
    @NotNull(message = "employee email cannot be null")
    private String email;

    @Column(name = "IsDeleted")
    private boolean isDeleted;

    @CreationTimestamp
    @Column(name = "Log")
    private LocalDateTime createdDateTime;


    public Employee(){}

    public Employee(String employeeId, String name, int officeId, String email) {
        this.employeeId = employeeId;
        this.name = name;
        this.officeId = officeId;
        this.email = email;

    }

    public String getEmployeeId() {
        return employeeId;
    }
    public String getName() {
        return name;
    }
    public int getOfficeId() {
        return officeId;
    }
    public String getEmail() {
        return email;
    }
    public boolean isDeleted() {
        return isDeleted;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeId='" + employeeId + '\'' +
                ", name='" + name + '\'' +
                ", officeId=" + officeId +
                ", email='" + email + '\'' +
                ", isDeleted=" + isDeleted +
                ", createdDateTime=" + createdDateTime +
                '}';
    }
}
