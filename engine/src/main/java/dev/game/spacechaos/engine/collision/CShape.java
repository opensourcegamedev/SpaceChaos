package dev.game.spacechaos.engine.collision;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import dev.game.spacechaos.engine.camera.CameraWrapper;
import dev.game.spacechaos.engine.time.GameTime;

/**
 * Created by Justin on 10.04.2017.
 */
public abstract class CShape {

    protected float offsetX = 0;
    protected float offsetY = 0;

    public abstract float getCenterX();

    public abstract float getCenterY();

    public abstract boolean overlaps(CShape obj);

    public abstract void drawShape(GameTime time, CameraWrapper camera, ShapeRenderer shapeRenderer, Color color);

    public void setOffset(float x, float y) {
        this.offsetX = x;
        this.offsetY = y;
    }

}
