package dk.sunepoulsen.timelog.backend.services;

import dk.sunepoulsen.timelog.backend.events.BackendConnectionEvents;
import dk.sunepoulsen.timelog.db.storage.DatabaseStorage;

/**
 * Factory class to create service instances for an BackendConnection to access and update
 * the different concepts that makes an BackendConnection.
 */
public class ServicesFactory {
    private final BackendConnectionEvents backendConnectionEvents;
    private final DatabaseStorage database;

    public ServicesFactory( final BackendConnectionEvents backendConnectionEvents, final DatabaseStorage database ) {
        this.backendConnectionEvents = backendConnectionEvents;
        this.database = database;
    }

    public RegistrationSystemsService newRegistrationSystemsService() {
        return new RegistrationSystemsService( this.backendConnectionEvents.getRegistrationSystems(), this.database );
    }
}
