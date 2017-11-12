package dk.sunepoulsen.timelog.db.storage;

import dk.sunepoulsen.timelog.utils.os.OperatingSystem;
import dk.sunepoulsen.timelog.utils.os.OperatingSystemFactory;
import lombok.Data;

import java.io.IOException;
import java.util.Properties;

@Data
public class DatabaseStorageSettings {
    private static final String PROPERTY_PREFIX = "liquibase.datasource";

    private String driver;
    private String url;
    private String username;
    private String password;
    private String hibernateDialect;
    private Boolean showSql;
    private Boolean hibernateUseJdbcMetadataDefaults;

    public static DatabaseStorageSettings createInstanceFromProperties( Properties properties ) throws IOException {
        DatabaseStorageSettings instance = new DatabaseStorageSettings();

        String url = properties.getProperty( PROPERTY_PREFIX + ".jdbc.url" );
        if( url.contains( "${os.user.app.directory}" ) ) {
            OperatingSystem os = OperatingSystemFactory.getInstance();
            url = url.replace( "${os.user.app.directory}", os.applicationDataDirectory().getCanonicalPath() );
        }

        instance.setDriver( properties.getProperty( PROPERTY_PREFIX + ".driver" ) );
        instance.setUrl( url );
        instance.setUsername( properties.getProperty( PROPERTY_PREFIX + ".jdbc.user" ) );
        instance.setPassword( properties.getProperty( PROPERTY_PREFIX + ".jdbc.password" ) );
        instance.setHibernateDialect( properties.getProperty( PROPERTY_PREFIX + ".hibernate.dialect" ) );
        instance.setShowSql( Boolean.valueOf( properties.getProperty( PROPERTY_PREFIX + ".hibernate.show_sql" ) ) );
        instance.setHibernateUseJdbcMetadataDefaults( Boolean.valueOf( properties.getProperty( PROPERTY_PREFIX + ".hibernate.temp.use_jdbc_metadata_defaults" ) ) );

        return instance;
    }

    public static DatabaseStorageSettings createInstanceFromPropertyResource( String resourceName ) throws IOException {
        Properties props = new Properties();
        props.load( DatabaseStorageSettings.class.getResourceAsStream( resourceName ) );

        return createInstanceFromProperties( props );
    }
}
