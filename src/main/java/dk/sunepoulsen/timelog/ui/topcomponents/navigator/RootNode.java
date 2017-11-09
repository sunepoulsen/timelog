package dk.sunepoulsen.timelog.ui.topcomponents.navigator;

import dk.sunepoulsen.timelog.ui.model.NodeNavigationModel;
import dk.sunepoulsen.timelog.ui.model.TreeNavigatorModel;
import dk.sunepoulsen.timelog.ui.topcomponents.accounts.AccountsPane;
import dk.sunepoulsen.timelog.ui.topcomponents.registration.systems.RegistrationSystemsPane;
import dk.sunepoulsen.timelog.ui.topcomponents.timelogs.TimelogsPane;
import javafx.scene.control.TreeItem;

/**
 * Created by sunepoulsen on 13/05/2017.
 */
public class RootNode extends TreeItem<TreeNavigatorModel> {
    public RootNode() {
        super();

        createAndAddItem( new NodeNavigationModel( "Timelogs", new TimelogsPane() ) );
        createAndAddItem( new NodeNavigationModel( "Accounts", new AccountsPane() ) );
        createAndAddItem( new NodeNavigationModel( "Registration Systems", new RegistrationSystemsPane() ) );
    }

    private void createAndAddItem( TreeNavigatorModel model ) {
        this.getChildren().add( new TreeItem<>( model ) );
    }
}
