package dk.sunepoulsen.timelog.ui.mainwindow;

import dk.sunepoulsen.timelog.registry.Registry;
import dk.sunepoulsen.timelog.ui.dialogs.registration.systems.RegistrationSystemDialog;
import dk.sunepoulsen.timelog.ui.model.registration.systems.RegistrationSystemModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.extern.slf4j.XSlf4j;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Created by sunepoulsen on 09/05/2017.
 */
@XSlf4j
public class ActionPanel extends AnchorPane implements Initializable {
    @FXML
    private MenuBar menuBar = null;

    private Registry registry;
    private ResourceBundle bundle;

    private Stage stage;

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
        RegistrationSystemDialog dialog = new RegistrationSystemDialog();
        Optional<RegistrationSystemModel> model = dialog.showAndWait();

        if( model.isPresent() ) {
            Alert alert = new Alert( Alert.AlertType.INFORMATION, bundle.getString( "action.not.implemented" ) );
            alert.setHeaderText( bundle.getString( "mainmenu.file" ) + "|" + bundle.getString( "mainmenu.file.new" ) + "|" + bundle.getString( "mainmenu.file.new.registration.system" ) );
            alert.show();
        }
    }

}
