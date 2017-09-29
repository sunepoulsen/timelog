package dk.sunepoulsen.timelog.ui.mainwindow;

import com.google.common.primitives.Doubles;
import dk.sunepoulsen.timelog.registry.Registry;
import dk.sunepoulsen.timelog.ui.topcomponents.clientpane.ClientPane;
import dk.sunepoulsen.timelog.ui.topcomponents.navigator.TreeNavigator;
import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SplitPane;
import javafx.stage.WindowEvent;
import lombok.extern.slf4j.XSlf4j;
import org.controlsfx.control.StatusBar;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@XSlf4j
public class MainWindow implements Initializable {
    @FXML
    private ActionPanel actionPanel;

    @FXML
    private SplitPane splitPane;

    @FXML
    private TreeNavigator navigator;

    @FXML
    private ClientPane clientPane;

    @FXML
    private StatusBar statusBar;

    boolean splitPaneInitialized = false;

    @Override
    public void initialize( final URL location, final ResourceBundle resources ) {
        clientPane.getCurrentPaneProperty().bind( navigator.getSelectedProperty() );

        actionPanel.setOnTaskCreated( this::setupTask );
    }

    public void initializeSplitPaneDividerPosition( WindowEvent windowEvent ) {
        log.debug( "Initialize divider position of SplitPane. Old positions: {}", splitPane.getDividerPositions() );

        if( windowEvent.getEventType().equals( WindowEvent.WINDOW_SHOWN ) && !splitPaneInitialized ) {
            double[] newPositions = { 0.20 };
            List<Double> userPositions = Registry.getDefault().getSettings().getUserStates().getDividerPositions();

            if( userPositions != null && !userPositions.isEmpty() ) {
                newPositions = Doubles.toArray( userPositions );
            }

            log.debug( "Set divider position to {}. Old value: {}", newPositions, splitPane.getDividerPositions() );
            splitPane.setDividerPositions( newPositions );
            splitPaneInitialized = true;
        }
    }

    public void storeSplitPaneDividerPosition( WindowEvent windowEvent ) {
        if( windowEvent.getEventType().equals( WindowEvent.WINDOW_CLOSE_REQUEST ) ) {
            log.debug( "Store divider positions of SplitPane: {}", splitPane.getDividerPositions() );

            List<Double> positions = Doubles.asList( splitPane.getDividerPositions() );
            Registry.getDefault().getSettings().getUserStates().setDividerPositions( positions );
        }
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
