package dev.game.spacechaos.engine.collision.shape;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Justin on 10.04.2017.
 */
public class CCircleTest {

    @Test
    public void testConstructor () {
        CCircle circle = new CCircle(10, 20, 40);

        assertEquals(10, circle.getCenterX(), 0);
        assertEquals(20, circle.getCenterY(), 0);
        assertEquals(40, circle.getRadius(), 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorException1 () {
        CCircle circle = new CCircle(10, 10, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorException2 () {
        CCircle circle = new CCircle(10, 10, -5);
    }

    @Test
    public void testOverlapsCircle () {
        CCircle circle1 = new CCircle(0, 0, 50);
        CCircle circle2 = new CCircle(200, 200, 50);

        assertEquals(false, circle1.overlaps(circle2));

        CCircle circle3 = new CCircle(100, 100, 50);
        assertEquals(false, circle1.overlaps(circle3));

        CCircle circle4 = new CCircle(100, 0, 50);
        assertEquals(true, circle1.overlaps(circle4));

        CCircle circle5 = new CCircle(100, 0, 55);
        assertEquals(true, circle1.overlaps(circle4));
    }

}
