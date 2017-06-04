package dev.game.spacechaos.engine.exception;

import dev.game.spacechaos.engine.entity.annotation.RequiredComponents;

/**
 * Throw when a required component wasn't found.
 * <p>
 * 
 * @see RequiredComponents The respective annotation.
 */
public class RequiredComponentNotFoundException extends RuntimeException {

    public RequiredComponentNotFoundException(String message) {
        super(message);
    }

}
