package dk.sunepoulsen.timelog.db.storage;

import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import lombok.extern.slf4j.XSlf4j;

import javax.persistence.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Implements the responsibility to register a single connection to a single database
 * that can be used to retrieve operation instances to interact with the database.
 */
@XSlf4j
public class DatabaseStorage {
    public DatabaseStorage() {
        this( PERSISTENCE_NAME, PROPERTIES_FILENAME );
    }

    public DatabaseStorage( String persistenceName, String propertiesFilename ) {
        this.persistenceName = persistenceName;
        this.propertiesFilename = propertiesFilename;
    }

    public void connect() {
        this.emf = Persistence.createEntityManagerFactory( persistenceName );
    }

    public void disconnect() {
        if( this.emf.isOpen() ) {
            this.emf.close();
        }
    }

    public boolean isOpen() {
        return this.emf.isOpen();
    }

    public void migrate() throws IOException, SQLException, LiquibaseException {
        Properties settings = new Properties();
        settings.load( getClass().getResourceAsStream( propertiesFilename ) );

        log.info( "Supported JDBC drivers:" );
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while( drivers.hasMoreElements() ) {
            Driver driver = drivers.nextElement();
            log.info( driver.getClass().getName() );
        }

        String url = settings.getProperty( "liquibase.datasource.url" );
        String username = settings.getProperty( "liquibase.datasource.username" );
        String password = settings.getProperty( "liquibase.datasource.password" );

        try( Connection connection = DriverManager.getConnection( url, username, password ) ) {
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation( new JdbcConnection( connection ) );
            Liquibase liquibase = new Liquibase( "db/changelog/changelog.xml", new ClassLoaderResourceAccessor(), database );

            liquibase.update( new Contexts(), new LabelExpression() );
        }
    }

    public boolean exists( Function<EntityManager, Query> function ) {
        EntityManager em = emf.createEntityManager();

        try {
            Query query = function.apply( em );
            Object object = query.getSingleResult();
            if( object instanceof Number ) {
                return ( (Number) object ).intValue() > 0;
            }
        }
        finally {
            em.close();
        }

        return false;
    }

    public <T> T find( Function<EntityManager, TypedQuery<T>> function ) {
        EntityManager em = emf.createEntityManager();

        try {
            TypedQuery<T> query = function.apply( em );
            List<T> entities = query.getResultList();
            if( entities.isEmpty() ) {
                return null;
            }

            return entities.get( 0 );
        }
        finally {
            em.close();
        }
    }

    public <T> T find( Class<T> clazz, Object primaryKey ) {
        EntityManager em = emf.createEntityManager();

        try {
            return em.find( clazz, primaryKey );
        }
        finally {
            em.close();
        }
    }

    public <T> List<T> query( Function<EntityManager, TypedQuery<T>> function ) {
        EntityManager em = emf.createEntityManager();

        try {
            TypedQuery<T> query = function.apply( em );
            return query.getResultList();
        }
        finally {
            em.close();
        }
    }

    public <T> int executeUpdate( Function<EntityManager, TypedQuery<T>> function ) {
        EntityManager em = emf.createEntityManager();

        try {
            TypedQuery<T> query = function.apply( em );
            return query.executeUpdate();
        }
        finally {
            em.close();
        }
    }

    public void persist( Object value ) {
        transactional( em -> em.persist( value ) );
    }

    public void untransactionalConsumer( Consumer<EntityManager> consumer ) {
        EntityManager em = emf.createEntityManager();

        try {
            consumer.accept( em );
        }
        finally {
            em.close();
        }
    }

    public <T> T untransactionalFunction( Function<EntityManager, T> function ) {
        EntityManager em = emf.createEntityManager();

        try {
            return function.apply( em );
        }
        finally {
            em.close();
        }
    }

    public void transactional( Consumer<EntityManager> consumer ) {
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            consumer.accept( em );
            tx.commit();
        }
        catch( Exception ex ) {
            if( tx != null && tx.isActive() ) {
                log.warn( "Rollback transaction because of exception.", ex );
                tx.rollback();
            }

            throw ex;
        }
        finally {
            em.close();
        }
    }

    public void deleteAllData() {
        // transactional( em -> em.createQuery( "DELETE FROM HolidayEntity entity" ).executeUpdate() );
        // transactional( em -> em.createQuery( "DELETE FROM AgreementEntity entity" ).executeUpdate() );
    }

    //-------------------------------------------------------------------------
    //              Private members
    //-------------------------------------------------------------------------

    private static final String PERSISTENCE_NAME = "timelog";
    private static final String PROPERTIES_FILENAME = "/application.properties";

    private final String persistenceName;
    private final String propertiesFilename;
    private EntityManagerFactory emf;
}
