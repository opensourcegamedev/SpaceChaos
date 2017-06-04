package dev.game.spacechaos.engine.window;

/**
 * Created by Justin on 06.02.2017.
 */
@FunctionalInterface
public interface ResizeListener {

    /**
     * method will be called, if window was resized
     *
     * @param width
     *            new width of window
     * @param height
     *            new height of window
     */
    public void onResize(final int width, final int height);

}
