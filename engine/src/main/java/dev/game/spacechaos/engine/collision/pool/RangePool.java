package dev.game.spacechaos.engine.collision.pool;

import com.badlogic.gdx.utils.Pool;
import dev.game.spacechaos.engine.collision.Range;

/**
 * Created by Justin on 11.04.2017.
 */
public class RangePool {

    protected static Pool<Range> rangePool = RangePoolFactory.createRectanglePool();

    public static Range create () {
        return rangePool.obtain();
    }

    public static void free (Range range) {
        rangePool.free(range);
    }

}
