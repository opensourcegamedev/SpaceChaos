package dev.game.spacechaos.engine.utils;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Justin on 21.04.2017.
 */
public class SpawnUtils {

    protected static Vector2 tmpVector = new Vector2(0, 0);

    public static Vector2 getRandomSpawnPosition(float minDistance, float maxDistance, float offsetX, float offsetY) {
        Vector2 pos = getRandomSpawnPosition(minDistance, maxDistance);

        pos.x += offsetX;
        pos.y = offsetY;

        return pos;
    }

    public static Vector2 getRandomSpawnPosition(float minDistance, float maxDistance) {
        if (minDistance < 0) {
            throw new IllegalArgumentException("minDistance has to be >= 0.");
        }

        if (maxDistance < minDistance) {
            throw new IllegalArgumentException("maxDistance has to be >= minDistance.");
        }

        // calculate new random distance
        float randomDistance = (float) (Math.random() * (maxDistance - minDistance) + minDistance);

        // get new random angle
        float angle = (float) Math.random() * 360;

        // set length and angle of vector
        tmpVector.set(randomDistance, 0);
        tmpVector.setLength(randomDistance);
        tmpVector.setAngle(angle);

        return tmpVector;
    }

}
