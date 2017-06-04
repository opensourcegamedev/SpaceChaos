package dev.game.spacechaos.engine.entity.listener;

/**
 * The listener interface for receiving health update events.
 * <p>
 * For more narrow use cases also see {@link HPHitListener} and
 * {@link HPDeathListener}.
 * 
 * @since 1.0.0-PreAlpha
 */
@FunctionalInterface
public interface UpdateHPListener {

    /**
     * Invoked when an entities health gets changed.
     * 
     * @param oldValue
     *            The old hp value.
     * @param newValue
     *            The new hp value.
     * @param maxHP
     *            The max hp.
     */
    public void onHPUpdate(float oldValue, float newValue, float maxValue);

}
