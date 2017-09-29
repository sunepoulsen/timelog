package dk.sunepoulsen.timelog.utils.os;

import java.io.IOException;

/**
 * Created by sunepoulsen on 15/06/2017.
 */
public class OperatingSystemFactory {
    public static OperatingSystem operatingSystem = null;

    public static OperatingSystem getInstance() throws IOException {
        if( operatingSystem == null ) {
            operatingSystem = new MacOS();
        }

        return operatingSystem;
    }
}
