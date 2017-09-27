package dk.sunepoulsen.timelog.application;

import dk.sunepoulsen.timelog.registry.Registry;
import dk.sunepoulsen.timelog.registry.db.DatabaseStorage;
import dk.sunepoulsen.timelog.ui.mainwindow.MainWindow;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import liquibase.exception.LiquibaseException;

import java.io.IOException;
import java.sql.SQLException;

public class TimelogApplication extends Application {
    private DatabaseStorage storage;
    private Registry registry = Registry.getDefault();

    @Override
    public void start( final Stage primaryStage ) throws Exception {
        initializeDatabase();
        initializeRegistry( primaryStage );

        Parent root = FXMLLoader.load( MainWindow.class.getResource( "mainwindow.fxml" ) );
        Scene scene = new Scene( root );

        primaryStage.setTitle( "TimeLog" );
        primaryStage.setScene( scene );

        // Maximize the window
        final Screen screen = Screen.getPrimary();
        primaryStage.setX( screen.getVisualBounds().getMinX() );
        primaryStage.setY( screen.getVisualBounds().getMinY() );
        primaryStage.setWidth( screen.getVisualBounds().getWidth() );
        primaryStage.setHeight( screen.getVisualBounds().getHeight() );

        primaryStage.show();
    }

    private void initializeRegistry( final Stage primaryStage ) throws IOException {
        registry.initialize( primaryStage );
    }

    @Override
    public void stop() throws Exception {
        registry.shutdown();
        super.stop();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main( String[] args ) {
        launch( args );
    }

    private void initializeDatabase() throws LiquibaseException, SQLException, IOException {
        storage = registry.getDatabaseStorage();
        storage.migrate();
        storage.connect();
    }
}
