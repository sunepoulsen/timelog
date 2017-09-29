package dk.sunepoulsen.timelog.ui.tasks.backend.registration.systems;


import dk.sunepoulsen.timelog.backend.BackendConnection;
import dk.sunepoulsen.timelog.ui.model.registration.systems.RegistrationSystemModel;
import dk.sunepoulsen.timelog.ui.tasks.backend.BackendConnectionTask;

/**
 * Task to create a new RegistrationSystem in a BackendConnection
 */
public class CreateRegistrationSystemTask extends BackendConnectionTask<Void> {
    private final RegistrationSystemModel model;

    public CreateRegistrationSystemTask( BackendConnection connection, RegistrationSystemModel model ) {
        super( connection );
        this.model = model;
    }

    @Override
    protected Void call() throws Exception {
        connection.servicesFactory().newRegistrationSystemsService().create( model );
        return null;
    }
}