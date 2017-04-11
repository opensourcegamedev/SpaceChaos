package dev.game.spacechaos.engine.utils;

import com.badlogic.gdx.math.Vector2;
import dev.game.spacechaos.engine.collision.Line;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Justin on 11.04.2017.
 */
public class FastMathTest {

    @Test
    public void testRotateVector90 () {
        Vector2 v1 = new Vector2(1, 0);
        Vector2 expected = new Vector2(0, 1);

        Vector2 rotatedVector = FastMath.rotateVector90(v1);
        assertEquals("wrong rotated vector calculation, expected: " + expected + ", calculated vector: " + rotatedVector, true, (expected.x == rotatedVector.x && expected.y == rotatedVector.y));
    }

    @Test
    public void testCheckVectorsParallel () {
        Vector2 v1 = new Vector2(1, 1);
        Vector2 v2 = new Vector2(1, 1);

        assertEquals(true, FastMath.checkVectorsParallel(v1, v2));
        assertEquals(true, FastMath.checkVectorsParallel(v2, v1));

        Vector2 v3 = new Vector2(1, 2);
        assertEquals(false, FastMath.checkVectorsParallel(v1, v3));
        assertEquals(false, FastMath.checkVectorsParallel(v3, v1));

        Vector2 v4 = new Vector2(2, 2);
        assertEquals(true, FastMath.checkVectorsParallel(v1, v4));
        assertEquals(true, FastMath.checkVectorsParallel(v4, v1));
    }

    @Test
    public void testCheckIfLinesAreEquals () {
        Line line1 = new Line(0, 0, 1, 1);
        Line line2 = new Line(0, 0, 1, 1);

        assertEquals(true, FastMath.checkIfLinesAreEquals(line1, line2));
        assertEquals(true, FastMath.checkIfLinesAreEquals(line2, line1));

        Line line3 = new Line(1, 1, 1, 1);
        assertEquals(true, FastMath.checkIfLinesAreEquals(line1, line3));
        assertEquals(true, FastMath.checkIfLinesAreEquals(line3, line1));

        Line line4 = new Line(0, 0, -1, 1);
        assertEquals(false, FastMath.checkIfLinesAreEquals(line1, line4));
        assertEquals(false, FastMath.checkIfLinesAreEquals(line4, line1));

        Line line5 = new Line(0, 0, 2, 2);
        assertEquals(true, FastMath.checkIfLinesAreEquals(line1, line5));
        assertEquals(true, FastMath.checkIfLinesAreEquals(line5, line1));
    }

}
