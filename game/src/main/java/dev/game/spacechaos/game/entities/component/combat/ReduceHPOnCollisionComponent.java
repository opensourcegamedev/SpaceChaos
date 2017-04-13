package dev.game.spacechaos.game.entities.component.combat;

import dev.game.spacechaos.engine.collision.listener.CollisionListener;
import dev.game.spacechaos.engine.entity.BaseComponent;
import dev.game.spacechaos.engine.entity.Entity;
import dev.game.spacechaos.engine.entity.component.PositionComponent;
import dev.game.spacechaos.engine.entity.component.collision.CollisionComponent;
import dev.game.spacechaos.engine.game.BaseGame;

/**
 * Created by Justin on 13.04.2017.
 */
public class ReduceHPOnCollisionComponent extends BaseComponent implements CollisionListener {

    protected CollisionComponent collisionComponent = null;
    protected HPComponent hpComponent = null;

    protected float reduceValue = 100;

    public ReduceHPOnCollisionComponent (float reduceValue) {
        this.reduceValue = reduceValue;
    }

    @Override
    protected void onInit(BaseGame game, Entity entity) {
        this.collisionComponent = entity.getComponent(CollisionComponent.class);
        this.hpComponent = entity.getComponent(HPComponent.class);

        if (this.collisionComponent == null) {
            throw new IllegalStateException("entity doesnt have an CollisionComponent.");
        }

        if (this.hpComponent == null) {
            throw new IllegalStateException("entity doesnt have an HPComponent.");
        }

        //register collision listener
        this.collisionComponent.addCollisionListener(this);
    }

    @Override
    public void onEnter(Entity entity, Entity otherEntity) {
        //check, if other entity is an projectile or other attacking entity
        AttackComponent attackComponent = otherEntity.getComponent(AttackComponent.class);

        if (attackComponent != null) {
            //dont reduce HP, because this is an task of fighting system
            return;
        }

        //reduce HP
        this.hpComponent.subHP(this.reduceValue);
    }

    @Override
    public void onStay(Entity entity, Entity otherEntity) {

    }

    @Override
    public void onExit(Entity entity, Entity otherEntity) {

    }
}
