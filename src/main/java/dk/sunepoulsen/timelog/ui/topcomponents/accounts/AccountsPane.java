package dk.sunepoulsen.timelog.ui.topcomponents.accounts;

import dk.sunepoulsen.timelog.backend.BackendConnection;
import dk.sunepoulsen.timelog.registry.Registry;
import dk.sunepoulsen.timelog.ui.dialogs.accounts.AccountDialog;
import dk.sunepoulsen.timelog.ui.model.accounts.AccountModel;
import dk.sunepoulsen.timelog.ui.tasks.backend.LoadBackendServiceItemsTask;
import dk.sunepoulsen.timelog.ui.tasks.backend.accounts.CreateAccountTask;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import lombok.extern.slf4j.XSlf4j;

import java.io.IOException;
import java.util.Optional;

@XSlf4j
public class AccountsPane extends BorderPane {
    private Registry registry;
    private BackendConnection backendConnection = null;

    @FXML
    private TableView<AccountModel> accountsView;

    @FXML
    private TableColumn<AccountModel, String> registrationSystemColumn;

    @FXML
    private Region veil = null;

    @FXML
    private ProgressIndicator progressIndicator = null;

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

        registrationSystemColumn.setCellValueFactory( param -> {
            if( param != null && param.getValue() != null && param.getValue().getRegistrationSystem() != null ) {
                return new SimpleObjectProperty<>( param.getValue().getRegistrationSystem().getName() );
            }

            return new SimpleObjectProperty<>( "" );
        } );

        backendConnection.getEvents().getAccounts().getCreatedEvent().addListener( v -> reload() );

        reload();
    }

    private void reload() {
        LoadBackendServiceItemsTask<AccountModel> task = new LoadBackendServiceItemsTask<>( backendConnection,
            connection -> connection.servicesFactory().newAccountsService().findAll()
        );

        progressIndicator.progressProperty().bind( task.progressProperty() );
        veil.visibleProperty().bind( task.runningProperty() );
        progressIndicator.visibleProperty().bind( task.runningProperty() );

        task.setOnSucceeded( event -> {
            ObservableList<AccountModel> movies = task.getValue();

            log.info( "Viewing {} accounts", movies.size() );
            accountsView.setItems( movies );
        } );

        log.info( "Loading accounts" );
        registry.getUiRegistry().getTaskExecutorService().submit( task );
    }

    @FXML
    private void addAccount( final ActionEvent event ) {
        AccountDialog dialog = new AccountDialog();
        Optional<AccountModel> model = dialog.showAndWait();

        model.ifPresent( item -> {
            CreateAccountTask task = new CreateAccountTask( backendConnection, item );
            registry.getUiRegistry().getTaskExecutorService().submit( task );
        } );
    }

}
