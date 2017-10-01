package dk.sunepoulsen.timelog.ui.model.accounts;

import dk.sunepoulsen.timelog.ui.model.registration.systems.RegistrationSystemModel;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AccountModel {
    private Long id;

    @NotNull
    private RegistrationSystemModel registrationSystem;

    @NotNull
    private String name;

    private String description;
}
