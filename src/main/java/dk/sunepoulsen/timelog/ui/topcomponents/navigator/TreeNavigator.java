package dk.sunepoulsen.timelog.ui.topcomponents.navigator;

import dk.sunepoulsen.timelog.ui.model.TreeNavigatorModel;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import lombok.extern.slf4j.XSlf4j;

import java.io.IOException;

/**
 * Created by sunepoulsen on 09/05/2017.
 */
@XSlf4j
public class TreeNavigator extends AnchorPane {
    @FXML
    private TreeView<TreeNavigatorModel> treeView;

    private SimpleObjectProperty<TreeNavigatorModel> selectedCollectionProperty;

    public TreeNavigator() {
        FXMLLoader fxmlLoader = new FXMLLoader( getClass().getResource( "treenavigator.fxml" ) );
        fxmlLoader.setRoot( this );
        fxmlLoader.setController( this );

        try {
            fxmlLoader.load();
        }
        catch( IOException exception ) {
            throw new RuntimeException( exception );
        }
    }

    @FXML
    public void initialize() {
        log.info( "Initializing {} custom control", getClass().getSimpleName() );

        treeView.setCellFactory( view -> new TreeNavigatorTreeCell() );
        treeView.setShowRoot( false );
        treeView.setEditable( false );
        treeView.getSelectionModel().setSelectionMode( SelectionMode.SINGLE );
        treeView.getSelectionModel().getSelectedItems().addListener( (ListChangeListener<TreeItem<TreeNavigatorModel>>) c -> {
            ObservableList<? extends TreeItem<TreeNavigatorModel>> list = c.getList();
            if( list.isEmpty() ) {
                selectedCollectionProperty.setValue( null );
            }
            else {
                TreeItem<TreeNavigatorModel> item = list.get( 0 );
                selectedCollectionProperty.setValue( item.getValue() );
            }
        } );

        selectedCollectionProperty = new SimpleObjectProperty<>();

        reload();
    }

    public void reload() {
        treeView.setRoot( new RootNode() );
    }

    public ObservableValue<TreeNavigatorModel> selectedCollectionProperty() {
        return selectedCollectionProperty;
    }
}
