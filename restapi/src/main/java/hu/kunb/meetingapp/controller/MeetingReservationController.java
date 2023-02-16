package hu.kunb.meetingapp.controller;

import hu.kunb.meetingapp.apidefinition.spring.api.MeetingReservationApi;
import hu.kunb.meetingapp.apidefinition.spring.model.MeetingListDto;
import hu.kunb.meetingapp.apidefinition.spring.model.MeetingReservationDto;
import hu.kunb.meetingapp.apidefinition.spring.model.MeetingReservationRequestDto;
import hu.kunb.meetingapp.reservation.boundary.RestBoundary;
import hu.kunb.meetingapp.reservation.exception.ValidationException;
import hu.kunb.meetingapp.reservation.modell.MeetingReservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Comparator;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class MeetingReservationController implements MeetingReservationApi {

    @Autowired
    RestBoundary restBoundary;

    @Override
    @PostMapping("/private/meeting/reservation")
    public ResponseEntity<MeetingReservationDto> createReservation(@RequestBody @Valid MeetingReservationRequestDto meetingReservationRequestDto) {
        MeetingReservation reservation = restBoundary.createReservation(meetingReservationRequestDto);
        MeetingReservationDto response = getMeetingReservationDto(reservation);
        return ResponseEntity.ok(response);
    }

    @Override
    @GetMapping("/private/meeting/listall")
    public ResponseEntity<MeetingListDto> listAll() {
        MeetingListDto response = new MeetingListDto();
        restBoundary.getAll()
                .stream()
                .sorted(Comparator.comparing(MeetingReservation::getStartTime))
                .forEach(meeting -> response.add(getMeetingReservationDto(meeting)));
        return ResponseEntity.ok(response);
    }

    @Override
    @GetMapping("/private/meeting/findbytime/{datetime}")
    public ResponseEntity<MeetingReservationDto> findByDateTime(@PathVariable String datetime) {
        try{
            LocalDateTime localDateTime = LocalDateTime.parse(datetime, DateTimeFormatter.ofPattern("yy.MM.dd HH:mm"));
            Optional<MeetingReservation> response = restBoundary.findByDateTime(localDateTime);
            if(response.isPresent()){
                return ResponseEntity.ok(getMeetingReservationDto(response.get()));
            }
        }catch (DateTimeParseException e){
            ValidationException exception = new ValidationException();
            exception.getErrors().add(e.getMessage());
            throw exception;
        }
        ValidationException exception = new ValidationException();
        exception.getErrors().add("not found");
        throw exception;
    }

    private MeetingReservationDto getMeetingReservationDto(MeetingReservation reservation) {
        MeetingReservationDto response = new MeetingReservationDto()
                .day(reservation.getStartTime().toLocalDate())
                .start("from: " + reservation.getStartTime().getHour() + ":" + reservation.getStartTime().getMinute())
                .end("to: " + reservation.getEndTime().getHour() + ":" + reservation.getEndTime().getMinute())
                .user(reservation.getUserName());
        return response;
    }

}
