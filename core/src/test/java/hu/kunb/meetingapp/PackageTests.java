package hu.kunb.meetingapp;

import hu.kunb.meetingapp.apidefinition.spring.model.MeetingReservationRequestDto;
import hu.kunb.meetingapp.reservation.exception.ValidationException;
import hu.kunb.meetingapp.reservation.gateway.PersistenceGateway;
import hu.kunb.meetingapp.reservation.interactor.MeetingReservationInteractor;
import hu.kunb.meetingapp.reservation.modell.MeetingReservation;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PackageTests {

    private MeetingReservationInteractor interactor;

    @BeforeEach
    void setUp(){
        interactor = new MeetingReservationInteractor(new FakeGateway());
    }

    @Test
    void fourNonOverLappingReservationExist(){
        assertDoesNotThrow(() -> interactor.createReservation(createDto(9,0,11,0,"A")));
        assertDoesNotThrow(() -> interactor.createReservation(createDto(11,0,13,0,"B")));
        assertDoesNotThrow(() -> interactor.createReservation(createDto(13,0,15,0,"C")));
        assertDoesNotThrow(() -> interactor.createReservation(createDto(15,0,17,0,"D")));
        assertThrows(ValidationException.class, () -> interactor.createReservation(createDto(11,30,12,30,"E")));
    }

    private MeetingReservationRequestDto createDto(int startHour, int startMin, int endHour, int endMin, String userName) {
        return new MeetingReservationRequestDto()
                .day(LocalDate.of(2023, 1, 4))
                .startHour(startHour)
                .startMin(startMin)
                .endHour(endHour)
                .endMin(endMin)
                .username(userName);
    }

    class FakeGateway implements PersistenceGateway {

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

        @Override
        public void deleteAll() {
            db.clear();
        }
    }

}
