package dk.sunepoulsen.timelog.ui.tasks.backend;

import dk.sunepoulsen.timelog.backend.BackendConnection;
import javafx.concurrent.Task;

public abstract class BackendConnectionTask<T> extends Task<T> {
    protected final BackendConnection connection;

    public BackendConnectionTask( BackendConnection connection ) {
        this.connection = connection;
    }
}
