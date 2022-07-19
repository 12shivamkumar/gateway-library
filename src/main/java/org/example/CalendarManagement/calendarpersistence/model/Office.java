package org.example.CalendarManagement.calendarpersistence.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "Office")
public class Office {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "OfficeId")
    private int officeID;

    @Column(name = "OfficeName")
    @NotNull(message = "office name cannot be null")
    private String officeName;

    @Column(unique = true , name = "Location")
    @NotNull(message = "office location cannot be null")
    private String officeLocation;

    @CreationTimestamp
    @Column(name = "Log")
    private LocalDateTime createdDateTime;

    public Office(){}

    public Office(int officeID, String officeName, String officeLocation) {
        this.officeID = officeID;
        this.officeName = officeName;
        this.officeLocation = officeLocation;
    }

    public int getOfficeID() {
        return officeID;
    }
    public String getOfficeName() {
        return officeName;
    }
    public String getOfficeLocation() {
        return officeLocation;
    }
    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
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
