package dev.game.spacechaos.engine.exception;

/**
 * Created by Justin on 10.02.2017.
 */
public class ReadOnlyException extends RuntimeException {

    public ReadOnlyException (String message) {
        super(message);
    }

}
