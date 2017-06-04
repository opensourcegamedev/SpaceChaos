package dev.game.spacechaos.engine.entity.listener;

/**
 * The listener interface for receiving entity movement events.
 *
 * @since 1.0.0-PreAlpha
 */
public interface PositionChangedListener {

    public void onPositionChanged(float oldX, float oldY, float newX, float newY);

}
