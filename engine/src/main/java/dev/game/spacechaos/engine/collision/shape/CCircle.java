package dev.game.spacechaos.engine.collision.shape;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import dev.game.spacechaos.engine.camera.CameraWrapper;
import dev.game.spacechaos.engine.collision.CShape;
import dev.game.spacechaos.engine.time.GameTime;

/**
 * Created by Justin on 10.04.2017.
 */
public class CCircle extends CShape {

    protected float x = 0;
    protected float y = 0;
    protected float radius = 0;

    public CCircle (float x, float y, float radius) {
        if (radius <= 0) {
            throw new IllegalArgumentException("radius has to be greater than 0.");
        }

        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    @Override
    public float getCenterX() {
        return this.x;
    }

    @Override
    public float getCenterY() {
        return this.y;
    }

    public float getRadius () {
        return this.radius;
    }

    @Override
    public boolean overlaps(CShape obj) {
        if (obj instanceof CCircle) {
            CCircle circle = (CCircle) obj;

            float dx = x - circle.x;
            float dy = y - circle.y;
            float distance = dx * dx + dy * dy;
            float radiusSum = radius + circle.radius;

            return distance <= radiusSum * radiusSum;
        } else {
            throw new IllegalArgumentException("shape class " + obj.getClass() + " isnt supported.");
        }
    }

    @Override
    public void drawShape(GameTime time, CameraWrapper camera, ShapeRenderer shapeRenderer, Color color) {
        shapeRenderer.setColor(color);
        shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.circle(this.x, this.y, this.radius);
    }

}
