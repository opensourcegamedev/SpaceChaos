package dev.game.spacechaos.engine.entity.outdated;

import dev.game.spacechaos.engine.camera.CameraWrapper;
import dev.game.spacechaos.engine.game.BaseGame;
import dev.game.spacechaos.engine.time.GameTime;

/**
 * Created by Justin on 29.03.2017.
 */
public interface UpdatableEntity {

    /**
     * update entity
     *
     * @param game
     *            instance of game
     * @param camera
     *            current game camera
     * @param time
     *            current game time
     */
    public void update(BaseGame game, CameraWrapper camera, GameTime time);

}
