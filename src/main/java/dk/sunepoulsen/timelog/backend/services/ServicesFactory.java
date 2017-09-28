package dk.sunepoulsen.timelog.backend.services;

import dk.sunepoulsen.timelog.db.storage.DatabaseStorage;

/**
 * Factory class to create service instances for an BackendConnection to access and update
 * the different concepts that makes an BackendConnection.
 */
public class ServicesFactory {
    private final DatabaseStorage database;

    public ServicesFactory( final DatabaseStorage database ) {
        this.database = database;
    }
}
