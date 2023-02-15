package hu.kunb.meetingapp.modell;

import hu.kunb.meetingapp.reservation.modell.MeetingReservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MeetingReservationTests {

    public static final String USER_NAME = "USER_NAME";
    private MeetingReservation meetingReservation;

    private LocalDateTime start = LocalDateTime.of(2023,1,1,9,0);
    private LocalDateTime end = LocalDateTime.of(2023,1,1,9,30);

    @BeforeEach
    void setUp(){
        meetingReservation = new MeetingReservation(start,end, USER_NAME);
    }

    @Test
    void toStringTest(){
        assertEquals(USER_NAME, meetingReservation.getUserName());
        assertEquals(start, meetingReservation.getStartTime());
        assertEquals(end, meetingReservation.getEndTime());
    }
}
