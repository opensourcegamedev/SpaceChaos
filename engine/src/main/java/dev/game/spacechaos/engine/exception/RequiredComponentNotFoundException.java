package dev.game.spacechaos.engine.exception;

/**
 * Created by Justin on 08.04.2017.
 */
public class RequiredComponentNotFoundException extends RuntimeException {

    public RequiredComponentNotFoundException (String message) {
        super(message);
    }

}
