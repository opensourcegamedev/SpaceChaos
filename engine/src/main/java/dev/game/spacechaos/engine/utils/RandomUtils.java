package dev.game.spacechaos.engine.utils;

/**
 * A few utility methods for dealing with random numbers and chances.
 * 
 * @since 1.0.0-PreAlpha
 */
public class RandomUtils {

    /**
     * Generates a random number within the specified range.
     * 
     * @param min
     *            Included minimal value.
     * @param max
     *            Included maximum value.
     * @return The random integer.
     */
    public static int getRandomNumber(int min, int max) {
        return (int) (Math.random() * (max - min)) + min;
    }

    /**
     * Rolls the dice and returns true with the chance {@code 1/x }
     * 
     * @param x
     *            The reciprocal of the chance.
     * @return The random integer.
     */
    public static boolean rollTheDice(int x) {
        return getRandomNumber(1, x) == 1;
    }

}
