package integrationtestclasses;

import com.fasterxml.jackson.databind.ObjectMapper;
import integrationtestclasses.config.ThriftMeetingServiceClientImpl;
import org.example.CalendarManagement.CalendarManagementApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ActiveProfiles;
import integrationtestclasses.config.MySqlConfiguration;
//import integrationtestclasses.testclasses.PopulateDatabase;


@SpringBootTest(classes = { MySqlConfiguration.class,CalendarManagementApplication.class, ThriftMeetingServiceClientImpl.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class BaseIntegrationTestClass {
    @LocalServerPort
    private int port;

    @Autowired
    protected ObjectMapper objectMapper;

    TestRestTemplate restTemplate = new TestRestTemplate();

    HttpHeaders headers = new HttpHeaders();
    public String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }


}
