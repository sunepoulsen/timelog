package dk.sunepoulsen.timelog.ui.mainwindow;

import dk.sunepoulsen.timelog.registry.Registry;
import dk.sunepoulsen.timelog.ui.topcomponents.clientpane.ClientPane;
import dk.sunepoulsen.timelog.ui.topcomponents.navigator.TreeNavigator;
import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import lombok.extern.slf4j.XSlf4j;
import org.controlsfx.control.StatusBar;

import java.net.URL;
import java.util.ResourceBundle;

@XSlf4j
public class MainWindow implements Initializable {
    @FXML
    private ActionPanel actionPanel;

    @FXML
    private TreeNavigator navigator;

    @FXML
    private ClientPane clientPane;

    @FXML
    private StatusBar statusBar;

    @Override
    public void initialize( final URL location, final ResourceBundle resources ) {
        clientPane.getCurrentPaneProperty().bind( navigator.getSelectedProperty() );

        actionPanel.setOnTaskCreated( this::setupTask );
    }

    private void setupTask( Task task ) {
        task.setOnScheduled( this::bindProgressIndicator );
        task.setOnSucceeded( this::unbindProgressIndicator );
        task.setOnCancelled( this::unbindProgressIndicator );
        task.setOnFailed( this::unbindProgressIndicator );

        Registry.getDefault().getUiRegistry().getTaskExecutorService().submit( task );
    }

    private void bindProgressIndicator( Event event ) {
        log.info( "Task changed state to {}", event.getEventType().toString() );

        Task task = (Task)event.getSource();
        statusBar.progressProperty().bind( task.progressProperty() );
        statusBar.textProperty().bind( task.messageProperty() );
    }

    private void unbindProgressIndicator( Event event ) {
        log.info( "Task changed state to {}", event.getEventType().toString() );

        statusBar.progressProperty().unbind();
        statusBar.textProperty().unbind();
        statusBar.setText( "" );
        statusBar.setProgress( 0.0 );
    }
}
