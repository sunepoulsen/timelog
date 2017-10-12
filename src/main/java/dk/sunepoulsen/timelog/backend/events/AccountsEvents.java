package dk.sunepoulsen.timelog.backend.events;

import dk.sunepoulsen.timelog.events.ConsumerEvent;
import dk.sunepoulsen.timelog.ui.model.accounts.AccountModel;
import lombok.Data;

import java.util.List;

@Data
public class AccountsEvents {
    private ConsumerEvent<List<AccountModel>> createdEvent;
    private ConsumerEvent<List<AccountModel>> updatedEvent;

    public AccountsEvents() {
        this.createdEvent = new ConsumerEvent<>();
        this.updatedEvent = new ConsumerEvent<>();
    }
}
