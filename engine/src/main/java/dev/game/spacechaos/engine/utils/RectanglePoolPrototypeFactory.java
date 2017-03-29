package dev.game.spacechaos.engine.utils;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Pool;

/**
 * Created by Justin on 10.03.2017.
 */
public class RectanglePoolPrototypeFactory {

    protected static Pool<Rectangle> rectPool = null;

    public static Pool<Rectangle> createRectanglePool () {
        if (rectPool == null) {
            rectPool = new Pool<Rectangle>() {

                @Override
                protected Rectangle newObject () {
                    return new Rectangle();
                }

            };
        }

        return rectPool;
    }

    public static Pool<Rectangle> createNewRectanglePool () {
        return new Pool<Rectangle>() {

            @Override
            protected Rectangle newObject () {
                return new Rectangle();
            }

        };
    }

}
