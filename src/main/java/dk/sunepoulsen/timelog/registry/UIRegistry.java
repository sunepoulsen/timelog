package dk.sunepoulsen.timelog.registry;

import javafx.stage.Stage;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by sunepoulsen on 18/05/2017.
 */
public class UIRegistry {
    //-------------------------------------------------------------------------
    //              Constructors
    //-------------------------------------------------------------------------

    public UIRegistry() {
        this.taskExecutorService = Executors.newSingleThreadExecutor();
    }

    //-------------------------------------------------------------------------
    //              Properties
    //-------------------------------------------------------------------------

    public Stage getStage() {
        return stage;
    }

    public void setStage( final Stage stage ) {
        this.stage = stage;
    }

    public ExecutorService getTaskExecutorService() {
        return taskExecutorService;
    }

    public void terminateTaskExecutor() {
        this.taskExecutorService.shutdownNow();
    }

    //-------------------------------------------------------------------------
    //              Private members
    //-------------------------------------------------------------------------

    private Stage stage;
    private ExecutorService taskExecutorService;
}
