package integrationtestclasses;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.CalendarManagement.api.Response;
import org.example.CalendarManagement.api.request.AddMeetingDataRequest;
import org.example.CalendarManagement.calendarpersistence.repository.MeetingRoomRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MeetingControllerIT extends BaseIntegrationTest
{
    @Autowired
    MeetingRoomRepository meetingRoomRepository;

    @Test
    public void scheduleMeetingSuccessTest_roomIsGiven() throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        LocalDate dateOfMeeting = LocalDate.of(2022,8,26);
        LocalTime startTime = LocalTime.of(16,00);
        LocalTime endTime = LocalTime.of(16,50);
        List<String> employeeList = Arrays.asList("abc-11", "abc-12", "abc-13", "abc-14", "abc-15", "abc-16");
        AddMeetingDataRequest request = new AddMeetingDataRequest.Builder("abc-10" , "sync-up","details",employeeList, dateOfMeeting,startTime, endTime, "Room1").build();
        String scheduleMeetingRequestString = objectMapper.writeValueAsString(request);

        HttpEntity<String> httpEntity =
                new HttpEntity<String>(scheduleMeetingRequestString, headers);

        ResponseEntity<Response> responseEntity = restTemplate.exchange(createURLWithPort("/meeting"), HttpMethod.POST, httpEntity,
                Response.class);
        assertEquals(201, responseEntity.getStatusCodeValue());
        assertNotNull(responseEntity.getBody());
        assertNotNull(responseEntity.getBody().getData());
    }

    @Test
    public void scheduleMeetingSuccessTest_roomIsNotGiven() throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        LocalDate dateOfMeeting = LocalDate.of(2022,8,26);
        LocalTime startTime = LocalTime.of(16,00);
        LocalTime endTime = LocalTime.of(16,50);
        List<String> employeeList = Arrays.asList("abc-11", "abc-12", "abc-13", "abc-14", "abc-15", "abc-16");
        AddMeetingDataRequest request = new AddMeetingDataRequest.Builder("abc-10" , "sync-up","details",employeeList, dateOfMeeting,startTime, endTime, "").build();
        String scheduleMeetingRequestString = objectMapper.writeValueAsString(request);

        HttpEntity<String> httpEntity =
                new HttpEntity<String>(scheduleMeetingRequestString, headers);
        ResponseEntity<Response> responseEntity = restTemplate.exchange(createURLWithPort("/meeting"), HttpMethod.POST, httpEntity,
                Response.class);
        assertEquals(201, responseEntity.getStatusCodeValue());
        assertNotNull(responseEntity.getBody());
        assertNotNull(responseEntity.getBody().getData());
    }

    @Test
    public void scheduleMeetingFailTest_roomIsGiven() throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        LocalDate dateOfMeeting = LocalDate.of(2022,8,26);
        LocalTime startTime = LocalTime.of(16,00);
        LocalTime endTime = LocalTime.of(16,50);
        List<String> employeeList = Arrays.asList("abc-11", "abc-12", "abc-13", "abc-14", "abc-15", "abc-16");

        AddMeetingDataRequest request = new AddMeetingDataRequest.Builder("abc-10" , "sync-up","details",employeeList, dateOfMeeting,startTime, endTime, "Room5").build();
        String scheduleMeetingRequestString = objectMapper.writeValueAsString(request);

        HttpEntity<String> httpEntity =
                new HttpEntity<String>(scheduleMeetingRequestString, headers);

        ResponseEntity<Response> responseEntity = restTemplate.exchange(createURLWithPort("/meeting"), HttpMethod.POST, httpEntity,
                Response.class);

        assertEquals(400, responseEntity.getStatusCodeValue());
        assertNotNull(responseEntity.getBody());
        assertEquals("meeting room does not exist or is closed" , responseEntity.getBody().getError());
    }

    @Test
    @DisplayName("Meeting is Not Productive with seven employees , fails validation")
    public void scheduleMeetingFailTest_roomIsNotGiven() throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        LocalDate dateOfMeeting = LocalDate.of(2022,8,26);
        LocalTime startTime = LocalTime.of(16,00);
        LocalTime endTime = LocalTime.of(16,50);
        List<String> employeeList = Arrays.asList("abc-11", "abc-12", "abc-13", "abc-14", "abc-15", "abc-16" ,"abc-17");
        AddMeetingDataRequest request = new AddMeetingDataRequest.Builder
                ("abc-10" , "sync-up","details",employeeList, dateOfMeeting,startTime, endTime, "").build();

        String scheduleMeetingRequestString = objectMapper.writeValueAsString(request);

        HttpEntity<String> httpEntity =
                new HttpEntity<String>(scheduleMeetingRequestString, headers);

        ResponseEntity<Response> responseEntity = restTemplate.exchange(createURLWithPort("/meeting"), HttpMethod.POST, httpEntity,
                Response.class);

        assertEquals(400, responseEntity.getStatusCodeValue());
        assertNotNull(responseEntity.getBody());

    }

    @Test
    public void scheduleMeetingFailTest_internalServerError() throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        LocalDate dateOfMeeting = LocalDate.of(2022,8,26);
        LocalTime startTime = LocalTime.of(16,00);
        LocalTime endTime = LocalTime.of(16,50);
        List<String> employeeList = Arrays.asList("abc-11", "abc-12", "abc-13");
        AddMeetingDataRequest request = new AddMeetingDataRequest.Builder
                ("abc-10" , "sync-up","details",employeeList, dateOfMeeting,startTime, endTime, "").build();

        String scheduleMeetingRequestString = objectMapper.writeValueAsString(request);

        HttpEntity<String> httpEntity =
                new HttpEntity<String>(scheduleMeetingRequestString, headers);

        ResponseEntity<Response> responseEntity = restTemplate.exchange(createURLWithPort("/meeting"), HttpMethod.POST, httpEntity,
                Response.class);

        assertEquals(500, responseEntity.getStatusCodeValue());

    }
}
