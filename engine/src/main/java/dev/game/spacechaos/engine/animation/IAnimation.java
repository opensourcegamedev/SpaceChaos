package dev.game.spacechaos.engine.animation;

/**
 * Created by Justin on 06.04.2017.
 */
public interface IAnimation {

    public void start();

    public void stop();

    public boolean isPlaying();

    /**
     * reset animation
     */
    public void reset();

}
