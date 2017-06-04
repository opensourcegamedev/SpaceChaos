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
import java.util.Collection;
import java.util.List;

/**
 * Created by Justin on 12.04.2017.
 */
public class DefaultCollisionManager implements CollisionManager, ComponentListener {

    // list with all collider components
    protected List<CollisionComponent> collisionComponentList = new ArrayList<>();

    protected List<Entity> tmpList = new ArrayList<>();

    public DefaultCollisionManager(EntityManager ecs) {
        // register component listener
        ecs.registerComponentListener(this);
    }

    @Override
    public void drawCollisionBoxes(GameTime time, CameraWrapper camera, ShapeRenderer shapeRenderer, Color color,
            Color inCollisionColor) {
        for (CollisionComponent component : this.collisionComponentList) {
            // draw collision shapes
            component.drawCollisionBoxes(time, camera, shapeRenderer, color, inCollisionColor);
        }
    }

    @Override
    public Collection<Entity> checkForCollision(Entity entity, CollisionComponent collisionComponent,
            PositionComponent positionComponent) {
        this.tmpList.clear();

        // TODO: use quadtree instead

        for (CollisionComponent collisionComponent1 : this.collisionComponentList) {
            Entity entity1 = collisionComponent1.getEntity();

            if (entity == entity1) {
                // its own entity
                continue;
            }

            // check for collision
            if (collisionComponent1.overlaps(collisionComponent)) {
                // collision detected
                tmpList.add(collisionComponent1.getEntity());
            }
        }

        return this.tmpList;
    }

    @Override
    public <T extends IComponent> void onComponentAdded(Entity entity, T component, Class<T> cls) {
        if (component instanceof CollisionComponent) {
            CollisionComponent collisionComponent = (CollisionComponent) component;

            // set collision manager
            collisionComponent.initCollider(this);

            // add component to list
            this.collisionComponentList.add(collisionComponent);

            // TODO: add position changed listener for quadtree
        }
    }

    @Override
    public <T extends IComponent> void onComponentRemoved(Entity entity, T component, Class<T> cls) {
        if (component instanceof CollisionComponent) {
            CollisionComponent collisionComponent = (CollisionComponent) component;

            // remove component to list
            this.collisionComponentList.remove(collisionComponent);
        }
    }

}
