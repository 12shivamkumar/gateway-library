package testclasses;


import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.MysqldConfig;
import com.wix.mysql.config.SchemaConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.aspectj.lang.annotation.Before;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.test.context.event.annotation.AfterTestClass;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.sql.DataSource;

import static com.wix.mysql.config.Charset.UTF8;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v5_7_latest;
@Configuration
public class EmbeddedMySqlConfiguration {
    private EmbeddedMysql mysqld;
    private final String SCHEMA_FILE="schema.sql";



    @ConfigurationProperties(prefix = "app.datasource")
    @Bean
    @Profile("test")
    @DependsOn("embeddedMySqlConfiguration")
    public HikariDataSource dataSource(){

        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @PostConstruct
    public void startEmbeddedDatabase() {
        MysqldConfig config = aMysqldConfig(v5_7_latest)
                .withCharset(UTF8)
                .withPort(3060)
                .withUser("test", "test")
                .build();
        SchemaConfig schemaConfig = SchemaConfig.aSchemaConfig("test_database")
                .build();
        mysqld = EmbeddedMysql.anEmbeddedMysql(config).addSchema(schemaConfig).start();
    }
    @PreDestroy
    public void tearDownEmbeddedDatabase(){
        mysqld.stop();
    }

}
