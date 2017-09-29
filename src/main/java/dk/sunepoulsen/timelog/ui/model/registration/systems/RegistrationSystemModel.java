package dk.sunepoulsen.timelog.ui.model.registration.systems;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class RegistrationSystemModel {
    private Long id;

    @NotNull
    private String name;

    private String description;
}
