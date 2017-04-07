package dev.game.spacechaos.engine.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dev.game.spacechaos.engine.camera.CameraWrapper;
import dev.game.spacechaos.engine.entity.priority.ECSPriority;
import dev.game.spacechaos.engine.time.GameTime;

/**
 * Created by Justin on 12.02.2017.
 */
public interface IDrawUILayerComponent extends IComponent {

    public void drawUILayer(GameTime time, CameraWrapper camera, SpriteBatch batch);

    public ECSPriority getUILayerDrawOrder();

}
