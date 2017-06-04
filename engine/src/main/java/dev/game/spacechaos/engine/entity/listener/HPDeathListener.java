package dev.game.spacechaos.engine.entity.listener;

import dev.game.spacechaos.engine.entity.Entity;

/**
 * The listener interface for receiving entity death events.
 *
 * @since 1.0.1-PreAlpha
 */
@FunctionalInterface
public interface HPDeathListener {

    /**
     * Invoked when an entity dies.
     * 
     * @param causingEntity
     *            The entity that caused the death. Will be null if there is no
     *            other entity responsible.
     */
    public void onDeath(Entity causingEntity);

}
