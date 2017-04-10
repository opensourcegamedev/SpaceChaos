package dev.game.spacechaos.engine.collision.shape;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import dev.game.spacechaos.engine.camera.CameraWrapper;
import dev.game.spacechaos.engine.collision.CShape;
import dev.game.spacechaos.engine.time.GameTime;

/**
 * Created by Justin on 10.04.2017.
 */
public class CRotatedRectangle extends CRectangle {

    protected float angle = 0;

    /**
     * default constructor
     *
     * @param x      x position of rectangle
     * @param y      position of rectangle
     * @param width  width of rectangle
     * @param height of rectangle
     */
    public CRotatedRectangle(float x, float y, float width, float height, float angle) {
        super(x, y, width, height);

        this.angle = angle;
    }

    public float getAngle () {
        return this.angle;
    }

    public void setAngle (float angle) {
        if (angle < 0) {
            throw new IllegalArgumentException("angle has to be >= 0.");
        }

        this.angle = angle % 360;
    }

    @Override
    public boolean overlaps(CShape obj) {
        //TODO: implement collision detection

        throw new IllegalArgumentException("shape class " + obj.getClass() + " isnt supported.");
    }

    @Override
    public void drawShape(GameTime time, CameraWrapper camera, ShapeRenderer shapeRenderer, Color color) {
        shapeRenderer.setColor(color);
        shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.rect(this.x, this.y, x + (width / 2), y + (height / 2), this.width, this.height, 1f, 1f, this.angle);
    }

}
