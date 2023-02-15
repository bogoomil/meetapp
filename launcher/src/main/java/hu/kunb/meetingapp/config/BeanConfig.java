package hu.kunb.meetingapp.config;

import hu.kunb.meetingapp.reservation.boundary.RestBoundary;
import hu.kunb.meetingapp.reservation.gateway.PersistenceGateway;
import hu.kunb.meetingapp.reservation.interactor.MeetingReservationInteractor;
import hu.kunb.meetingapp.reservation.modell.MeetingReservation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.List;

@Configuration
public class BeanConfig {
    @Bean
    public RestBoundary restBoundary(){
        return new MeetingReservationInteractor(inMemoryPersistenceGateway());
    }

    private PersistenceGateway inMemoryPersistenceGateway() {
        return new PersistenceGateway() {
            @Override
            public void storeReservation(MeetingReservation reservation) {

            }

            @Override
            public List<MeetingReservation> getAllReservations() {
                return null;
            }

            @Override
            public MeetingReservation findByDateTime(LocalDateTime localDateTime) {
                return null;
            }

            @Override
            public void deleteReservation(LocalDateTime localDateTime) {

            }
        };
    }
}
