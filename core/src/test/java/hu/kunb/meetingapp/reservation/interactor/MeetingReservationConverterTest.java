package hu.kunb.meetingapp.reservation.interactor;

import hu.kunb.meetingapp.apidefinition.spring.model.MeetingReservationRequestDto;
import hu.kunb.meetingapp.reservation.modell.MeetingReservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class MeetingReservationConverterTest {

    public static final String USER_NAME = "USER_NAME";
    private MeetingReservationConverter converter;
    private LocalDateTime start = LocalDateTime.of(2023,1,1,9,0);
    private LocalDateTime end = LocalDateTime.of(2023,1,1,9,30);


    @BeforeEach
    void setUp() {
        MeetingReservationRequestDto dto = new MeetingReservationRequestDto()
                .day(LocalDate.of(2023,1,1))
                .startHour(9)
                .startMin(0)
                .endHour(9)
                .endMin(30)
                .username(USER_NAME);
        converter = new MeetingReservationConverter(dto);
    }

    @Test
    void convert() {
        MeetingReservation reservation = converter.convert();
        assertEquals(start, reservation.getStartTime());
        assertEquals(end, reservation.getEndTime());
        assertEquals(USER_NAME, reservation.getUserName());
    }
}