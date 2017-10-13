package dk.sunepoulsen.timelog.db.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table( name = "accounts" )
@NamedQueries( {
    @NamedQuery( name = "findAllAccounts", query = "SELECT a FROM AccountEntity a" ),
    @NamedQuery( name = "deleteAccounts", query = "DELETE FROM AccountEntity a WHERE a.id IN :ids" )
})
public class AccountEntity {
    /**
     * Primary key.
     */
    @Id
    @SequenceGenerator( name = "account_id_seq_generator", sequenceName = "account_id_seq", allocationSize = 1 )
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "account_id_seq_generator" )
    @Column( name = "account_id" )
    private Long id;

    @ManyToOne
    @JoinColumn( name = "registrationsystem_id", nullable = false )
    private RegistrationSystemEntity registrationSystem;

    @Column( name = "name" )
    private String name;

    @Column( name = "description" )
    private String description;
}
