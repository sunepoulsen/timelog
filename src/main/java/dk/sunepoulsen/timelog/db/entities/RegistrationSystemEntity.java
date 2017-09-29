package dk.sunepoulsen.timelog.db.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table( name = "registrationsystems" )
@NamedQueries( {
    @NamedQuery( name = "findAll", query = "SELECT r FROM RegistrationSystemEntity r" )
})
public class RegistrationSystemEntity {
    /**
     * Primary key.
     */
    @Id
    @SequenceGenerator( name = "registrationsystem_id_seq_generator", sequenceName = "registrationsystem_id_seq", allocationSize = 1 )
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "registrationsystem_id_seq_generator" )
    @Column( name = "registrationsystem_id" )
    private Long id;

    @Column( name = "name" )
    private String name;

    @Column( name = "description" )
    private String description;
}
