package dk.sunepoulsen.timelog.backend;

import dk.sunepoulsen.timelog.backend.services.ServicesFactory;
import dk.sunepoulsen.timelog.db.storage.DatabaseStorage;
import liquibase.exception.LiquibaseException;
import lombok.extern.slf4j.XSlf4j;

import java.io.IOException;
import java.sql.SQLException;

/**
 * This class is the main entry to an Accounting Project
 *
 * <h3>Public interface</h3>
 *
 * From a public point of view this class has the following responsibilities:
 * <ul>
 *     <li>Define properties that can identify a connection on desk</li>
 *     <li>
 *         Provide factory methods to create service classes that can do operations on an Accounting Project.
 *     </li>
 * </ul>
 *
 * <h3>Implementation</h3>
 *
 *
 *
 */
@XSlf4j
public class BackendConnection {
    private DatabaseStorage database;

    public BackendConnection() {
        this.database = new DatabaseStorage();
    }

    public void connect() throws BackendConnectionException {
        try {
            this.database.migrate();
            this.database.connect();
        }
        catch( IOException | SQLException | LiquibaseException ex ) {
            throw new BackendConnectionException( "Unable to connect to backend database", ex );
        }
    }

    public void disconnect() {
        this.database.disconnect();
    }

    public boolean isOpen() {
        return database.isOpen();
    }

    public ServicesFactory servicesFactory() {
        return new ServicesFactory( this.database );
    }
}