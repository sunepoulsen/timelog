package dk.sunepoulsen.timelog.ui.topcomponents.timelogs;

import dk.sunepoulsen.timelog.backend.BackendConnection;
import dk.sunepoulsen.timelog.registry.Registry;
import dk.sunepoulsen.timelog.ui.model.accounts.AccountModel;
import dk.sunepoulsen.timelog.utils.ComponentUtils;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TreeTableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import lombok.extern.slf4j.XSlf4j;

import java.util.ResourceBundle;

@XSlf4j
public class TimelogsPane extends BorderPane {
    private Registry registry;
    private BackendConnection backendConnection = null;
    private ResourceBundle bundle;

    @FXML
    private TreeTableView<AccountModel> timelogs;

    @FXML
    private Region veil = null;

    @FXML
    private ProgressIndicator progressIndicator = null;


    public TimelogsPane() {
        this.registry = Registry.getDefault();
        this.backendConnection = registry.getBackendConnection();
        this.bundle = registry.getBundle( getClass() );

        ComponentUtils.loadFXMLFile( this, bundle );
    }

    @FXML
    public void initialize() {
        log.info( "Initializing {} custom control", getClass().getSimpleName() );
    }
}
