package dev.game.spacechaos.engine.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;
import dev.game.spacechaos.engine.camera.CameraWrapper;

/**
 * Created by Justin on 12.02.2017.
 */
public class MouseUtils {

    protected static Vector3 tmpVector = new Vector3(0, 0, 0);

    public static float getMouseX (float x, CameraWrapper camera) {
        return (x + camera.getX()) * 1 / camera.getZoom();/* - (viewportWidth / 2)*/
    }

    public static float getMouseY (float y, CameraWrapper camera) {
        //y = camera.getOriginalCamera().viewportHeight - y;
        return (y + camera.getY()) * 1 / camera.getZoom();
    }

    @Deprecated
    public static Vector3 getMousePositionWithCamera (Camera camera) {
        tmpVector.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        return camera.unproject(tmpVector);
    }

    public Vector3 getMousePositionWithCamera (CameraWrapper camera) {
        return camera.getMousePosition();
    }

}
