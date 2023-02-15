package hu.kunb.meetingapp.reservation.interactor;

import hu.kunb.meetingapp.apidefinition.spring.model.MeetingReservationRequestDto;
import hu.kunb.meetingapp.reservation.exception.ValidationException;
import hu.kunb.meetingapp.reservation.gateway.PersistenceGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class MeetingReservationValidatorTest {

    private MeetingReservationValidator validator;
    private MeetingReservationRequestDto dto;

    @BeforeEach
    void setUp() {
        PersistenceGateway gateway = Mockito.mock(PersistenceGateway.class);
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
}