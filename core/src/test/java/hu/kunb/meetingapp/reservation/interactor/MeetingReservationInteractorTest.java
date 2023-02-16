package hu.kunb.meetingapp.reservation.interactor;

import hu.kunb.meetingapp.apidefinition.spring.model.MeetingReservationRequestDto;
import hu.kunb.meetingapp.reservation.exception.ValidationException;
import hu.kunb.meetingapp.reservation.gateway.PersistenceGateway;
import hu.kunb.meetingapp.reservation.modell.MeetingReservation;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class MeetingReservationInteractorTest {

    private MeetingReservationRequestDto dto;
    private MeetingReservationInteractor interactor;
    private PersistenceGateway gateway;

    @BeforeEach
    public void setUp() throws Exception {
        dto = new MeetingReservationRequestDto()
                .username("USER_NAME")
                .day(LocalDate.of(2023, 1, 4))
                .startHour(9)
                .startMin(0)
                .endHour(10)
                .endMin(0);
        gateway = Mockito.mock(PersistenceGateway.class);
        interactor = new MeetingReservationInteractor(gateway);
    }

    @Test
    void testCreateReservation() {
        assertNotNull(interactor.createReservation(dto));
        Mockito.verify(gateway).storeReservation(Mockito.any());
    }

    @Test
    void testValidatorIsCalled() {
        dto.setUsername(null);
        assertThrows(ValidationException.class, () -> interactor.createReservation(dto));
    }

    @Test
    void listAll() {
        Mockito.when(gateway.getAllReservations()).thenReturn(Arrays.asList(new EasyRandom().nextObject(MeetingReservation.class)));
        assertTrue(interactor.getAll().size() == 1);
    }

    @Test
    void findByDateTime() {
        LocalDateTime start = LocalDateTime.of(2023, 1, 4, 10, 30);
        MeetingReservation reservation = new MeetingReservation(
                start,
                LocalDateTime.of(2023, 01, 04, 12, 30),
                "user");

        Mockito.when(gateway.getAllReservations()).thenReturn(Arrays.asList(reservation));
        assertTrue(interactor.findByDateTime(start).isPresent());
        assertTrue(interactor.findByDateTime(LocalDateTime.MAX).isEmpty());
    }
}