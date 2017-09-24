package dk.sunepoulsen.timelog.ui.topcomponents.registration.systems.viewer;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import lombok.extern.slf4j.XSlf4j;

import java.io.IOException;

@XSlf4j
public class RegistrationSystemsPane extends AnchorPane {
    public RegistrationSystemsPane() {
        FXMLLoader fxmlLoader = new FXMLLoader( getClass().getResource( "viewer.fxml" ) );
        fxmlLoader.setRoot( this );
        fxmlLoader.setController( this );

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
