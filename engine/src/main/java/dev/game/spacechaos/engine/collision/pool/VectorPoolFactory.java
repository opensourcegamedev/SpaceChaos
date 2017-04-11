package dev.game.spacechaos.engine.collision.pool;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;

/**
 * Created by Justin on 11.04.2017.
 */
public class VectorPoolFactory {

    protected static Pool<Vector2> Vector2Pool = null;

    public static Pool<Vector2> createRectanglePool () {
        if (Vector2Pool == null) {
            Vector2Pool = new Pool<Vector2>() {

                @Override
                protected Vector2 newObject () {
                    return new Vector2();
                }

            };
        }

        return Vector2Pool;
    }

    public static Pool<Vector2> createNewRectanglePool () {
        return new Pool<Vector2>() {

            @Override
            protected Vector2 newObject () {
                return new Vector2();
            }

        };
    }
    
}
