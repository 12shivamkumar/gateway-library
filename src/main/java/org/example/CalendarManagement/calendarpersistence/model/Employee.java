package org.example.CalendarManagement.calendarpersistence.model;

import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table
public class Employee {
    @Id
    private String employeeId;
    @Column
    @NotNull(message = "employee name cannot be null")
    private String name;

    @Column
    @NotNull(message = "employee must belongs to office")
    private int officeId;

    @Column(unique = true)
    @NotNull(message = "employee email cannot be null")
    private String email;

    @Column
    private boolean isDeleted;

    @CreationTimestamp
    @Column
    private LocalDateTime createdDateTime;


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
