package dev.game.spacechaos.engine.entity.component.collision;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import dev.game.spacechaos.engine.camera.CameraWrapper;
import dev.game.spacechaos.engine.time.GameTime;

/**
 * Created by Justin on 12.04.2017.
 */
public interface CollisionManager {

    public void drawCollisionBoxes (GameTime time, CameraWrapper camera, ShapeRenderer shapeRenderer, Color color);

}
