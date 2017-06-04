package dev.game.spacechaos.engine.collision;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import dev.game.spacechaos.engine.camera.CameraWrapper;
import dev.game.spacechaos.engine.entity.Entity;
import dev.game.spacechaos.engine.entity.component.PositionComponent;
import dev.game.spacechaos.engine.entity.component.collision.CollisionComponent;
import dev.game.spacechaos.engine.time.GameTime;

import java.util.Collection;

/**
 * Created by Justin on 12.04.2017.
 */
public interface CollisionManager {

    public void drawCollisionBoxes(GameTime time, CameraWrapper camera, ShapeRenderer shapeRenderer, Color color,
            Color inCollisionColor);

    /**
     * list all entities, which are colliding with this entity
     */
    public Collection<Entity> checkForCollision(Entity entity, CollisionComponent collisionComponent,
            PositionComponent positionComponent);

}
