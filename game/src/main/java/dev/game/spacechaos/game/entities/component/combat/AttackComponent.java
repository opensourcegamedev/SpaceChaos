package dev.game.spacechaos.game.entities.component.combat;

import dev.game.spacechaos.engine.collision.listener.CollisionListener;
import dev.game.spacechaos.engine.entity.BaseComponent;
import dev.game.spacechaos.engine.entity.Entity;
import dev.game.spacechaos.engine.entity.component.collision.CollisionComponent;
import dev.game.spacechaos.engine.game.BaseGame;

/**
 * Created by Justin on 13.04.2017.
 */
public class AttackComponent extends BaseComponent implements CollisionListener {

    protected CollisionComponent collisionComponent = null;

    protected Entity ownerEntity = null;
    protected float reduceHP = 0;

    public AttackComponent (Entity ownerEntity, float reduceHP) {
        this.ownerEntity = ownerEntity;
        this.reduceHP = reduceHP;
    }

    @Override
    protected void onInit(BaseGame game, Entity entity) {
        this.collisionComponent = entity.getComponent(CollisionComponent.class);

        if (this.collisionComponent == null) {
            throw new IllegalStateException("entity doesnt have an CollisionComponent.");
        }

        //register collision listener
        this.collisionComponent.addCollisionListener(this);
    }

    @Override
    public void onEnter(Entity entity, Entity otherEntity) {
        if (otherEntity == ownerEntity) {
            //dont attack shuttles, which fires this entity
            return;
        }

        //reduce HP of enemy shuttle
        HPComponent hpComponent = otherEntity.getComponent(HPComponent.class);

        if (hpComponent == null) {
            //dont reduce HP, maybe its an meteorit
            return;
        }

        hpComponent.subHP(this.reduceHP);
    }

    @Override
    public void onStay(Entity entity, Entity otherEntity) {

    }

    @Override
    public void onExit(Entity entity, Entity otherEntity) {

    }
}
