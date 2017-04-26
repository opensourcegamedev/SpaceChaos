package dev.game.spacechaos.engine.collision;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import dev.game.spacechaos.engine.collision.pool.LinePool;
import dev.game.spacechaos.engine.collision.pool.Vector2Pool;
import dev.game.spacechaos.engine.collision.shape.CCircle;
import dev.game.spacechaos.engine.collision.shape.CPoint;
import dev.game.spacechaos.engine.utils.FastMath;

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

    public static boolean testCirclePointCollision (CCircle circle, CPoint point) {
        float dx = circle.getCenterX() - point.getCenterX();
        float dy = circle.getCenterY() - point.getCenterY();
        float distance = dx * dx + dy * dy;
        float radiusSum = circle.getRadius();

        return distance <= radiusSum * radiusSum;
    }

    public static boolean testLineCollision (Line a, Line b) {
        if (FastMath.checkVectorsParallel(a.getDirection(), b.getDirection())) {
            return FastMath.checkIfLinesAreEquals(a, b);
        } else {}
        return true;
    }

    public static boolean onOneSide (Line axis, Segment s) {
        Vector2 d1 = Vector2Pool.create(s.getPoint1()).sub(axis.getBase());
        Vector2 d2 = Vector2Pool.create(s.getPoint2()).sub(axis.getBase());
        Vector2 n = FastMath.rotateVector90(axis.getDirection(), Vector2Pool.create());

        float dotProduct = n.dot(d1) * n.dot(d2);

        //recycle vectors
        Vector2Pool.free(d1, d2, n);

        return dotProduct  > 0;
    }

    public static boolean testSegmentCollision (Segment a, Segment b) {
        Vector2 d = Vector2Pool.create(a.getPoint2()).sub(a.getPoint1());

        Line axisA = LinePool.create();
        axisA.setBase(a.getPoint1());
        axisA.setDirection(d);

        Line axisB = LinePool.create();

        if (onOneSide(axisA, b)) {
            //recycle objects
            LinePool.free(axisA, axisB);
            Vector2Pool.free(d);

            return false;
        }

        axisB.setBase(b.getPoint1());
        d.set(b.getPoint2());
        d = d.sub(b.getPoint1());
        axisB.setDirection(d);

        if (onOneSide(axisB, a)) {
            //recycle objects
            LinePool.free(axisA, axisB);
            Vector2Pool.free(d);

            return false;
        }

        if (FastMath.checkVectorsParallel(axisA.getDirection(), axisB.getDirection())) {
            Range rangeA = a.projectSegment(axisA.getDirection());
            Range rangeB = b.projectSegment(axisB.getDirection());

            //recycle objects
            LinePool.free(axisA, axisB);
            Vector2Pool.free(d);

            return rangeA.overlaps(rangeB);
        } else {
            //recycle objects
            LinePool.free(axisA, axisB);
            Vector2Pool.free(d);

            return true;
        }
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

        //check if values are overlapping
        return minB <= maxA && minA <= maxB;
    }

}
