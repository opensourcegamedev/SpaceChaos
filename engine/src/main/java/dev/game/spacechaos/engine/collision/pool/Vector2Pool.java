package dev.game.spacechaos.engine.collision.pool;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import dev.game.spacechaos.engine.collision.Range;

/**
 * Created by Justin on 11.04.2017.
 */
public class Vector2Pool {

    protected static Pool<Vector2> rangePool = VectorPoolFactory.createRectanglePool();

    public static Vector2 create(float x, float y) {
        Vector2 v = create();
        v.set(x, y);

        return v;
    }

    public static Vector2 create(Vector2 vector) {
        Vector2 v = create();
        v.set(vector.x, vector.y);

        return v;
    }

    public static Vector2 create() {
        return rangePool.obtain();
    }

    public static void free(Vector2 range) {
        rangePool.free(range);
    }

    public static void free(Vector2... array) {
        for (Vector2 v : array) {
            rangePool.free(v);
        }
    }

}
