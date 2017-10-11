package dk.sunepoulsen.timelog.backend.events;

import dk.sunepoulsen.timelog.events.ConsumerEvent;
import dk.sunepoulsen.timelog.ui.model.registration.systems.RegistrationSystemModel;
import lombok.Data;

import java.util.List;

@Data
public class RegistrationSystemsEvents {
    private ConsumerEvent<List<RegistrationSystemModel>> createdEvent;
    private ConsumerEvent<List<RegistrationSystemModel>> updatedEvent;
    private ConsumerEvent<List<RegistrationSystemModel>> deletedEvent;

    public RegistrationSystemsEvents() {
        this.createdEvent = new ConsumerEvent<>();
        this.updatedEvent = new ConsumerEvent<>();
        this.deletedEvent = new ConsumerEvent<>();
    }
}
