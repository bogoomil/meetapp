package hu.kunb.inmemorypersistence;

import hu.kunb.meetingapp.reservation.modell.MeetingReservation;
import junit.framework.TestCase;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryPersistenceGatewayTest {

    private InMemoryPersistenceGateway gateway;
    private MeetingReservation reservation;

    @BeforeEach
    public void setUp() throws Exception {
        reservation = new EasyRandom().nextObject(MeetingReservation.class);
        gateway = new InMemoryPersistenceGateway();
        gateway.storeReservation(reservation);
    }

    @Test
    public void testStoreReservation() {
        assertFalse(gateway.getAllReservations().isEmpty());
    }

    @Test
    public void testGetAllReservations() {
        Collection<MeetingReservation> reservations = gateway.getAllReservations();
        assertEquals(1, reservations.size());
    }

    @Test
    public void testFindByDateTime() {
        MeetingReservation r = gateway.findByDateTime(reservation.getStartTime());
        assertNotNull(r);
    }
}