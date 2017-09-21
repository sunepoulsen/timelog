package dk.sunepoulsen.timelog.it.registry.db

import dk.sunepoulsen.timelog.db.AgreementEntity
import dk.sunepoulsen.timelog.registry.db.DatabaseStorage
import liquibase.Contexts
import liquibase.LabelExpression
import liquibase.Liquibase
import liquibase.database.Database
import liquibase.database.DatabaseFactory
import liquibase.database.jvm.JdbcConnection
import liquibase.resource.ClassLoaderResourceAccessor
import org.junit.After
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test

import java.sql.Connection
import java.sql.DriverManager
import java.time.LocalDate

/**
 * Created by sunepoulsen on 12/05/2017.
 */
class DatabaseStorageIT {
    private static Properties settings = new Properties()
    private DatabaseStorage databaseStorage;

    @BeforeClass
    static void migrateDatabase() {
        settings.load( getClass().getResourceAsStream( "/application-test.properties" ) )

        String url = settings.getProperty( "liquibase.datasource.url" )
        String username = settings.getProperty( "liquibase.datasource.username" )
        String password = settings.getProperty( "liquibase.datasource.password" )

        Connection connection = DriverManager.getConnection( url, username, password )
        try {
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation( new JdbcConnection( connection ) )
            Liquibase liquibase = new liquibase.Liquibase( "db/changelog/changelog.xml", new ClassLoaderResourceAccessor(), database )

            liquibase.update( new Contexts(), new LabelExpression() )
        }
        finally {
            connection.close()
        }
    }

    @Before
    void initDatabaseStorage() {
        databaseStorage = new DatabaseStorage( "timelog-integration-tests" )
        databaseStorage.connect()
        databaseStorage.deleteAllData()
    }

    @After
    void clearDatabaseStorage() {
        databaseStorage.disconnect()
    }

    @Test
    void addMovieWithArtifact() {
        AgreementEntity agreementEntity = new AgreementEntity( name: "name", startDate: LocalDate.now() )
        databaseStorage.persist( agreementEntity )

        databaseStorage.untransactionalConsumer { em ->
            AgreementEntity foundAgreement = em.find( AgreementEntity.class, agreementEntity.id )

            assert foundAgreement == agreementEntity
        }
    }
}
