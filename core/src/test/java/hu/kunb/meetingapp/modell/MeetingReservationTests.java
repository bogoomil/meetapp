package hu.kunb.meetingapp.modell;

import hu.kunb.meetingapp.reservation.modell.MeetingReservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MeetingReservationTests {

    public static final String TO_STRING = "MeetingReservation{day=+999999999-12-31, startHour=1, startMinute=30, endHour=2, endMinute=0}";
    private MeetingReservation meetingReservation;

    @BeforeEach
    void setUp(){
        meetingReservation = new MeetingReservation();
        meetingReservation.day = LocalDate.MAX;
        meetingReservation.startHour = 1;
        meetingReservation.startMinute = 30;
        meetingReservation.endHour = 2;
        meetingReservation.endMinute = 0;
    }

    @Test
    void toStringTest(){
        assertEquals(TO_STRING, meetingReservation.toString());
    }
}
