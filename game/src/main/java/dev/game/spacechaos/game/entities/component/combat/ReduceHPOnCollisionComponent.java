package dev.game.spacechaos.game.entities.component.combat;

import dev.game.spacechaos.engine.collision.listener.CollisionListener;
import dev.game.spacechaos.engine.entity.BaseComponent;
import dev.game.spacechaos.engine.entity.Entity;
import dev.game.spacechaos.engine.entity.component.collision.CollisionComponent;
import dev.game.spacechaos.engine.game.BaseGame;

/**
 * Reduces an entities health upon collision.
 *
 * @author SpaceChaos-Team
 *         (https://github.com/opensourcegamedev/SpaceChaos/blob/master/CONTRIBUTORS.md)
 * @since 1.0.0-PreAlpha
 */
public class ReduceHPOnCollisionComponent extends BaseComponent implements CollisionListener {

    private HPComponent hpComponent = null;

    private float reduceValue = 100;

    // time, when player enters collision
    private long collisionStartTime = 0;

    // interval, after an collision will be entered again, also if player also
    // is in collision
    private long collisionInterval = 0;

    private float lastIntervalHPReduce = 0;

    // quick & dirty fix
    private boolean entered = false;

    public ReduceHPOnCollisionComponent(float reduceValue, long collisionInterval) {
        this.reduceValue = reduceValue;
        this.collisionInterval = collisionInterval;
    }

    public ReduceHPOnCollisionComponent(float reduceValue) {
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

        // register collision listener
        collisionComponent.addCollisionListener(this);
    }

    @Override
    public void onEnter(Entity entity, Entity otherEntity) {
        // set flag
        entered = true;

        // check if the other entity is a projectile or another attacking entity
        AttackComponent attackComponent = otherEntity.getComponent(AttackComponent.class);

        if (attackComponent != null) {
            // don't reduce HP, because this is an task of the fighting system
            return;
        }

        // reduce HP
        this.hpComponent.subHP(this.reduceValue);

        if (this.collisionStartTime == 0) {
            // set new collision enter time
            this.collisionStartTime = System.currentTimeMillis();
        }
    }

    @Override
    public void onStay(Entity entity, Entity otherEntity) {
        if (!entered) {
            return;
        }

        // check if the other entity is a projectile or another attacking entity
        AttackComponent attackComponent = otherEntity.getComponent(AttackComponent.class);

        if (attackComponent != null) {
            // don't reduce HP, because this is an task of the fighting system
            return;
        }

        if (collisionInterval > 0 && this.collisionStartTime > 0) {
            long a = System.currentTimeMillis() - this.collisionStartTime;

            if (a > collisionInterval) {
                // reduce HP
                this.hpComponent.subHP(this.reduceValue);

                this.collisionStartTime = System.currentTimeMillis();
                this.lastIntervalHPReduce = System.currentTimeMillis();
            }

            /*
             * if ((this.collisionStartTime + collisionInterval) <
             * System.currentTimeMillis()) { if ((lastIntervalHPReduce +
             * collisionInterval) > System.currentTimeMillis()) { return; }
             * 
             * System.out.println("reduce HP.");
             * 
             * //reduce HP this.hpComponent.subHP(this.reduceValue);
             * 
             * this.collisionStartTime = System.currentTimeMillis();
             * this.lastIntervalHPReduce = System.currentTimeMillis(); }
             */
        }
    }

    @Override
    public void onExit(Entity entity, Entity otherEntity) {
        // reset collision enter time
        // this.collisionStartTime = 0;

        // reset flag
        entered = false;
    }
}
