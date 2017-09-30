package dk.sunepoulsen.timelog.backend.events;

import dk.sunepoulsen.timelog.ui.model.registration.systems.RegistrationSystemModel;
import javafx.beans.property.SimpleObjectProperty;
import lombok.Data;

import java.util.List;

@Data
public class RegistrationSystemsEvents {
    private SimpleObjectProperty<List<RegistrationSystemModel>> created;

    public RegistrationSystemsEvents() {
        this.created = new SimpleObjectProperty<>();
    }
}