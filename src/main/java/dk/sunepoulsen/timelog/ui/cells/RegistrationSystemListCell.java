package dk.sunepoulsen.timelog.ui.cells;

import dk.sunepoulsen.timelog.ui.model.registration.systems.RegistrationSystemModel;
import javafx.scene.control.ListCell;

public class RegistrationSystemListCell extends ListCell<RegistrationSystemModel> {
    @Override
    protected void updateItem( RegistrationSystemModel item, boolean empty ) {
        super.updateItem( item, empty );

        setGraphic( null );
        if( item == null || empty ) {
            setText( null );
        }
        else {
            setText( item.getName() );
        }
    }
}
