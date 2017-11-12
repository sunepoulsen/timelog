package dk.sunepoulsen.it.timelog.backend.services

import dk.sunepoulsen.timelog.backend.events.RegistrationSystemsEvents
import dk.sunepoulsen.timelog.backend.services.RegistrationSystemsService
import dk.sunepoulsen.timelog.db.storage.DatabaseStorage
import dk.sunepoulsen.timelog.db.storage.DatabaseStorageSettings
import dk.sunepoulsen.timelog.ui.model.registration.systems.RegistrationSystemModel
import org.junit.After
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test

class RegistrationSystemsServiceIT {
    private DatabaseStorage databaseStorage

    @BeforeClass
    static void migrateDatabase() {
        DatabaseStorage database = new DatabaseStorage( "timelog", DatabaseStorageSettings.createInstanceFromPropertyResource( "/application-test.properties" ) )
        database.migrate()
    }

    @Before
    void initDatabaseStorage() {
        databaseStorage = new DatabaseStorage( "timelog", DatabaseStorageSettings.createInstanceFromPropertyResource( "/application-test.properties" ) )
        databaseStorage.connect()
        databaseStorage.deleteAllData()
    }

    @After
    void clearDatabaseStorage() {
        databaseStorage.disconnect()
    }

    @Test
    void testCreateRegistrationSystem() {
        RegistrationSystemsService service = new RegistrationSystemsService( new RegistrationSystemsEvents(), databaseStorage )

        RegistrationSystemModel model = new RegistrationSystemModel( name: "name", description: "description" )
        service.create( model )
        assert model.id != null

        RegistrationSystemModel actual = service.find( model.id )
        assert actual == new RegistrationSystemModel( id: model.id, name: "name", description: "description" )
    }
}
