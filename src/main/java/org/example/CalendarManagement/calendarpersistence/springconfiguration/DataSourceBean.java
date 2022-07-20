package org.example.CalendarManagement.calendarpersistence.springconfiguration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/*@Configuration
@Component
public class DataSourceBean {
    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    @Profile("!test")
    DataSource createDataSource(){
        return DataSourceBuilder.create().build();
    }
}*/
