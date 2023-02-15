package hu.kunb.meetingapp.reservation.gateway;

import hu.kunb.meetingapp.reservation.modell.MeetingReservation;

import java.time.LocalDateTime;
import java.util.List;

public interface PersistenceGateway {
    void storeReservation(MeetingReservation reservation);
    List<MeetingReservation> getAllReservations();
    MeetingReservation findByDateTime(LocalDateTime localDateTime);
    void deleteReservation(LocalDateTime localDateTime);
}
