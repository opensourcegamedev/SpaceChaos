package dev.game.spacechaos.engine.exception;

/**
 * Signals that an attempt to open the file denoted by a specified pathname has failed, because of insufficient permissions.
 */
public class FilePermissionException extends RuntimeException {

    public FilePermissionException (String message) {
        super(message);
    }

}
