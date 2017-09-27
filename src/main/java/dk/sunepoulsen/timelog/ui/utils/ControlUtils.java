package dk.sunepoulsen.timelog.ui.utils;

import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

/**
 * Created by sunepoulsen on 14/06/2017.
 */
public class ControlUtils {
    public static String getText( TextField field ) {
        if( field == null ) {
            return "";
        }

        String s = field.getText();
        if( s != null ) {
            return s;
        }

        return "";
    }

    public static String getText( DatePicker control ) {
        if( control == null ) {
            return "";
        }

        if( control.editorProperty() == null ) {
            return "";
        }

        return getText( control.editorProperty().getValue() );
    }
}
