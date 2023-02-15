package hu.kunb.meetingapp.reservation.exception;

import java.util.ArrayList;
import java.util.List;

public class ValidationException extends RuntimeException{

    public static final String USERNAME_CANNOT_BE_NULL = "username cannot be null";
    public static final String DAY_CANNOT_BE_NULL = "day cannot be null";
    public static final String DAY_CANNOT_BE_WEEKEND = "day cannot be weekend";
    public static final String START_HOUR_CANNOT_BE_NULL = "start hour cannot be null";
    public static final String START_HOUR_MUST_BE_GREATER_OR_EQUAL_9 = "start hour must be greater or equal 9";
    public static final String START_HOUR_MUST_BE_LESS_OR_EQUAL_16 = "start hour must be less or equal 16";
    public static final String END_HOUR_CANNOT_BE_NULL = "end hour cannot be null";
    public static final String END_HOUR_MUST_BE_GREATER_OR_EQUAL_9 = "end hour must be greater or equal 9";
    public static final String END_HOUR_MUST_BE_LESS_OR_EQUAL_17 = "end hour must be less or equal 1=";
    public static final String START_MIN_CANNOT_BE_NULL = "start minute cannot be null";
    public static final String START_MIN_MUST_BE_0_OR_30 = "start min must be 0 or 30";
    public static final String END_MIN_CANNOT_BE_NULL = "end minute cannot be null";
    public static final String END_MIN_MUST_BE_0_OR_30 = "end min must be 0 or 30";
    public static final String START_TIME_MUST_BE_BEFORE_END_TIME = "start time must be before end time";
    public static final String MAXIMUM_MEETING_LENGTH_IS_3_HOURS = "maximum meeting length is 3 hours";
    public static final String MEETING_CANNOT_BE_OPEN_AFTER_1700 = "meeting cannot be open after 17:00";


    private List<String> errors = new ArrayList<>();

    public List<String> getErrors() {
        return errors;
    }
}
