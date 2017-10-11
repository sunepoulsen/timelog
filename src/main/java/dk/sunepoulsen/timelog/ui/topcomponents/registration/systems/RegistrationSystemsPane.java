package dk.sunepoulsen.timelog.ui.topcomponents.registration.systems;

import dk.sunepoulsen.timelog.backend.BackendConnection;
import dk.sunepoulsen.timelog.registry.Registry;
import dk.sunepoulsen.timelog.ui.dialogs.registration.systems.RegistrationSystemDialog;
import dk.sunepoulsen.timelog.ui.model.registration.systems.RegistrationSystemModel;
import dk.sunepoulsen.timelog.ui.tasks.backend.ExecuteBackendServiceTask;
import dk.sunepoulsen.timelog.ui.tasks.backend.LoadBackendServiceItemsTask;
import dk.sunepoulsen.timelog.ui.tasks.backend.registration.systems.CreateRegistrationSystemTask;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import lombok.extern.slf4j.XSlf4j;

import java.io.IOException;
import java.util.Optional;
import java.util.ResourceBundle;

@XSlf4j
public class RegistrationSystemsPane extends BorderPane {
    private Registry registry;
    private BackendConnection backendConnection = null;
    private ResourceBundle bundle;

    @FXML
    private TableView<RegistrationSystemModel> viewer;

    @FXML
    private Region veil = null;

    @FXML
    private ProgressIndicator progressIndicator = null;

    @FXML
    private Button deleteButton = null;


    public RegistrationSystemsPane() {
        this.registry = Registry.getDefault();
        this.backendConnection = registry.getBackendConnection();
        this.bundle = registry.getBundle( getClass() );

        FXMLLoader fxmlLoader = new FXMLLoader( getClass().getResource( "registrationsystemspane.fxml" ) );
        fxmlLoader.setRoot( this );
        fxmlLoader.setController( this );
        fxmlLoader.setResources( bundle );

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

        backendConnection.getEvents().getRegistrationSystems().getCreatedEvent().addListener( v -> reload() );
        backendConnection.getEvents().getRegistrationSystems().getDeletedEvent().addListener( v -> reload() );

        viewer.getSelectionModel().setSelectionMode( SelectionMode.MULTIPLE );
        viewer.getSelectionModel().getSelectedItems().addListener( this::updateButtonsState );

        reload();
    }

    private void updateButtonsState( ListChangeListener.Change<? extends RegistrationSystemModel> listener ) {
        ObservableList<? extends RegistrationSystemModel> list = listener.getList();

        deleteButton.disableProperty().setValue( list.isEmpty() );
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

            deleteButton.setDisable( true );
        } );

        log.info( "Loading registration systems" );
        new Thread( task ).start();
    }

    @FXML
    private void addItem( final ActionEvent event ) {
        RegistrationSystemDialog dialog = new RegistrationSystemDialog();
        Optional<RegistrationSystemModel> model = dialog.showAndWait();

        model.ifPresent( registrationSystemModel -> {
            CreateRegistrationSystemTask task = new CreateRegistrationSystemTask( registry.getBackendConnection(), registrationSystemModel );
            registry.getUiRegistry().getTaskExecutorService().submit( task );
        } );
    }

    @FXML
    private void deleteItem( final ActionEvent event ) {
        Alert alert = new Alert( Alert.AlertType.CONFIRMATION, bundle.getString( "alert.deletion.content.text" ) );
        alert.setHeaderText( bundle.getString( "alert.deletion.header.text" ) );
        alert.setTitle( bundle.getString( "alert.deletion.title.text" ) );

        alert.showAndWait()
            .filter(response -> response == ButtonType.OK)
            .ifPresent(response -> {
                ExecuteBackendServiceTask task = new ExecuteBackendServiceTask( backendConnection, connection ->
                    connection.servicesFactory().newRegistrationSystemsService().delete( viewer.getSelectionModel().getSelectedItems() )
                );
                registry.getUiRegistry().getTaskExecutorService().submit( task );
            });
    }
}
