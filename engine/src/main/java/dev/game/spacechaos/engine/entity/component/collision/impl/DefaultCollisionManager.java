package dev.game.spacechaos.engine.entity.component.collision.impl;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import dev.game.spacechaos.engine.camera.CameraWrapper;
import dev.game.spacechaos.engine.entity.EntityManager;
import dev.game.spacechaos.engine.entity.component.collision.CollisionComponent;
import dev.game.spacechaos.engine.entity.component.collision.CollisionManager;
import dev.game.spacechaos.engine.time.GameTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Justin on 12.04.2017.
 */
public class DefaultCollisionManager implements CollisionManager {

    //list with all collider components
    protected List<CollisionComponent> collisionComponentList = new ArrayList<>();

    public DefaultCollisionManager (EntityManager ecs) {
        //
    }

    @Override
    public void drawCollisionBoxes(GameTime time, CameraWrapper camera, ShapeRenderer shapeRenderer, Color color) {
        for (CollisionComponent component : this.collisionComponentList) {
            //draw collision shapes
            component.drawCollisionBoxes(time, camera, shapeRenderer, color);
        }
    }

}
