//-----------------------------------------------------------------------------
package dk.sunepoulsen.timelog.registry;

//-----------------------------------------------------------------------------

import dk.sunepoulsen.timelog.registry.db.DatabaseStorage;

/**
 * Created by sunepoulsen on 21/10/2016.
 */
public class Registry {
    //-------------------------------------------------------------------------
    //              Constructors
    //-------------------------------------------------------------------------

    public Registry() {
        this.databaseStorage = new DatabaseStorage();
        this.uiRegistry = new UIRegistry();
    }

    //-------------------------------------------------------------------------
    //              Registries
    //-------------------------------------------------------------------------

    public DatabaseStorage getDatabaseStorage() {
        return databaseStorage;
    }

    public UIRegistry getUIRegistry() {
        return this.uiRegistry;
    }

    //-------------------------------------------------------------------------
    //              Global registry
    //-------------------------------------------------------------------------

    public static Registry getDefault() {
        if( registry == null ) {
            registry = new Registry();
        }

        return registry;
    }

    //-------------------------------------------------------------------------
    //              Private members
    //-------------------------------------------------------------------------

    private static Registry registry;
    private DatabaseStorage databaseStorage;
    private UIRegistry uiRegistry;
}
