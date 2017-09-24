package dk.sunepoulsen.timelog.ui.mainwindow;

import dk.sunepoulsen.timelog.ui.topcomponents.clientpane.ClientPane;
import dk.sunepoulsen.timelog.ui.topcomponents.navigator.TreeNavigator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import lombok.extern.slf4j.XSlf4j;

import java.net.URL;
import java.util.ResourceBundle;

@XSlf4j
public class MainWindow implements Initializable {
    @FXML
    private TreeNavigator navigator;

    @FXML
    private ClientPane clientPane;

    @Override
    public void initialize( final URL location, final ResourceBundle resources ) {
        clientPane.getCurrentPaneProperty().bind( navigator.getSelectedProperty() );
    }
}
