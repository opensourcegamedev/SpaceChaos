package dev.game.spacechaos.engine.collision;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
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

    @Test
    public void testLineCollision () {
        Line line1 = new Line(0, 0, 1, 1);
        Line line2 = new Line(0, 0, 1, 1);

        assertEquals(true, ColliderUtils.testLineCollision(line1, line2));

        Line line3 = new Line(0, 0, -1, -1);
        assertEquals(true, ColliderUtils.testLineCollision(line1, line3));
        assertEquals(true, ColliderUtils.testLineCollision(line3, line1));

        Line line4 = new Line(1, 1, 1, 1);
        assertEquals(true, ColliderUtils.testLineCollision(line1, line4));
        assertEquals(true, ColliderUtils.testLineCollision(line4, line1));

        Line line5 = new Line(1, 2, 1, 1);
        assertEquals(false, ColliderUtils.testLineCollision(line1, line5));
        assertEquals(false, ColliderUtils.testLineCollision(line5, line1));

        Line line6 = new Line(3, 5, 5, -1);
        Line line7 = new Line(3, 5, 5, 2);
        Line line8 = new Line(3, 2, 5, 2);
        Line line9 = new Line(8, 4, 5, -1);

        assertEquals(true, ColliderUtils.testLineCollision(line6, line7));
        assertEquals(true, ColliderUtils.testLineCollision(line6, line8));
        assertEquals(false, ColliderUtils.testLineCollision(line7, line8));
        assertEquals(true, ColliderUtils.testLineCollision(line6, line9));
    }

    @Test
    public void testSegmentCollision () {
        Segment segment1 = new Segment(new Vector2(0, 0), new Vector2(1, 1));
        Segment segment2 = new Segment(new Vector2(0, 0), new Vector2(1, 1));

        assertEquals(true, ColliderUtils.testSegmentCollision(segment1, segment2));
        assertEquals(true, ColliderUtils.testSegmentCollision(segment2, segment1));

        Segment segment3 = new Segment(new Vector2(2, 2), new Vector2(3, 3));
        assertEquals(false, ColliderUtils.testSegmentCollision(segment1, segment3));
        assertEquals(false, ColliderUtils.testSegmentCollision(segment1, segment3));

        Segment segment4 = new Segment(new Vector2(0.5f, 0.5f), new Vector2(0.8f, 0.8f));
        assertEquals(true, ColliderUtils.testSegmentCollision(segment1, segment4));
        assertEquals(true, ColliderUtils.testSegmentCollision(segment4, segment1));

        //Segment segment5 = new Segment(new Vector2(1, 0), new Vector2(0, 1));
        Segment segment5 = new Segment(new Vector2(1, 1), new Vector2(0, 0));
        assertEquals(true, ColliderUtils.testSegmentCollision(segment1, segment5));
        assertEquals(true, ColliderUtils.testSegmentCollision(segment5, segment1));

        Segment segment6 = new Segment(new Vector2(3, 4), new Vector2(11, 1));
        Segment segment7 = new Segment(new Vector2(8, 4), new Vector2(11, 7));
        assertEquals(false, ColliderUtils.testSegmentCollision(segment6, segment7));
        assertEquals(false, ColliderUtils.testSegmentCollision(segment7, segment6));

        Segment segment8 = new Segment(new Vector2(0, 1), new Vector2(1, 0));
        assertEquals(true, ColliderUtils.testSegmentCollision(segment1, segment8));
        assertEquals(true, ColliderUtils.testSegmentCollision(segment8, segment1));

        Segment segment9 = new Segment(new Vector2(1, 0), new Vector2(0, 1));
        assertEquals(true, ColliderUtils.testSegmentCollision(segment1, segment9));
        assertEquals(true, ColliderUtils.testSegmentCollision(segment9, segment1));
    }

}
