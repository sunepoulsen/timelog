package dk.sunepoulsen.timelog.backend.events;

import dk.sunepoulsen.timelog.ui.model.accounts.AccountModel;
import javafx.beans.property.SimpleObjectProperty;
import lombok.Data;

import java.util.List;

@Data
public class AccountsEvents {
    private SimpleObjectProperty<List<AccountModel>> created;

    public AccountsEvents() {
        this.created = new SimpleObjectProperty<>();
    }
}
