package dev.game.spacechaos.engine.collision.pool;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import dev.game.spacechaos.engine.collision.Line;

/**
 * Created by Justin on 11.04.2017.
 */
public class LinePool {

    protected static Pool<Line> rangePool = LinePoolFactory.createLinePool();

    public static Line create(Vector2 base, Vector2 direction) {
        Line v = create();
        v.setBase(base.x, base.y);
        v.setDirection(base.x, base.y);

        return v;
    }

    public static Line create() {
        return rangePool.obtain();
    }

    public static void free(Line range) {
        rangePool.free(range);
    }

    public static void free(Line... array) {
        for (Line v : array) {
            rangePool.free(v);
        }
    }

}
