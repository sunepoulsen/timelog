package dk.sunepoulsen.timelog.ui.mainwindow;

import dk.sunepoulsen.timelog.registry.Registry;
import dk.sunepoulsen.timelog.ui.dialogs.registration.systems.RegistrationSystemDialog;
import dk.sunepoulsen.timelog.ui.model.registration.systems.RegistrationSystemModel;
import dk.sunepoulsen.timelog.ui.tasks.backend.registration.systems.CreateRegistrationSystemTask;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.Setter;
import lombok.extern.slf4j.XSlf4j;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Consumer;

/**
 * Created by sunepoulsen on 09/05/2017.
 */
@XSlf4j
public class ActionPanel extends AnchorPane implements Initializable {
    @FXML
    private MenuBar menuBar = null;

    private Stage stage;
    private ResourceBundle bundle;

    private Registry registry;

    @Setter
    private Consumer<Task> onTaskCreated;

    public ActionPanel() {
        this.registry = Registry.getDefault();
        this.stage = this.registry.getUiRegistry().getStage();

        FXMLLoader fxmlLoader = new FXMLLoader( getClass().getResource( "actionpanel.fxml" ) );
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

    @Override
    public void initialize( URL location, ResourceBundle resources ) {
        log.info( "Initializing ActionPanel custom control" );

        bundle = resources;
        menuBar.setUseSystemMenuBar( true );
    }

    //-------------------------------------------------------------------------
    //              Event handlers
    //-------------------------------------------------------------------------

    /**
     * Handle action related to "File|New|Registration System" menu item.
     *
     * @param event Event on "File|New|Registration System" menu item.
     */
    @FXML
    private void newRegistrationSystem( final ActionEvent event ) {
        if( onTaskCreated == null ) {
            log.warn( "onTaskCreated is not initialized. Unable to create tasks for the UI." );
            return;
        }

        RegistrationSystemDialog dialog = new RegistrationSystemDialog();
        Optional<RegistrationSystemModel> model = dialog.showAndWait();

        model.ifPresent( registrationSystemModel -> {
            CreateRegistrationSystemTask task = new CreateRegistrationSystemTask( registry.getBackendConnection(), registrationSystemModel );
            onTaskCreated.accept( task );
        } );
    }

}
