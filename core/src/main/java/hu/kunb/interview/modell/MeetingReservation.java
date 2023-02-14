package hu.kunb.interview.modell;

import java.time.LocalDate;

public class MeetingReservation {
    public LocalDate day;
    public Integer startHour;
    public Integer startMinute;
    public Integer endHour;
    public Integer endMinute;

    @Override
    public String toString() {
        return "MeetingReservation{" +
                "day=" + day +
                ", startHour=" + startHour +
                ", startMinute=" + startMinute +
                ", endHour=" + endHour +
                ", endMinute=" + endMinute +
                '}';
    }
}
