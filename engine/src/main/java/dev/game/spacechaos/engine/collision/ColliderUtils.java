package dev.game.spacechaos.engine.collision;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Justin on 10.04.2017.
 */
public class ColliderUtils {

    public static boolean testRectangleRectangleCollision (Rectangle rect1, Rectangle rect2) {
        return rect1.overlaps(rect2);
    }

    public static boolean testCircleCircleCollision (Circle circle1, Circle circle2) {
        return circle1.overlaps(circle2);
    }

    public static boolean overlaping (float minA, float maxA, float minB, float maxB) {
        if (maxA < minA) {
            //swap values
            float a = maxA;
            maxA = minA;
            minA = a;
        }

        if (maxB < minB) {
            //swap values
            float b = minB;
            maxB = minB;
            minB = b;
        }

        //check, if values are overlaping
        return minB <= maxA && minA <= maxB;
    }

}
