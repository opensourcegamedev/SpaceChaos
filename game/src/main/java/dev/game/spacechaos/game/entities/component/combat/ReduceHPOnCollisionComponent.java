package dev.game.spacechaos.game.entities.component.combat;

import dev.game.spacechaos.engine.collision.listener.CollisionListener;
import dev.game.spacechaos.engine.entity.BaseComponent;
import dev.game.spacechaos.engine.entity.Entity;
import dev.game.spacechaos.engine.entity.component.collision.CollisionComponent;
import dev.game.spacechaos.engine.game.BaseGame;

/**
 * Adds an health-reducing-component to an entity so it gets damage and later, destroyed.
 *
 * @author SpaceChaos-Team (https://github.com/opensourcegamedev/SpaceChaos/blob/master/CONTRIBUTORS.md)
 * @version 1.0.0-PreAlpha
 */
public class ReduceHPOnCollisionComponent extends BaseComponent implements CollisionListener {

    private HPComponent hpComponent = null;

    private float reduceValue = 100;

    public ReduceHPOnCollisionComponent (float reduceValue) {
        this.reduceValue = reduceValue;
    }

    @Override
    protected void onInit(BaseGame game, Entity entity) {
        CollisionComponent collisionComponent = entity.getComponent(CollisionComponent.class);
        this.hpComponent = entity.getComponent(HPComponent.class);

        if (collisionComponent == null) {
            throw new IllegalStateException("entity doesn't have an CollisionComponent.");
        }

        if (this.hpComponent == null) {
            throw new IllegalStateException("entity doesn't have an HPComponent.");
        }

        //register collision listener
        collisionComponent.addCollisionListener(this);
    }

    @Override
    public void onEnter(Entity entity, Entity otherEntity) {
        //check if the other entity is a projectile or another attacking entity
        AttackComponent attackComponent = otherEntity.getComponent(AttackComponent.class);

        if (attackComponent != null) {
            //don't reduce HP, because this is an task of the fighting system
            return;
        }

        //reduce HP
        this.hpComponent.subHP(this.reduceValue);
    }

    @Override
    public void onStay(Entity entity, Entity otherEntity) {}

    @Override
    public void onExit(Entity entity, Entity otherEntity) {}
}
