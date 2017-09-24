package dk.sunepoulsen.timelog.ui.mainwindow;

import dk.sunepoulsen.timelog.ui.topcomponents.clientpane.ClientPane;
import dk.sunepoulsen.timelog.ui.topcomponents.navigator.TreeNavigator;
import javafx.fxml.FXML;
import lombok.extern.slf4j.XSlf4j;
import org.controlsfx.control.StatusBar;

@XSlf4j
public class MainWindow {
    @FXML
    private TreeNavigator navigator;

    @FXML
    private ClientPane clientPane;

    @FXML
    private StatusBar statusBar;
}
