package dev.game.spacechaos.engine.entity.listener;

/**
 * Created by Justin on 11.03.2017.
 */
@FunctionalInterface
public interface MoveListener {

    public boolean canMove(float oldX, float oldY, float newX, float newY);

}
