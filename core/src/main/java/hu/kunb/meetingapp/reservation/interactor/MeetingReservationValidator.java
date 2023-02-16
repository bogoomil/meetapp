package hu.kunb.meetingapp.reservation.interactor;

import hu.kunb.meetingapp.apidefinition.spring.model.MeetingReservationRequestDto;
import hu.kunb.meetingapp.reservation.exception.ValidationException;
import hu.kunb.meetingapp.reservation.gateway.PersistenceGateway;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class MeetingReservationValidator {

    PersistenceGateway gateway;
    MeetingReservationRequestDto dto;
    List<String> errors = new ArrayList<>();
    private List<Integer> validMins = Arrays.asList(0, 30);

    public MeetingReservationValidator(MeetingReservationRequestDto dto, PersistenceGateway gateway) {
        this.gateway = gateway;
        this.dto = dto;
    }

    public void validate() {
        validateDay();
        validateStartHour();
        validateEndHour();
        validateStartMin();
        validateEndMin();
        validateBusinessRules();
        nullCheck.accept(dto.getUsername(), ValidationException.USERNAME_CANNOT_BE_NULL);
        throwExceptionIfNecessary();
    }

    private void validateDay() {
        nullCheck.accept(dto.getDay(), ValidationException.DAY_CANNOT_BE_NULL);
        validateDayIsNotWeekend.accept(dto.getDay(), ValidationException.DAY_CANNOT_BE_WEEKEND);
    }

    private void validateStartHour() {
        nullCheck.accept(dto.getStartHour(), ValidationException.START_HOUR_CANNOT_BE_NULL);
        validateHourNotLessThan.accept(dto.getStartHour(), 9, ValidationException.START_HOUR_MUST_BE_GREATER_OR_EQUAL_9);
        validateHourNotGreaterThan.accept(dto.getStartHour(), 16, ValidationException.START_HOUR_MUST_BE_LESS_OR_EQUAL_16);
    }

    private void validateEndHour() {
        nullCheck.accept(dto.getEndHour(), ValidationException.END_HOUR_CANNOT_BE_NULL);
        validateHourNotLessThan.accept(dto.getEndHour(), 9, ValidationException.END_HOUR_MUST_BE_GREATER_OR_EQUAL_9);
        validateHourNotGreaterThan.accept(dto.getEndHour(), 17, ValidationException.END_HOUR_MUST_BE_LESS_OR_EQUAL_17);
    }

    private void validateStartMin() {
        nullCheck.accept(dto.getStartMin(), ValidationException.START_MIN_CANNOT_BE_NULL);
        validateMinuteIsZeroOrThirty.accept(dto.getStartMin(), ValidationException.START_MIN_MUST_BE_0_OR_30);
    }

    private void validateEndMin() {
        nullCheck.accept(dto.getEndMin(), ValidationException.END_MIN_CANNOT_BE_NULL);
        validateMinuteIsZeroOrThirty.accept(dto.getEndMin(), ValidationException.END_MIN_MUST_BE_0_OR_30);
    }

    private void validateBusinessRules() {
        if(errors.isEmpty()){
            LocalDateTime start = LocalDateTime.of(dto.getDay().getYear(), dto.getDay().getMonth(), dto.getDay().getDayOfMonth(), dto.getStartHour(), dto.getStartMin());
            LocalDateTime end = LocalDateTime.of(dto.getDay().getYear(), dto.getDay().getMonth(), dto.getDay().getDayOfMonth(), dto.getEndHour(), dto.getEndMin());
            validateEndDateNotBeforeStartDate.accept(start, end);
            validateMeetingLengthNotLongerThan.accept(start, end);
            validateEndTimeIsNotGreaterThan1700.accept(end);
        }
    }

    private void throwExceptionIfNecessary() {
        if (!errors.isEmpty()) {
            ValidationException validationException = new ValidationException();
            validationException.getErrors().addAll(errors);
            throw validationException;
        }
    }

    @FunctionalInterface
    interface TriConsumer<P1, P2, P3> {
        void accept(P1 p1, P2 p2, P3 p3);
    }

    BiConsumer<Object, String> nullCheck = (o, s) -> {
        if(o == null){
            errors.add(s);
        }
    };
    BiConsumer<Integer, String> validateMinuteIsZeroOrThirty = (i, s) -> {
        if(i != null && !validMins.contains(i)){
            errors.add(s);
        }
    };
    BiConsumer<LocalDate, String> validateDayIsNotWeekend = (d, s) -> {
        if(d != null && (d.getDayOfWeek() == DayOfWeek.SATURDAY || d.getDayOfWeek() == DayOfWeek.SUNDAY)){
            errors.add(s);
        }
    };

    TriConsumer<Integer, Integer, String> validateHourNotLessThan = (hour, value, msg) -> {
        if(hour != null && hour < value) errors.add(msg);
    };

    TriConsumer<Integer, Integer, String> validateHourNotGreaterThan = (hour, value, msg) -> {
        if(hour != null && hour > value) errors.add(msg);
    };

    BiConsumer<LocalDateTime, LocalDateTime> validateEndDateNotBeforeStartDate = (start, end) -> {
        if(!start.isBefore(end)){
            errors.add(ValidationException.START_TIME_MUST_BE_BEFORE_END_TIME);
        }
    };

    BiConsumer<LocalDateTime, LocalDateTime> validateMeetingLengthNotLongerThan = (start, end) -> {
      if(start.plusHours(3).isBefore(end)){
          errors.add(ValidationException.MAXIMUM_MEETING_LENGTH_IS_3_HOURS);
      }
    };

    Consumer<LocalDateTime> validateEndTimeIsNotGreaterThan1700 = end -> {
        if (end.getHour() == 17 && end.getMinute() == 30) {
            errors.add(ValidationException.MEETING_CANNOT_BE_OPEN_AFTER_1700);
        }
    };
}
