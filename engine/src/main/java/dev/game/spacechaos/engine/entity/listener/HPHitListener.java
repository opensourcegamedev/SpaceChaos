package dev.game.spacechaos.engine.entity.listener;

import dev.game.spacechaos.engine.entity.Entity;

/**
 * The listener interface for receiving entity hit events (every event, that
 * reduces an entities health).
 *
 * @since 1.0.0-PreAlpha
 */
@FunctionalInterface
public interface HPHitListener {

    /**
     * Invoked when an entity gets hit.
     * 
     * @param oldValue
     *            The old hp value.
     * @param newValue
     *            The new hp value.
     * @param maxHP
     *            The max hp.
     * @param causingEntity
     *            The entity that caused the hit. Will be null if there is no
     *            other entity responsible.
     */
    public void onHit(float oldValue, float newValue, float maxHP, Entity causingEntity);

}
