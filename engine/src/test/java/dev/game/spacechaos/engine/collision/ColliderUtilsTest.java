package dev.game.spacechaos.engine.collision;

import com.badlogic.gdx.math.Rectangle;
import dev.game.spacechaos.engine.collision.shape.CCircle;
import dev.game.spacechaos.engine.collision.shape.CPoint;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Justin on 10.04.2017.
 */
public class ColliderUtilsTest {

    @Test
    public void testOverlaping () {
        assertEquals("values arent overlaping, but method returns true.", false, ColliderUtils.overlaping(0, 1, 2, 3));
        assertEquals("values arent overlaping, but method returns true.", false, ColliderUtils.overlaping(2, 3, 3.1f, 4));
        assertEquals("values are overlaping, but method returns false.", true, ColliderUtils.overlaping(0, 3, 2, 3));
        assertEquals("values are overlaping, but method returns false.", true, ColliderUtils.overlaping(3, 4, 2, 3));
        assertEquals("values are overlaping, but method returns false.", true, ColliderUtils.overlaping(4, 3, 2, 3));
    }

    @Test
    public void testRectangleRectangleCollisionTest () {
        Rectangle rect1 = new Rectangle(0, 0, 100, 100);
        Rectangle rect2 = new Rectangle(99, 99, 100, 100);

        assertEquals("rectangle is overlaping, but collision test returns false.", true, ColliderUtils.testRectangleRectangleCollision(rect1, rect2));
    }

    @Test
    public void testOverlapingCirclePoint () {
        CCircle circle = new CCircle(0, 0, 5);
        CPoint point1 = new CPoint(0, 0);

        assertEquals(true, ColliderUtils.testCirclePointCollision(circle, point1));

        CPoint point2 = new CPoint(5, 0);
        assertEquals(true, ColliderUtils.testCirclePointCollision(circle, point2));

        CPoint point3 = new CPoint(10, 10);
        assertEquals(false, ColliderUtils.testCirclePointCollision(circle, point3));

        CPoint point4 = new CPoint(0, -5);
        assertEquals(true, ColliderUtils.testCirclePointCollision(circle, point4));
    }

}
