package dk.sunepoulsen.timelog.utils;

import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.util.ResourceBundle;

public class ComponentUtils {
    public static void loadFXMLFile( Object controller, ResourceBundle bundle ) {
        FXMLLoader fxmlLoader = new FXMLLoader( controller.getClass().getResource( controller.getClass().getSimpleName().toLowerCase() + ".fxml" ) );
        fxmlLoader.setRoot( controller );
        fxmlLoader.setController( controller );
        fxmlLoader.setResources( bundle );

        try {
            fxmlLoader.load();
        }
        catch( IOException exception ) {
            throw new RuntimeException( exception );
        }
    }
}
