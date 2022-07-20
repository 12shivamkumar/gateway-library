package integrationtestclasses;

import org.example.CalendarManagement.CalendarManagementApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ActiveProfiles;
import testclasses.EmbeddedMySqlConfiguration;
import testclasses.EmbeddedRedisConfiguration;
//import testclasses.PopulateDatabase;


@SpringBootTest(classes = { EmbeddedMySqlConfiguration.class,CalendarManagementApplication.class },
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class BaseIntegrationTestClass {
    @LocalServerPort
    private int port;

    TestRestTemplate restTemplate = new TestRestTemplate();

    HttpHeaders headers = new HttpHeaders();
    public String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }


}
