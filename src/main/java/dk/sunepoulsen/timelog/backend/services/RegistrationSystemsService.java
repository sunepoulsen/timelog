package dk.sunepoulsen.timelog.backend.services;

import dk.sunepoulsen.timelog.db.entities.RegistrationSystemEntity;
import dk.sunepoulsen.timelog.db.storage.DatabaseStorage;
import dk.sunepoulsen.timelog.ui.model.registration.systems.RegistrationSystemModel;
import dk.sunepoulsen.timelog.validation.TimeLogValidateException;
import dk.sunepoulsen.timelog.validation.TimeLogValidation;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by sunepoulsen on 12/06/2017.
 */
public class RegistrationSystemsService {
    private final DatabaseStorage database;

    public RegistrationSystemsService( final DatabaseStorage database ) {
        this.database = database;
    }

    public RegistrationSystemModel create( RegistrationSystemModel registrationSystem ) throws TimeLogValidateException {
        TimeLogValidation.validateValue( registrationSystem );

        database.transactional( em -> {
            RegistrationSystemEntity entity = convertModel( registrationSystem );
            em.persist( entity );

            registrationSystem.setId( entity.getId() );
        } );

        return registrationSystem;
    }

    public RegistrationSystemModel find( Long id ) {
        return database.untransactionalFunction( em -> {
            RegistrationSystemEntity entity = em.find( RegistrationSystemEntity.class, id );
            return convertEntity( entity );
        } );
    }

    public List<RegistrationSystemModel> findAll() {
        List<RegistrationSystemEntity> entities = database.query( em ->  em.createNamedQuery( "findAll", RegistrationSystemEntity.class ) );

        return entities.stream()
                .map( this::convertEntity )
                .collect( Collectors.toList() );
    }

    private RegistrationSystemEntity convertModel( RegistrationSystemModel model ) {
        RegistrationSystemEntity entity = new RegistrationSystemEntity();

        entity.setId( model.getId() );
        entity.setName( model.getName() );
        entity.setDescription( model.getDescription() );

        return entity;
    }

    private RegistrationSystemModel convertEntity( RegistrationSystemEntity entity ) {
        RegistrationSystemModel model = new RegistrationSystemModel();

        model.setId( entity.getId() );
        model.setName( entity.getName() );
        model.setDescription( entity.getDescription() );

        return model;
    }
}
