package dev.game.spacechaos.engine.exception;

import dev.game.spacechaos.engine.entity.annotation.InjectComponent;

/**
 * Throw when a required component wasn't found.
 * <p>
 * 
 * @see InjectComponent The respective annotation.
 */
public class RequiredComponentNotFoundException extends RuntimeException {

    public RequiredComponentNotFoundException(String message) {
        super(message);
    }

}
