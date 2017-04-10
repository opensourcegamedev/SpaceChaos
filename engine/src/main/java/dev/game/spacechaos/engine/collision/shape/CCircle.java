package dev.game.spacechaos.engine.collision.shape;

import dev.game.spacechaos.engine.collision.CObject;

/**
 * Created by Justin on 10.04.2017.
 */
public class CCircle extends CObject {

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
    public boolean overlaps(CObject obj) {
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

}
