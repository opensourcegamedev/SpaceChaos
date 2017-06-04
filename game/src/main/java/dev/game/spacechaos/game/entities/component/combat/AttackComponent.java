package dev.game.spacechaos.game.entities.component.combat;

import dev.game.spacechaos.engine.collision.listener.CollisionListener;
import dev.game.spacechaos.engine.entity.BaseComponent;
import dev.game.spacechaos.engine.entity.Entity;
import dev.game.spacechaos.engine.entity.component.collision.CollisionComponent;
import dev.game.spacechaos.engine.game.BaseGame;

/**
 * Adds an attack-component to entities which are able to be shot and thus deal
 * damage on collision.
 *
 * @author SpaceChaos-Team
 *         (https://github.com/opensourcegamedev/SpaceChaos/blob/master/CONTRIBUTORS.md)
 * @since 1.0.0-PreAlpha
 */
public class AttackComponent extends BaseComponent implements CollisionListener {

    private Entity ownerEntity = null;
    private float reduceHP = 0;

    public AttackComponent(Entity ownerEntity, float reduceHP) {
        this.ownerEntity = ownerEntity;
        this.reduceHP = reduceHP;
    }

    @Override
    protected void onInit(BaseGame game, Entity entity) {
        CollisionComponent collisionComponent = entity.getComponent(CollisionComponent.class);

        if (collisionComponent == null) {
            throw new IllegalStateException("entity doesn't have an CollisionComponent.");
        }

        // register collision listener
        collisionComponent.addCollisionListener(this);
    }

    @Override
    public void onEnter(Entity entity, Entity otherEntity) {
        // don't attack shuttles, which fires this entity
        if (otherEntity == ownerEntity) {
            return;
        }

        HPComponent hpComponent = otherEntity.getComponent(HPComponent.class);
        if (hpComponent == null) {
            // don't reduce HP, maybe its an meteorite
            return;
        }

        hpComponent.subHP(this.reduceHP, entity);
    }

    @Override
    public void onStay(Entity entity, Entity otherEntity) {

    }

    @Override
    public void onExit(Entity entity, Entity otherEntity) {

    }
}
