package dev.game.spacechaos.engine.collision.shape;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import dev.game.spacechaos.engine.camera.CameraWrapper;
import dev.game.spacechaos.engine.collision.CShape;
import dev.game.spacechaos.engine.time.GameTime;

/**
 * Created by Justin on 11.04.2017.
 */
public class CPoint extends CShape {

    protected float x = 0;
    protected float y = 0;

    public CPoint (float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public float getCenterX() {
        return this.x;
    }

    @Override
    public float getCenterY() {
        return this.y;
    }

    @Override
    public boolean overlaps(CShape obj) {
        if (obj instanceof CPoint) {
            CPoint point = (CPoint) obj;

            //points only collide, if coordinates are equals
            return this.x == point.x && this.y == point.y;
        } else {
            throw new IllegalArgumentException("shape class " + obj.getClass() + " isnt supported.");
        }
    }

    @Override
    public void drawShape(GameTime time, CameraWrapper camera, ShapeRenderer shapeRenderer, Color color) {

    }

}
