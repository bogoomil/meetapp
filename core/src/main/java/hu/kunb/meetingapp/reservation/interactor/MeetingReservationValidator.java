package hu.kunb.meetingapp.reservation.interactor;

import hu.kunb.meetingapp.apidefinition.spring.model.MeetingReservationRequestDto;
import hu.kunb.meetingapp.reservation.exception.ValidationException;
import hu.kunb.meetingapp.reservation.gateway.PersistenceGateway;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MeetingReservationValidator {

    PersistenceGateway gateway;
    MeetingReservationRequestDto dto;
    List<String> errors = new ArrayList<>();

    public MeetingReservationValidator(MeetingReservationRequestDto dto, PersistenceGateway gateway) {
        this.gateway = gateway;
        this.dto = dto;
    }

    private List<Integer> validMins = Arrays.asList(0, 30);

    void validate() {
        validateDay();
        validateStartHour();
        validateEndHour();
        validateStartMin();
        validateEndMin();
        if (errors.isEmpty()) {
            validateBusinessRules();
        }
        validateUsername();
        throwExceptionIfNecessary();
    }

    private void throwExceptionIfNecessary() {
        if (!errors.isEmpty()) {
            ValidationException validationException = new ValidationException();
            validationException.getErrors().addAll(errors);
            throw validationException;
        }
    }

    private void validateBusinessRules() {
        LocalDateTime start = LocalDateTime.of(dto.getDay().getYear(), dto.getDay().getMonth(), dto.getDay().getDayOfMonth(), dto.getStartHour(), dto.getStartMin());
        LocalDateTime end = LocalDateTime.of(dto.getDay().getYear(), dto.getDay().getMonth(), dto.getDay().getDayOfMonth(), dto.getEndHour(), dto.getEndMin());

        if (!start.isBefore(end)) {
            errors.add(ValidationException.START_TIME_MUST_BE_BEFORE_END_TIME);
        }

        if (start.plusHours(3).isBefore(end)) {
            errors.add(ValidationException.MAXIMUM_MEETING_LENGTH_IS_3_HOURS);
        }

        if (end.getHour() == 17 && end.getMinute() == 30) {
            errors.add(ValidationException.MEETING_CANNOT_BE_OPEN_AFTER_1700);
        }

    }

    private void validateUsername() {
        if (dto.getUsername() == null) {
            errors.add(ValidationException.USERNAME_CANNOT_BE_NULL);
        }
    }

    private void validateEndMin() {
        if (dto.getEndMin() == null) {
            errors.add(ValidationException.END_MIN_CANNOT_BE_NULL);
        } else if (!validMins.contains(dto.getEndMin())) {
            errors.add(ValidationException.END_MIN_MUST_BE_0_OR_30);
        }
    }

    private void validateStartMin() {
        if (dto.getStartMin() == null) {
            errors.add(ValidationException.START_MIN_CANNOT_BE_NULL);
        } else if (!validMins.contains(dto.getStartMin())) {
            errors.add(ValidationException.START_MIN_MUST_BE_0_OR_30);
        }
    }

    private void validateEndHour() {
        if (dto.getEndHour() == null) {
            errors.add(ValidationException.END_HOUR_CANNOT_BE_NULL);
        } else if (dto.getEndHour() < 9) {
            errors.add(ValidationException.END_HOUR_MUST_BE_GREATER_OR_EQUAL_9);
        } else if (dto.getEndHour() > 17) {
            errors.add(ValidationException.END_HOUR_MUST_BE_LESS_OR_EQUAL_17);
        }
    }

    private void validateStartHour() {
        if (dto.getStartHour() == null) {
            errors.add(ValidationException.START_HOUR_CANNOT_BE_NULL);
        } else if (dto.getStartHour() < 9) {
            errors.add(ValidationException.START_HOUR_MUST_BE_GREATER_OR_EQUAL_9);
        } else if (dto.getStartHour() > 16) {
            errors.add(ValidationException.START_HOUR_MUST_BE_LESS_OR_EQUAL_16);
        }
    }

    private void validateDay() {
        if (dto.getDay() == null) {
            errors.add(ValidationException.DAY_CANNOT_BE_NULL);
        } else if (dto.getDay().getDayOfWeek() == DayOfWeek.SATURDAY || dto.getDay().getDayOfWeek() == DayOfWeek.SUNDAY) {
            errors.add(ValidationException.DAY_CANNOT_BE_WEEKEND);
        }
    }
}
