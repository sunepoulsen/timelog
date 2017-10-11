package dk.sunepoulsen.timelog.backend.services;

import dk.sunepoulsen.timelog.backend.events.AccountsEvents;
import dk.sunepoulsen.timelog.backend.events.RegistrationSystemsEvents;
import dk.sunepoulsen.timelog.db.entities.AccountEntity;
import dk.sunepoulsen.timelog.db.entities.RegistrationSystemEntity;
import dk.sunepoulsen.timelog.db.storage.DatabaseStorage;
import dk.sunepoulsen.timelog.ui.model.accounts.AccountModel;
import dk.sunepoulsen.timelog.validation.TimeLogValidateException;
import dk.sunepoulsen.timelog.validation.TimeLogValidation;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by sunepoulsen on 12/06/2017.
 */
public class AccountsService {
    private final RegistrationSystemsEvents registrationSystemsEvents;
    private final AccountsEvents accountsEvents;
    private final RegistrationSystemsService registrationSystemsService;
    private final DatabaseStorage database;

    public AccountsService( final AccountsEvents accountsEvents, final RegistrationSystemsEvents registrationSystemsEvents,
                            final RegistrationSystemsService registrationSystemsService, final DatabaseStorage database ) {
        this.accountsEvents = accountsEvents;
        this.registrationSystemsEvents = registrationSystemsEvents;
        this.registrationSystemsService = registrationSystemsService;
        this.database = database;
    }

    public AccountModel create( AccountModel accountModel ) throws TimeLogValidateException {
        TimeLogValidation.validateValue( accountModel );

        database.transactional( em -> {
            RegistrationSystemEntity registrationSystemEntity = em.find( RegistrationSystemEntity.class, accountModel.getRegistrationSystem().getId() );
            AccountEntity entity = convertModel( accountModel, registrationSystemEntity );
            em.persist( entity );

            accountModel.setId( entity.getId() );
        } );

        accountsEvents.getCreatedEvent().fire( Arrays.asList( accountModel ) );
        registrationSystemsEvents.getUpdatedEvent().fire( Arrays.asList( accountModel.getRegistrationSystem() ) );

        return accountModel;
    }

    public List<AccountModel> findAll() {
        List<AccountEntity> entities = database.query( em ->  em.createNamedQuery( "findAllAccounts", AccountEntity.class ) );

        return entities.stream()
            .map( AccountsService::convertEntity )
            .collect( Collectors.toList() );
    }

    private static AccountEntity convertModel( AccountModel model, RegistrationSystemEntity registrationSystemEntity ) {
        AccountEntity entity = new AccountEntity();

        entity.setId( model.getId() );
        entity.setRegistrationSystem( registrationSystemEntity );
        entity.setName( model.getName() );
        entity.setDescription( model.getDescription() );

        return entity;
    }

    private static AccountModel convertEntity( AccountEntity entity ) {
        AccountModel model = new AccountModel();

        model.setId( entity.getId() );
        model.setName( entity.getName() );
        model.setDescription( entity.getDescription() );

        if( entity.getRegistrationSystem() != null ) {
            model.setRegistrationSystem( RegistrationSystemsService.convertEntity( entity.getRegistrationSystem() ) );
        }

        return model;
    }
}
