package hu.kunb.meetingapp.reservation.interactor;

import hu.kunb.meetingapp.apidefinition.spring.model.MeetingReservationRequestDto;
import hu.kunb.meetingapp.reservation.exception.ValidationException;
import hu.kunb.meetingapp.reservation.gateway.PersistenceGateway;
import hu.kunb.meetingapp.reservation.modell.MeetingReservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MeetingReservationValidatorTest {

    public static final String USER_NAME = "USER_NAME";
    private MeetingReservationValidator validator;
    private MeetingReservationRequestDto dto;

    @BeforeEach
    void setUp() {

        List<MeetingReservation> reservations = new ArrayList<>();
        MeetingReservation reservation = new MeetingReservation(
                LocalDateTime.of(2023, 1, 4, 10, 0),
                LocalDateTime.of(2023, 1, 4, 12, 0),
                USER_NAME
        );
        reservations.add(reservation);

        PersistenceGateway gateway = Mockito.mock(PersistenceGateway.class);
        Mockito.when(gateway.getAllReservations()).thenReturn(reservations);

        dto = new MeetingReservationRequestDto();
        validator = new MeetingReservationValidator(dto, gateway);

    }

    @Test
    void validateUsername() {
        ValidationException validationException = assertThrows(ValidationException.class, () -> validator.validate());
        assertTrue(validationException.getErrors().contains(ValidationException.USERNAME_CANNOT_BE_NULL));
    }

    @Test
    void validateDayNotNull() {
        ValidationException validationException = assertThrows(ValidationException.class, () -> validator.validate());
        assertTrue(validationException.getErrors().contains(ValidationException.DAY_CANNOT_BE_NULL));
    }

    @Test
    void validateDayNotSaturday() {
        dto.setDay(LocalDate.of(2023, 1, 7));
        ValidationException validationException = assertThrows(ValidationException.class, () -> validator.validate());
        assertTrue(validationException.getErrors().contains(ValidationException.DAY_CANNOT_BE_WEEKEND));
    }

    @Test
    void validateDayNotSunday() {
        dto.setDay(LocalDate.of(2023, 1, 8));
        ValidationException validationException = assertThrows(ValidationException.class, () -> validator.validate());
        assertTrue(validationException.getErrors().contains(ValidationException.DAY_CANNOT_BE_WEEKEND));
    }

    @Test
    void startHourNotNull() {
        ValidationException validationException = assertThrows(ValidationException.class, () -> validator.validate());
        assertTrue(validationException.getErrors().contains(ValidationException.START_HOUR_CANNOT_BE_NULL));
    }

    @Test
    void startHourGreaterOrEqual9() {
        dto.setStartHour(8);
        ValidationException validationException = assertThrows(ValidationException.class, () -> validator.validate());
        assertTrue(validationException.getErrors().contains(ValidationException.START_HOUR_MUST_BE_GREATER_OR_EQUAL_9));
    }

    @Test
    void startHourEqual9() {
        dto.setStartHour(9);
        ValidationException exception = assertThrows(ValidationException.class, () -> validator.validate());
        assertFalse(exception.getErrors().contains(ValidationException.START_HOUR_MUST_BE_GREATER_OR_EQUAL_9));
    }

    @Test
    void startHourEqual16() {
        dto.setStartHour(16);
        ValidationException exception = assertThrows(ValidationException.class, () -> validator.validate());
        assertFalse(exception.getErrors().contains(ValidationException.START_HOUR_MUST_BE_LESS_OR_EQUAL_16));
    }

    @Test
    void startHourGreaterThan16() {
        dto.setStartHour(18);
        ValidationException exception = assertThrows(ValidationException.class, () -> validator.validate());
        assertTrue(exception.getErrors().contains(ValidationException.START_HOUR_MUST_BE_LESS_OR_EQUAL_16));
    }

    @Test
    void endHourNotNull() {
        ValidationException validationException = assertThrows(ValidationException.class, () -> validator.validate());
        assertTrue(validationException.getErrors().contains(ValidationException.END_HOUR_CANNOT_BE_NULL));
    }

    @Test
    void endHourGreaterOrEqual9() {
        dto.setEndHour(8);
        ValidationException validationException = assertThrows(ValidationException.class, () -> validator.validate());
        assertTrue(validationException.getErrors().contains(ValidationException.END_HOUR_MUST_BE_GREATER_OR_EQUAL_9));
    }

    @Test
    void endHourEqual9() {
        dto.setEndHour(9);
        ValidationException exception = assertThrows(ValidationException.class, () -> validator.validate());
        assertFalse(exception.getErrors().contains(ValidationException.END_HOUR_MUST_BE_GREATER_OR_EQUAL_9));
    }

    @Test
    void endHourGreaterThan17() {
        dto.setEndHour(18);
        ValidationException exception = assertThrows(ValidationException.class, () -> validator.validate());
        assertTrue(exception.getErrors().contains(ValidationException.END_HOUR_MUST_BE_LESS_OR_EQUAL_17));
    }

    @Test
    void endHourEqual17() {
        dto.setEndHour(17);
        ValidationException exception = assertThrows(ValidationException.class, () -> validator.validate());
        assertFalse(exception.getErrors().contains(ValidationException.END_HOUR_MUST_BE_LESS_OR_EQUAL_17));
    }

    @Test
    void startMinNotNull() {
        ValidationException exception = assertThrows(ValidationException.class, () -> validator.validate());
        assertTrue(exception.getErrors().contains(ValidationException.START_MIN_CANNOT_BE_NULL));

    }

    @Test
    void startMinNotValid() {
        dto.setStartMin(23);
        ValidationException exception = assertThrows(ValidationException.class, () -> validator.validate());
        assertTrue(exception.getErrors().contains(ValidationException.START_MIN_MUST_BE_0_OR_30));

    }

    @Test
    void endMinNotNull() {
        ValidationException exception = assertThrows(ValidationException.class, () -> validator.validate());
        assertTrue(exception.getErrors().contains(ValidationException.END_MIN_CANNOT_BE_NULL));

    }

    @Test
    void endMinNotValid() {
        dto.setEndMin(23);
        ValidationException exception = assertThrows(ValidationException.class, () -> validator.validate());
        assertTrue(exception.getErrors().contains(ValidationException.END_MIN_MUST_BE_0_OR_30));
    }

    @Test
    void startTimeAfterEndTime() {
        dto.setDay(LocalDate.now());
        dto.setStartHour(12);
        dto.setStartMin(0);
        dto.setEndHour(11);
        dto.setEndMin(0);
        ValidationException exception = assertThrows(ValidationException.class, () -> validator.validate());
        assertTrue(exception.getErrors().contains(ValidationException.START_TIME_MUST_BE_BEFORE_END_TIME));
    }

    @Test
    void startMeetingLengthTooLong() {
        dto.setDay(LocalDate.now());
        dto.setStartHour(9);
        dto.setStartMin(0);
        dto.setEndHour(13);
        dto.setEndMin(0);
        ValidationException exception = assertThrows(ValidationException.class, () -> validator.validate());
        assertTrue(exception.getErrors().contains(ValidationException.MAXIMUM_MEETING_LENGTH_IS_3_HOURS));
    }

    @Test
    void startMeetingOpenAfter17() {
        dto.setDay(LocalDate.now());
        dto.setStartHour(16);
        dto.setStartMin(0);
        dto.setEndHour(17);
        dto.setEndMin(30);
        ValidationException exception = assertThrows(ValidationException.class, () -> validator.validate());
        assertTrue(exception.getErrors().contains(ValidationException.MEETING_CANNOT_BE_OPEN_AFTER_1700));
    }

    @Test
    void testOverLappingReservationsStartTimeEquals() {
        initDto(10, 0, 13, 0);
        ValidationException exception = assertThrows(ValidationException.class, () -> validator.validate());
        assertTrue(exception.getErrors().contains(ValidationException.RESERVATIONS_CANNOT_BE_OVERLAPPING));
    }

    @Test
    void testOverLappingReservationsStartTimeGreater() {
        initDto(11, 0, 13, 0);
        ValidationException exception = assertThrows(ValidationException.class, () -> validator.validate());
        assertTrue(exception.getErrors().contains(ValidationException.RESERVATIONS_CANNOT_BE_OVERLAPPING));
    }

    @Test
    void testOverLappingReservationsEndTimeEquals() {
        initDto(9, 0, 12, 0);
        ValidationException exception = assertThrows(ValidationException.class, () -> validator.validate());
        assertTrue(exception.getErrors().contains(ValidationException.RESERVATIONS_CANNOT_BE_OVERLAPPING));
    }

    @Test
    void testOverLappingReservationsEndTimeLess() {
        initDto(9, 0, 11, 30);
        ValidationException exception = assertThrows(ValidationException.class, () -> validator.validate());
        assertTrue(exception.getErrors().contains(ValidationException.RESERVATIONS_CANNOT_BE_OVERLAPPING));
    }

    @Test
    void testOverLappingReservations() {
        initDto(15, 0, 16, 0);
        assertDoesNotThrow(() -> validator.validate());
    }

    private void initDto(int startHour, int startMin, int endHour, int endMin) {
        dto.setDay(LocalDate.of(2023, 1, 4));
        dto.setStartHour(startHour);
        dto.setStartMin(startMin);
        dto.setEndHour(endHour);
        dto.setEndMin(endMin);
        dto.setUsername(USER_NAME);
    }
}