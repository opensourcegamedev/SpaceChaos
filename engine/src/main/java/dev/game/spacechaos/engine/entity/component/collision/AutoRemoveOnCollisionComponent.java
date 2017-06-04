package dev.game.spacechaos.engine.entity.component.collision;

import dev.game.spacechaos.engine.collision.listener.CollisionListener;
import dev.game.spacechaos.engine.entity.BaseComponent;
import dev.game.spacechaos.engine.entity.Entity;
import dev.game.spacechaos.engine.game.BaseGame;

/**
 * Component to remove entity, if entity collides with an other entity
 *
 * Created by Justin on 13.04.2017.
 */
public class AutoRemoveOnCollisionComponent extends BaseComponent implements CollisionListener {

    protected CollisionComponent collisionComponent = null;

    @Override
    protected void onInit(BaseGame game, Entity entity) {
        this.collisionComponent = entity.getComponent(CollisionComponent.class);

        if (this.collisionComponent == null) {
            throw new IllegalStateException("entity doesnt have an CollisionComponent.");
        }

        // register collision listener
        this.collisionComponent.addCollisionListener(this);
    }

    @Override
    public void onEnter(Entity entity, Entity otherEntity) {
        if (entity.getComponent(AvoidRemoveOnCollisionComponent.class) != null
                && entity.getComponent(AvoidRemoveOnCollisionComponent.class).getOwnerEntity() == otherEntity) {
            // dont remove entity
            return;
        }

        // auto remove entity on next gameloop cycle
        game.runOnUIThread(() -> {
            // remove entity from ecs
            this.entity.getEntityComponentSystem().removeEntity(this.entity);
        });
    }

    @Override
    public void onStay(Entity entity, Entity otherEntity) {

    }

    @Override
    public void onExit(Entity entity, Entity otherEntity) {

    }
}
