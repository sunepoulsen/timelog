package dk.sunepoulsen.timelog.events;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * General Event class that consumes a value.
 *
 * @param <T> The type of the value to consumer by the listeners.
 */
public class ConsumerEvent<T> {
    private List<Consumer<T>> listeners = new ArrayList<>();

    public void addListener( Consumer<T> listener ) {
        listeners.add( listener );
    }

    public void removeListener( Consumer<T> listener ) {
        listeners.remove( listener );
    }

    public void fire( T value ) {
        listeners.stream().forEach( action -> action.accept( value ) );
    }
}
