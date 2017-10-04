package dk.sunepoulsen.timelog.backend.events;

import lombok.Data;

@Data
public class BackendConnectionEvents {
    private AccountsEvents accounts;
    private RegistrationSystemsEvents registrationSystems;

    public BackendConnectionEvents() {
        this.accounts = new AccountsEvents();
        this.registrationSystems = new RegistrationSystemsEvents();
    }
}
