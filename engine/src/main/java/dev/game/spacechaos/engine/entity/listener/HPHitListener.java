package dev.game.spacechaos.engine.entity.listener;

/**
 * Created by Justin on 14.03.2017.
 */
@FunctionalInterface
public interface HPHitListener {

    public void onHit(float oldValue, float newValue, float maxHP);

}
