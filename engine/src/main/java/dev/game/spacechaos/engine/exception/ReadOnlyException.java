package dev.game.spacechaos.engine.exception;

/**
 * Thrown when someone tries to alter a readonly attribute of a component.
 */
public class ReadOnlyException extends RuntimeException {

    public ReadOnlyException (String message) {
        super(message);
    }

}
