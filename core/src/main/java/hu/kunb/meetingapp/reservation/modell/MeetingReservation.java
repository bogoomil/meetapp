package hu.kunb.meetingapp.reservation.modell;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class MeetingReservation {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String userName;

    public MeetingReservation(LocalDateTime startTime, LocalDateTime endTime, String userName) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.userName = userName;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public String getUserName() {
        return userName;
    }
}
