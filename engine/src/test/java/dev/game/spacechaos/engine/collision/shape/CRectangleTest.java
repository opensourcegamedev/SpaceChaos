package dev.game.spacechaos.engine.collision.shape;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by Justin on 10.04.2017.
 */
public class CRectangleTest {

    @Test
    public void testConstructor() {
        CRectangle rectangle = new CRectangle(10, 20, 100, 200);

        assertNotEquals(10, 10.1, 0);
        assertEquals(10, rectangle.getX(), 0);
        assertEquals(20, rectangle.getY(), 0);
        assertEquals(100, rectangle.getWidth(), 0);
        assertEquals(200, rectangle.getHeight(), 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorException1() {
        CRectangle rectangle = new CRectangle(10, 20, 0, 200);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorException2() {
        CRectangle rectangle = new CRectangle(10, 20, 100, 0);
    }

    @Test
    public void testOverlapsRectangle() {
        CRectangle rect1 = new CRectangle(0, 0, 100, 100);
        CRectangle rect2 = new CRectangle(0, 0, 100, 100);

        assertEquals(true, rect1.overlaps(rect2));

        CRectangle rect3 = new CRectangle(-10, -10, 5, 5);
        assertEquals(false, rect1.overlaps(rect3));

        CRectangle rect4 = new CRectangle(100, 100, 100, 100);
        assertEquals(true, rect1.overlaps(rect4));

        CRectangle rect5 = new CRectangle(50, 50, 100, 100);
        assertEquals(true, rect1.overlaps(rect5));
    }

}
