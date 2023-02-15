package hu.kunb.meetingapp.controller;

import hu.kunb.meetingapp.apidefinition.spring.api.MeetingReservationApi;
import hu.kunb.meetingapp.apidefinition.spring.model.MeetingReservationRequestDto;
import hu.kunb.meetingapp.apidefinition.spring.model.MeetingReservationResponseDto;
import hu.kunb.meetingapp.reservation.boundary.RestBoundary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class MeetingReservationController implements MeetingReservationApi {

    @Autowired
    RestBoundary restBoundary;

    @Override
    @PostMapping("/private/meeting/reservation")
    public ResponseEntity<MeetingReservationResponseDto> postMeetingReservation(@RequestBody @Valid MeetingReservationRequestDto meetingReservationRequestDto) {

        boolean success = restBoundary.createReservation(meetingReservationRequestDto);

        MeetingReservationResponseDto response = new MeetingReservationResponseDto();
        response.setStatus("success");
        return ResponseEntity.ok(response);
    }
}
