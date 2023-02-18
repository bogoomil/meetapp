package hu.kunb.meetingapp.systemtest.stepdefinitions;

import hu.kunb.meetingapp.apidefinition.spring.model.ErrorDto;
import hu.kunb.meetingapp.apidefinition.spring.model.MeetingReservationDto;
import hu.kunb.meetingapp.apidefinition.spring.model.MeetingReservationRequestDto;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.messages.internal.com.fasterxml.jackson.core.JsonProcessingException;
import io.cucumber.messages.internal.com.fasterxml.jackson.databind.DeserializationFeature;
import io.cucumber.messages.internal.com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StepDefinitions {

    public static final String USER_NAME = "USER_NAME";
    private static RestTemplate restTemplate = new RestTemplate();
    private ResponseEntity<MeetingReservationDto> response;
    private MeetingReservationRequestDto request;
    private ErrorDto errorDto;


    @Given("four meetings already reserved")
    public void meetingKezdoIdopontL() {
        List<MeetingReservationRequestDto> requests = Arrays.asList(
                new MeetingReservationRequestDto()
                        .day(LocalDate.of(2023, 1, 4))
                        .startHour(9)
                        .endHour(11)
                        .startMin(0)
                        .endMin(0)
                        .username(USER_NAME),
                new MeetingReservationRequestDto()
                        .day(LocalDate.of(2023, 1, 4))
                        .startHour(11)
                        .endHour(13)
                        .startMin(0)
                        .endMin(0)
                        .username(USER_NAME),
                new MeetingReservationRequestDto()
                        .day(LocalDate.of(2023, 1, 4))
                        .startHour(13)
                        .endHour(15)
                        .startMin(0)
                        .endMin(0)
                        .username(USER_NAME),
                new MeetingReservationRequestDto()
                        .day(LocalDate.of(2023, 1, 4))
                        .startHour(15)
                        .endHour(17)
                        .startMin(0)
                        .endMin(0)
                        .username(USER_NAME));
        for (MeetingReservationRequestDto requestDto : requests){
            try{
                restTemplate.postForEntity("http://localhost:8080/api/private/meeting/reservation", request, MeetingReservationDto.class);
            }catch (Exception e){
                System.out.println("e:" + e.getMessage());
            }
        }
    }

    @When("i try to create a new reservation")
    public void meghivomASzolgaltatast() throws JsonProcessingException {
        MeetingReservationRequestDto requestDto = new MeetingReservationRequestDto()
                .day(LocalDate.of(2023, 1, 4))
                .startHour(10)
                .endHour(12)
                .startMin(0)
                .endMin(0)
                .username(USER_NAME);


        HttpClientErrorException e = assertThrows(HttpClientErrorException.class, () -> restTemplate.postForEntity("http://localhost:8080/api/private/meeting/reservation", requestDto, MeetingReservationDto.class));
        ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        errorDto = objectMapper.readValue(e.getResponseBodyAsString(), ErrorDto.class);


    }

    @Then("i got an exception")
    public void megkapomAValaszt() throws IOException {
        assertNotNull(errorDto);
//        assertEquals(USER_NAME, response.getBody().getUser());
    }
}
