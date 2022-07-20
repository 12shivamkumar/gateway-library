package integrationtestclasses;

import org.example.CalendarManagement.api.Response;
import org.example.CalendarManagement.api.request.AddEmployeeDataRequest;
import org.example.CalendarManagement.calendarpersistence.model.Employee;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;


public class AddEmployee extends BaseIntegrationTestClass{

@Test
public void addEmployeeTest() throws JSONException {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    Employee employee = new Employee("xyz-12","tushar",2,"tushar@xyz.com");
    //AddEmployeeDataRequest employeeDataRequest = new AddEmployeeDataRequest("xyz-12","tushar","tushar@xyz.com",2);

    JSONObject employeeJsonObject = new JSONObject();
    employeeJsonObject.put("officeId",1);
    employeeJsonObject.put("email","tushar@xyz.com");
    employeeJsonObject.put("name","tushar");
    employeeJsonObject.put("employeeId","xyz-12");

    HttpEntity<String> httpEntity = new HttpEntity<String>(employeeJsonObject.toString(),headers);

    ResponseEntity<Response> responseEntity = restTemplate.exchange(createURLWithPort("/employee"), HttpMethod.POST,httpEntity,Response.class);
    assertEquals(201,responseEntity.getStatusCodeValue());
    assertNotNull(responseEntity.getBody());
    assertEquals(employee,responseEntity.getBody().getData());
}
}
