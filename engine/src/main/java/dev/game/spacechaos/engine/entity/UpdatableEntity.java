package dev.game.spacechaos.engine.entity;

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
     *            The instance of the game.
     * @param camera
     *            The current camera.
     * @param time
     *            The game time.
     */
    public void update(BaseGame game, CameraWrapper camera, GameTime time);

}
