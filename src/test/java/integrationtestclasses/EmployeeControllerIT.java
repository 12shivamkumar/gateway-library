package integrationtestclasses;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.CalendarManagement.api.Response;
import org.example.CalendarManagement.api.request.AddEmployeeDataRequest;
import org.example.CalendarManagement.api.request.RemoveEmployeeDataRequest;
import org.example.CalendarManagement.calendarpersistence.model.Employee;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;


public class EmployeeControllerIT extends BaseIntegrationTestClass{

    @Test
    public void addEmployeeSuccessTest() throws JsonProcessingException {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        AddEmployeeDataRequest request =
            new AddEmployeeDataRequest("xyz-12", "tushar", "tushar@xyz.com", 2);
        Employee employee = new Employee("xyz-12", "tushar", 2, "tushar@xyz.com");
        String employeeRequestString = objectMapper.writeValueAsString(request);

        HttpEntity<String> httpEntity =
            new HttpEntity<String>(employeeRequestString, headers);

        ResponseEntity<Response> responseEntity =
            restTemplate.exchange(createURLWithPort("/employee"), HttpMethod.POST, httpEntity,
                Response.class);
        assertEquals(201, responseEntity.getStatusCodeValue());
        assertNotNull(responseEntity.getBody());
        Employee employeeSaved =
            objectMapper.convertValue(responseEntity.getBody().getData(), Employee.class);
        assertEquals(employee, employeeSaved);
    }

    @Test
    public void addEmployeeFailedTest() throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        AddEmployeeDataRequest request =
                new AddEmployeeDataRequest("xyz-13", "shivam","shivam@xyz.com", 3);

        String employeeRequestString = objectMapper.writeValueAsString(request);

        HttpEntity<String> httpEntity = new HttpEntity<String>(employeeRequestString,headers);

        ResponseEntity<Response> responseEntity =
                restTemplate.exchange(createURLWithPort("/employee") , HttpMethod.POST , httpEntity,  Response.class);

        assertEquals(400, responseEntity.getStatusCodeValue());
        assertNotNull(responseEntity.getBody());
        assertEquals("Office Information is Not Present" , responseEntity.getBody().getError());
    }
    @Test
    public void removeEmployeeSuccessTest() throws JsonProcessingException{

        String id = "xyz-12";
        HttpEntity<?> httpEntity = HttpEntity.EMPTY;
        ResponseEntity<Response> responseEntity =
                restTemplate.exchange(createURLWithPort("/employee/"+id),HttpMethod.DELETE,httpEntity,Response.class);
        assertEquals(200,responseEntity.getStatusCodeValue());
    }

    @Test
    public void removeEmployeeFailsTest(){
        String id = "xyz-123";
        HttpEntity<?> httpEntity = HttpEntity.EMPTY;
        ResponseEntity<Response> responseEntity =
                restTemplate.exchange(createURLWithPort("/employee/"+id),HttpMethod.DELETE,httpEntity,Response.class);
        assertEquals(400, responseEntity.getStatusCodeValue());
    }
}
