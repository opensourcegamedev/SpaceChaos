package dev.game.spacechaos.engine.collision.impl;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import dev.game.spacechaos.engine.camera.CameraWrapper;
import dev.game.spacechaos.engine.entity.Entity;
import dev.game.spacechaos.engine.entity.EntityManager;
import dev.game.spacechaos.engine.entity.IComponent;
import dev.game.spacechaos.engine.entity.component.PositionComponent;
import dev.game.spacechaos.engine.entity.component.collision.CollisionComponent;
import dev.game.spacechaos.engine.collision.CollisionManager;
import dev.game.spacechaos.engine.entity.listener.ComponentListener;
import dev.game.spacechaos.engine.time.GameTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Justin on 12.04.2017.
 */
public class DefaultCollisionManager implements CollisionManager, ComponentListener {

    //list with all collider components
    protected List<CollisionComponent> collisionComponentList = new ArrayList<>();

    public DefaultCollisionManager (EntityManager ecs) {
        //register component listener
        ecs.registerComponentListener(this);
    }

    @Override
    public void drawCollisionBoxes(GameTime time, CameraWrapper camera, ShapeRenderer shapeRenderer, Color color) {
        for (CollisionComponent component : this.collisionComponentList) {
            //draw collision shapes
            component.drawCollisionBoxes(time, camera, shapeRenderer, color);
        }
    }

    @Override
    public boolean checkForCollision(Entity entity, CollisionComponent collisionComponent, PositionComponent positionComponent) {
        throw new UnsupportedOperationException("method isnt implemented yet.");
    }

    @Override
    public <T extends IComponent> void onComponentAdded(Entity entity, T component, Class<T> cls) {
        if (component instanceof CollisionComponent) {
            CollisionComponent collisionComponent = (CollisionComponent) component;

            //add component to list
            this.collisionComponentList.add(collisionComponent);
        }
    }

    @Override
    public <T extends IComponent> void onComponentRemoved(Entity entity, T component, Class<T> cls) {
        if (component instanceof CollisionComponent) {
            CollisionComponent collisionComponent = (CollisionComponent) component;

            //remove component to list
            this.collisionComponentList.remove(collisionComponent);
        }
    }

}
