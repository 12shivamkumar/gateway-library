

package org.example.CalendarManagement.calendarpersistence.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class Office {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "office_name")
    @NotNull
    private String name;

    @Column(name = "location",unique = true)
    @NotNull
    private String officeLocation;

    @CreationTimestamp
    @Column(name = "created_date")
    private LocalDateTime createdDateTime;

    public Office(){}

    public Office(int officeID, String officeName, String officeLocation) {
        this.id = officeID;
        this.name = officeName;
        this.officeLocation = officeLocation;
    }
}