package hu.kunb.meetingapp.reservation.interactor;

import hu.kunb.meetingapp.apidefinition.spring.model.MeetingReservationRequestDto;
import hu.kunb.meetingapp.reservation.boundary.RestBoundary;
import hu.kunb.meetingapp.reservation.gateway.PersistenceGateway;
import hu.kunb.meetingapp.reservation.modell.MeetingReservation;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

public class MeetingReservationInteractor implements RestBoundary {

    private PersistenceGateway gateway;

    public MeetingReservationInteractor(PersistenceGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public MeetingReservation createReservation(MeetingReservationRequestDto dto) {
        MeetingReservationValidator validator = new MeetingReservationValidator(dto, gateway);
        validator.validate();
        MeetingReservation reservation = new MeetingReservationConverter(dto).convert();
        gateway.storeReservation(reservation);
        return reservation;
    }

    @Override
    public Collection<MeetingReservation> getAll() {
        return gateway.getAllReservations();
    }

    @Override
    public Optional<MeetingReservation> findByDateTime(LocalDateTime dateTime) {
        return gateway.getAllReservations()
                .stream()
                .filter(reservation -> dateTime.isEqual(reservation.getStartTime()))
                .findAny();
    }

    @Override
    public void deleteAll() {
        gateway.deleteAll();
    }
}
