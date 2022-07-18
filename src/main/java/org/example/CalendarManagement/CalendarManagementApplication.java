package org.example.CalendarManagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ServletComponentScan
@SpringBootApplication
@EnableJpaRepositories("org.example.CalendarManagement.calendarpersistence.repository")
@EntityScan("org.example.CalendarManagement.calendarpersistence.model")
public class CalendarManagementApplication
{
    public static void main(String[] args) {
        SpringApplication.run(CalendarManagementApplication.class, args);
    }
}
