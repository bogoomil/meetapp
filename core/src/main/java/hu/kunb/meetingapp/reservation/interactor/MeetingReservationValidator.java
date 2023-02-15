package hu.kunb.meetingapp.reservation.interactor;

import hu.kunb.meetingapp.apidefinition.spring.model.MeetingReservationRequestDto;
import hu.kunb.meetingapp.reservation.exception.ReservationException;
import hu.kunb.meetingapp.reservation.gateway.PersistenceGateway;

import java.util.Arrays;
import java.util.List;

public class MeetingReservationValidator {

    PersistenceGateway gateway;

    public MeetingReservationValidator(PersistenceGateway gateway) {
        this.gateway = gateway;
    }

    private List<Integer> validMins= Arrays.asList(0,30);
    void validate(MeetingReservationRequestDto dto){
        if (!validMins.contains(dto.getEndMin())){
            throw new ReservationException("end min must be 0 or 30");
        }
        if (!validMins.contains(dto.getStartMin())){
            throw new ReservationException("start min must be 0 or 30");
        }
    }
}
