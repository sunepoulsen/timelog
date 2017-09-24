package dk.sunepoulsen.timelog.ui.topcomponents.navigator;

import dk.sunepoulsen.timelog.ui.model.NodeNavigationModel;
import dk.sunepoulsen.timelog.ui.model.TreeNavigatorModel;
import dk.sunepoulsen.timelog.ui.topcomponents.registration.systems.viewer.RegistrationSystemsPane;
import javafx.scene.control.TreeItem;

/**
 * Created by sunepoulsen on 13/05/2017.
 */
public class RootNode extends TreeItem<TreeNavigatorModel> {
    public RootNode() {
        super();

        createAndAddItem( new NodeNavigationModel( "Registration Systems", new RegistrationSystemsPane() ) );
    }

    private void createAndAddItem( TreeNavigatorModel model ) {
        this.getChildren().add( new TreeItem<>( model ) );
    }
}
