package dk.sunepoulsen.timelog.backend.events;

import lombok.Data;

@Data
public class BackendConnectionEvents {
    private RegistrationSystemsEvents registrationSystems;

    public BackendConnectionEvents() {
        this.registrationSystems = new RegistrationSystemsEvents();
    }
}
