package dev.game.spacechaos.engine.exception;

import com.sun.org.apache.regexp.internal.RE;

/**
 * Created by Justin on 08.04.2017.
 */
public class RequiredComponentNotFoundException extends RuntimeException {

    public RequiredComponentNotFoundException (String message) {
        super(message);
    }

}
