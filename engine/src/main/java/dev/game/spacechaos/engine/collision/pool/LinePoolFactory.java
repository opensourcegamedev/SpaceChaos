package dev.game.spacechaos.engine.collision.pool;

import com.badlogic.gdx.utils.Pool;
import dev.game.spacechaos.engine.collision.Line;

/**
 * Created by Justin on 11.04.2017.
 */
public class LinePoolFactory {

    protected static Pool<Line> LinePool = null;

    public static Pool<Line> createLinePool() {
        if (LinePool == null) {
            LinePool = new Pool<Line>() {

                @Override
                protected Line newObject() {
                    return new Line();
                }

            };
        }

        return LinePool;
    }

    public static Pool<Line> createNewLinePool() {
        return new Pool<Line>() {

            @Override
            protected Line newObject() {
                return new Line();
            }

        };
    }

}
