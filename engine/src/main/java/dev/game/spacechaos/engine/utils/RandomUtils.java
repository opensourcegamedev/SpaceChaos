package dev.game.spacechaos.engine.utils;

/**
 * Created by Justin on 17.04.2017.
 */
public class RandomUtils {

    public static int getRandomNumber (int min, int max) {
        return (int) (Math.random() * (max - min)) + min;
    }

}
