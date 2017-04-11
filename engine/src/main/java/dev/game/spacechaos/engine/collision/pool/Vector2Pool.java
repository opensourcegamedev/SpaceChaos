package dev.game.spacechaos.engine.collision.pool;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import dev.game.spacechaos.engine.collision.Range;

/**
 * Created by Justin on 11.04.2017.
 */
public class Vector2Pool {

    protected static Pool<Vector2> rangePool = VectorPoolFactory.createRectanglePool();

    public static Vector2 create () {
        return rangePool.obtain();
    }

    public static void free (Vector2 range) {
        rangePool.free(range);
    }

}
