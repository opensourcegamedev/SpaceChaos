package dev.game.spacechaos.engine.entity.outdated;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dev.game.spacechaos.engine.camera.CameraWrapper;
import dev.game.spacechaos.engine.time.GameTime;

/**
 * Created by Justin on 29.03.2017.
 */
public interface DrawableEntity {

    /**
    * draw entity
     *
     * @param time current game time
     * @param camera game camera
     * @param batch sprite batcher to draw images and so on
    */
    public void draw (GameTime time, CameraWrapper camera, SpriteBatch batch);

}
