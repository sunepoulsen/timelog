package dk.sunepoulsen.timelog.ui.tasks.backend.accounts;


import dk.sunepoulsen.timelog.backend.BackendConnection;
import dk.sunepoulsen.timelog.ui.model.accounts.AccountModel;
import dk.sunepoulsen.timelog.ui.tasks.backend.BackendConnectionTask;

/**
 * Task to create a new Account in a BackendConnection
 */
public class CreateAccountTask extends BackendConnectionTask<Void> {
    private final AccountModel model;

    public CreateAccountTask( BackendConnection connection, AccountModel model ) {
        super( connection );
        this.model = model;
    }

    @Override
    protected Void call() throws Exception {
        connection.servicesFactory().newAccountsService().create( model );
        return null;
    }
}
