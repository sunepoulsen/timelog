//-----------------------------------------------------------------------------
package dk.sunepoulsen.timelog.registry;

//-----------------------------------------------------------------------------

import dk.sunepoulsen.timelog.registry.db.DatabaseStorage;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by sunepoulsen on 21/10/2016.
 */
public class Registry {
    //-------------------------------------------------------------------------
    //              Constructors
    //-------------------------------------------------------------------------

    public Registry() {
        this.uiRegistry = new UIRegistry();
        this.locale = null;

        this.databaseStorage = new DatabaseStorage();
    }

    public void initialize( final Stage primaryStage ) throws IOException {
        this.uiRegistry.initialize( primaryStage );
        this.locale = Locale.getDefault();
    }

    public void shutdown() {
        this.uiRegistry.shutdown();
        this.databaseStorage.disconnect();

    }

    //-------------------------------------------------------------------------
    //              Registries
    //-------------------------------------------------------------------------

    public DatabaseStorage getDatabaseStorage() {
        return databaseStorage;
    }

    //-------------------------------------------------------------------------
    //              Resource Bundles
    //-------------------------------------------------------------------------

    public <T> ResourceBundle getBundle( Class<T> clazz ) {
        String baseName = clazz.getName().toLowerCase();
        return ResourceBundle.getBundle( baseName, locale );
    }

    //-------------------------------------------------------------------------
    //              Global registry
    //-------------------------------------------------------------------------

    public static Registry getDefault() {
        if( global == null ) {
            global = new Registry();
        }

        return global;
    }

    //-------------------------------------------------------------------------
    //              Private members
    //-------------------------------------------------------------------------

    private static Registry global;
    private DatabaseStorage databaseStorage;

    @Getter
    @Setter
    private Locale locale;

    @Getter
    private UIRegistry uiRegistry;
}
