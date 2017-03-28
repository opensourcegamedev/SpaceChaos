package dev.game.spacechaos.engine.time.listener;

/**
 * Created by Justin on 07.03.2017.
 */
@FunctionalInterface
public interface CooldownTimerFinishedListener {

    public void onFinished(long startTime, long interval, long now);

}
