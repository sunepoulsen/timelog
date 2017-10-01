package dk.sunepoulsen.timelog.ui.topcomponents.accounts;

import dk.sunepoulsen.timelog.backend.BackendConnection;
import dk.sunepoulsen.timelog.registry.Registry;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import lombok.extern.slf4j.XSlf4j;

import java.io.IOException;

@XSlf4j
public class AccountsPane extends BorderPane {
    private Registry registry;
    private BackendConnection backendConnection = null;

    public AccountsPane() {
        this.registry = Registry.getDefault();
        this.backendConnection = registry.getBackendConnection();

        FXMLLoader fxmlLoader = new FXMLLoader( getClass().getResource( "accountspane.fxml" ) );
        fxmlLoader.setRoot( this );
        fxmlLoader.setController( this );
        fxmlLoader.setResources( registry.getBundle( getClass() ) );

        try {
            fxmlLoader.load();
        }
        catch( IOException exception ) {
            throw new RuntimeException( exception );
        }
    }

    @FXML
    public void initialize() {
        log.info( "Initializing {} custom control", getClass().getSimpleName() );
    }
}
