package calendarpersistence.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class Office {
    @Id
    private int officeID;

    @Column
    @NotNull(message = "office name cannot be null")
    private String officeName;

    @Column(unique = true)
    @NotNull(message = "office location cannot be null")
    private String officeLocation;

    @CreationTimestamp
    @Column
    private LocalDateTime createdDateTime;

    public Office(int officeID, String officeName, String officeLocation, LocalDateTime createdDateTime) {
        this.officeID = officeID;
        this.officeName = officeName;
        this.officeLocation = officeLocation;
        this.createdDateTime = createdDateTime;
    }

    public int getOfficeID() {
        return officeID;
    }

    public void setOfficeID(int officeID) {
        this.officeID = officeID;
    }

    public String getOfficeName() {
        return officeName;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }

    public String getOfficeLocation() {
        return officeLocation;
    }

    public void setOfficeLocation(String officeLocation) {
        this.officeLocation = officeLocation;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(LocalDateTime createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    @Override
    public String toString() {
        return "Office{" +
                "officeID=" + officeID +
                ", officeName='" + officeName + '\'' +
                ", officeLocation='" + officeLocation + '\'' +
                ", createdDateTime=" + createdDateTime +
                '}';
    }
}
