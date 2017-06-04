package dev.game.spacechaos.engine.collision;

import com.badlogic.gdx.math.Vector2;
import dev.game.spacechaos.engine.collision.pool.Vector2Pool;
import dev.game.spacechaos.engine.utils.FastMath;

/**
 * Created by Justin on 11.04.2017.
 */
public class Line {

    protected Vector2 base = Vector2Pool.create();
    protected Vector2 direction = Vector2Pool.create();

    public Line(float baseX, float baseY, float dirX, float dirY) {
        this.base.set(baseX, baseY);
        this.direction.set(dirX, dirY);
    }

    public Line() {
        //
    }

    public Vector2 getBase() {
        return this.base;
    }

    public void setBase(float x, float y) {
        this.base.set(x, y);
    }

    public void setBase(Vector2 v) {
        this.base.set(v.x, v.y);
    }

    public Vector2 getDirection() {
        return this.direction;
    }

    public void setDirection(float x, float y) {
        this.direction.set(x, y);
    }

    public void setDirection(Vector2 v) {
        this.direction.set(v.x, v.y);
    }

    public void dispose() {
        if (base == null || direction == null) {
            return;
        }

        Vector2Pool.free(base);
        base = null;
        Vector2Pool.free(direction);
        direction = null;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Line) {
            Line b = (Line) obj;

            return FastMath.checkIfLinesAreEquals(this, b);
        } else {
            throw new IllegalArgumentException("Cannot compare Line with " + obj.getClass() + " class.");
        }
    }

    @Override
    public void finalize() {
        dispose();
    }

}
