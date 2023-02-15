package hu.kunb.meetingapp.reservation.interactor;

import hu.kunb.meetingapp.apidefinition.spring.model.MeetingReservationRequestDto;
import hu.kunb.meetingapp.reservation.modell.MeetingReservation;

import java.time.LocalDateTime;

public class MeetingReservationConverter {
    MeetingReservationRequestDto dto;
    public MeetingReservationConverter(MeetingReservationRequestDto dto) {
        this.dto = dto;
    }

    public MeetingReservation convert() {
        LocalDateTime start = LocalDateTime.of(dto.getDay().getYear(), dto.getDay().getMonth(), dto.getDay().getDayOfMonth(), dto.getStartHour(), dto.getStartMin());
        LocalDateTime end = LocalDateTime.of(dto.getDay().getYear(), dto.getDay().getMonth(), dto.getDay().getDayOfMonth(), dto.getEndHour(), dto.getEndMin());
        return new MeetingReservation(start, end, dto.getUsername());
    }
}
