package dev.game.spacechaos.engine.collision;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import dev.game.spacechaos.engine.camera.CameraWrapper;
import dev.game.spacechaos.engine.time.GameTime;

/**
 * Created by Justin on 10.04.2017.
 */
public abstract class CShape {

    public abstract float getCenterX ();

    public abstract float getCenterY ();

    public abstract boolean overlaps (CShape obj);

    public abstract void drawShape (GameTime time, CameraWrapper camera, ShapeRenderer shapeRenderer, Color color);

}
