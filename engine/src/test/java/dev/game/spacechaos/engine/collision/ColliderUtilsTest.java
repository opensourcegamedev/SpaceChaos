package dev.game.spacechaos.engine.collision;

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

}
