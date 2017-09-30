package dk.sunepoulsen.timelog.ui.topcomponents.registration.systems;

import dk.sunepoulsen.timelog.backend.BackendConnection;
import dk.sunepoulsen.timelog.registry.Registry;
import dk.sunepoulsen.timelog.ui.model.registration.systems.RegistrationSystemModel;
import dk.sunepoulsen.timelog.ui.tasks.backend.LoadBackendServiceItemsTask;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import lombok.extern.slf4j.XSlf4j;

import java.io.IOException;

@XSlf4j
public class RegistrationSystemsPane extends AnchorPane {
    @FXML
    private TableView<RegistrationSystemModel> viewer;

    @FXML
    private Region veil = null;

    @FXML
    private ProgressIndicator progressIndicator = null;

    private BackendConnection backendConnection = null;

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

        backendConnection = Registry.getDefault().getBackendConnection();
        backendConnection.getEvents().getRegistrationSystems().getCreated().addListener( ( observable, oldValue, newValue ) -> reload() );

        reload();
    }

    private void reload() {
        LoadBackendServiceItemsTask<RegistrationSystemModel> task = new LoadBackendServiceItemsTask<>( backendConnection,
            connection -> connection.servicesFactory().newRegistrationSystemsService().findAll()
        );

        progressIndicator.progressProperty().bind( task.progressProperty() );
        veil.visibleProperty().bind( task.runningProperty() );
        progressIndicator.visibleProperty().bind( task.runningProperty() );

        task.setOnSucceeded( event -> {
            ObservableList<RegistrationSystemModel> movies = task.getValue();

            log.info( "Viewing {} registration systems", movies.size() );
            viewer.setItems( movies );
        } );

        log.info( "Loading registration systems" );
        new Thread( task ).start();
    }
}
