package dev.game.spacechaos.engine.collision;

/**
 * Created by Justin on 10.04.2017.
 */
public abstract class CShape {

    public abstract float getCenterX ();

    public abstract float getCenterY ();

    public abstract boolean overlaps (CShape obj);

}
