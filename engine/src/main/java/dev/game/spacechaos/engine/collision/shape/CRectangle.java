package dev.game.spacechaos.engine.collision.shape;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import dev.game.spacechaos.engine.camera.CameraWrapper;
import dev.game.spacechaos.engine.collision.CShape;
import dev.game.spacechaos.engine.time.GameTime;

/**
 * Created by Justin on 10.04.2017.
 */
public class CRectangle extends CShape {

    protected float x = 0;
    protected float y = 0;
    protected float width = 0;
    protected float height = 0;

    /**
     * default constructor
     *
     * @param x
     *            x position of rectangle
     * @param y
     *            position of rectangle
     * @param width
     *            width of rectangle
     * @param height
     *            of rectangle
     */
    public CRectangle(float x, float y, float width, float height) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("width and height has to be greater than 0.");
        }

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public float getX() {
        return this.x + offsetX;
    }

    public float getY() {
        return this.y + offsetY;
    }

    public float getWidth() {
        return this.width;
    }

    public float getHeight() {
        return this.height;
    }

    @Override
    public float getCenterX() {
        return x + (width / 2) + offsetX;
    }

    @Override
    public float getCenterY() {
        return y + (height / 2) + offsetY;
    }

    @Override
    public boolean overlaps(CShape obj) {
        if (obj instanceof CRectangle) {
            // rectangle - rectangle - collision test
            CRectangle rect = (CRectangle) obj;

            // test if rectangle overlaps other rectangle
            return x <= rect.getX() + rect.width && getX() + width >= rect.getX() && getY() <= rect.getY() + rect.height
                    && getY() + height >= rect.getY();
        } else {
            throw new IllegalArgumentException("shape class " + obj.getClass() + " isnt supported.");
        }
    }

    @Override
    public void drawShape(GameTime time, CameraWrapper camera, ShapeRenderer shapeRenderer, Color color) {
        shapeRenderer.setColor(color);
        shapeRenderer.set(ShapeRenderer.ShapeType.Line);
        shapeRenderer.rect(this.getX(), this.getY(), this.width, this.height);
    }

}
