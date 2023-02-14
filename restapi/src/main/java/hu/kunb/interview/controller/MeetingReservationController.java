package hu.kunb.interview.controller;

import hu.kunb.interview.restapi.spring.api.MeetingReservationApi;
import hu.kunb.interview.restapi.spring.model.MeetingReservationRequestDto;
import hu.kunb.interview.restapi.spring.model.MeetingReservationResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MeetingReservationController implements MeetingReservationApi {

    @Override
    @PostMapping("/private/meeting/reservation")
    public ResponseEntity<MeetingReservationResponseDto> postMeetingReservation(MeetingReservationRequestDto meetingReservationRequestDto) {
        return MeetingReservationApi.super.postMeetingReservation(meetingReservationRequestDto);
    }
}
