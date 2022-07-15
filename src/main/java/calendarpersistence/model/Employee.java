package calendarpersistence.model;

import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
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

    public Employee(String employeeId, String name, int officeId, String email, boolean isDeleted, LocalDateTime createdDateTime) {
        this.employeeId = employeeId;
        this.name = name;
        this.officeId = officeId;
        this.email = email;
        this.isDeleted = isDeleted;
        this.createdDateTime = createdDateTime;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOfficeId() {
        return officeId;
    }

    public void setOfficeId(int officeId) {
        this.officeId = officeId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
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
