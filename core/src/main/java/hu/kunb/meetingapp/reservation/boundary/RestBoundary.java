package hu.kunb.meetingapp.reservation.boundary;

import hu.kunb.meetingapp.apidefinition.spring.model.MeetingReservationRequestDto;

import java.time.LocalDate;

public interface RestBoundary {
    boolean createReservation(MeetingReservationRequestDto dto);
}
