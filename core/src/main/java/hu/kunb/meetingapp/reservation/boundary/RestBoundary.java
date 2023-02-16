package hu.kunb.meetingapp.reservation.boundary;

import hu.kunb.meetingapp.apidefinition.spring.model.MeetingReservationRequestDto;
import hu.kunb.meetingapp.reservation.modell.MeetingReservation;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

public interface RestBoundary {
    MeetingReservation createReservation(MeetingReservationRequestDto dto);
    Collection<MeetingReservation> getAll();
    Optional<MeetingReservation> findByDateTime(LocalDateTime dateTime);
}
