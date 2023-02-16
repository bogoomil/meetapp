package hu.kunb.inmemorypersistence;

import hu.kunb.meetingapp.reservation.gateway.PersistenceGateway;
import hu.kunb.meetingapp.reservation.modell.MeetingReservation;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class InMemoryPersistenceGateway implements PersistenceGateway {

    Map<LocalDateTime, MeetingReservation> db = new HashMap<>();

    @Override
    public void storeReservation(MeetingReservation reservation) {
        db.put(reservation.getStartTime(), reservation);
    }

    @Override
    public Collection<MeetingReservation> getAllReservations() {
        return db.values();
    }

    @Override
    public MeetingReservation findByDateTime(LocalDateTime localDateTime) {
        return db.get(localDateTime);
    }
}
