package hu.kunb.meetingapp.config;

import hu.kunb.inmemorypersistence.InMemoryPersistenceGateway;
import hu.kunb.meetingapp.reservation.boundary.RestBoundary;
import hu.kunb.meetingapp.reservation.gateway.PersistenceGateway;
import hu.kunb.meetingapp.reservation.interactor.MeetingReservationInteractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {
    @Bean
    public RestBoundary restBoundary() {
        return new MeetingReservationInteractor(inMemoryPersistenceGateway());
    }

    private PersistenceGateway inMemoryPersistenceGateway() {
        return new InMemoryPersistenceGateway();
    }
}
