package hu.kunb.meetingapp.reservation.interactor;

import hu.kunb.meetingapp.apidefinition.spring.model.MeetingReservationRequestDto;
import hu.kunb.meetingapp.reservation.boundary.RestBoundary;
import hu.kunb.meetingapp.reservation.gateway.PersistenceGateway;
import hu.kunb.meetingapp.reservation.modell.MeetingReservation;

public class MeetingReservationInteractor implements RestBoundary {

    private PersistenceGateway gateway;

    public MeetingReservationInteractor(PersistenceGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public boolean createReservation(MeetingReservationRequestDto dto) {

        MeetingReservation reservation = new MeetingReservationConverter(dto).convert();
        return false;
    }
}
