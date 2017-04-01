package dev.game.spacechaos.game.camera;

import dev.game.spacechaos.engine.camera.CameraWrapper;
import dev.game.spacechaos.engine.game.BaseGame;

/**
 * Created by Justin on 29.03.2017.
 */
public class SmoothCamera {

    private SmoothCamera() {
        //
    }

    public static void update(BaseGame game, CameraWrapper camera, float targetX, float targetY, float lerp) {
        //get screen resolution
        float screenWidth = game.getViewportWidth();
        float screenHeight = game.getViewportHeight();

        //calculate camera middle
        float currentCameraMiddleX = game.getCamera().getX() + (screenWidth / 2);
        float currentCameraMiddleY = game.getCamera().getY() + (screenHeight / 2);

        //calculate differents
        float deltaX = targetX - currentCameraMiddleX;
        float deltaY = targetY - currentCameraMiddleY;

        if (Math.abs(deltaX) < 1) {
            deltaX = 0;
        }

        if (Math.abs(deltaY) < 1) {
            deltaY = 0;
        }

        float newCameraX = currentCameraMiddleX + (deltaX * lerp);
        float newCameraY = currentCameraMiddleY + (deltaY * lerp);

        //game.getCamera().setPosition(newCameraX, newCameraY);
        game.getCamera().setPosition(newCameraX - (game.getViewportWidth() / 2), newCameraY - (game.getViewportHeight() / 2));
    }

}
