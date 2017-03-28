package dev.game.spacechaos.engine.camera;

import dev.game.spacechaos.engine.time.GameTime;

/**
 * Created by Justin on 11.02.2017.
 */
public interface CameraModification {

    public void onUpdate(GameTime time, TempCameraParams position, ModificationFinishedListener listener);

    public void dispose();

}
