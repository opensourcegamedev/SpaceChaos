package dev.game.spacechaos.engine.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dev.game.spacechaos.engine.camera.CameraWrapper;
import dev.game.spacechaos.engine.entity.priority.ECSPriority;
import dev.game.spacechaos.engine.time.GameTime;

/**
 * Created by Justin on 10.02.2017.
 */
public interface IDrawComponent extends IComponent {

    public void draw(GameTime time, CameraWrapper camera, SpriteBatch batch);

    public ECSPriority getDrawOrder();

}
