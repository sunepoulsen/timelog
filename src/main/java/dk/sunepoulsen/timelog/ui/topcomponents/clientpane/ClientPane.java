package dk.sunepoulsen.timelog.ui.topcomponents.clientpane;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import lombok.extern.slf4j.XSlf4j;

import java.io.IOException;

@XSlf4j
public class ClientPane extends AnchorPane {
    @FXML
    private StackPane stackPane;

    public ClientPane() {
        FXMLLoader fxmlLoader = new FXMLLoader( getClass().getResource( "clientpane.fxml" ) );
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
