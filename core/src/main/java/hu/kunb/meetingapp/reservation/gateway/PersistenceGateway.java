package hu.kunb.meetingapp.reservation.gateway;

import hu.kunb.meetingapp.reservation.modell.MeetingReservation;

import java.time.LocalDateTime;
import java.util.Collection;

public interface PersistenceGateway {
    void storeReservation(MeetingReservation reservation);
    Collection<MeetingReservation> getAllReservations();
    MeetingReservation findByDateTime(LocalDateTime localDateTime);
    void deleteAll();
}
