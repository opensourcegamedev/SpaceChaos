package dev.game.spacechaos.game.entities.component.combat;

import dev.game.spacechaos.engine.collision.listener.CollisionListener;
import dev.game.spacechaos.engine.entity.BaseComponent;
import dev.game.spacechaos.engine.entity.Entity;
import dev.game.spacechaos.engine.entity.annotation.InjectComponent;
import dev.game.spacechaos.engine.entity.component.collision.CollisionComponent;
import dev.game.spacechaos.engine.game.BaseGame;

/**
 * Reduces an entities health upon collision.
 *
 * @author SpaceChaos-Team
 *         (https://github.com/opensourcegamedev/SpaceChaos/blob/master/CONTRIBUTORS.md)
 * @since 1.0.0-PreAlpha
 */
public class GetDamagedOnCollisionComponent extends BaseComponent implements CollisionListener {

    @InjectComponent(nullable = false)
    private HPComponent hpComponent = null;

    @InjectComponent
    private ShieldComponent shieldComponent = null;

    @InjectComponent(nullable = false)
    private CollisionComponent collisionComponent = null;
    
    private float reduceValue = 100;

    // time, when player enters collision
    private long collisionStartTime = 0;

    // interval, after an collision will be entered again, also if player also
    // is in collision
    private long collisionInterval = 0;

    private float lastIntervalHPReduce = 0;

    // quick & dirty fix
    private boolean entered = false;

    public GetDamagedOnCollisionComponent(float reduceValue, long collisionInterval) {
        this.reduceValue = reduceValue;
        this.collisionInterval = collisionInterval;
    }

    public GetDamagedOnCollisionComponent(float reduceValue) {
        this.reduceValue = reduceValue;
    }

    @Override
    protected void onInit(BaseGame game, Entity entity) {
        // register collision listener
        collisionComponent.addCollisionListener(this);
    }

    @Override
    public void onEnter(Entity entity, Entity otherEntity) {
        // set flag
        entered = true;

        // check if the other entity is a projectile or another attacking entity
        ProjectileComponent attackComponent = otherEntity.getComponent(ProjectileComponent.class);

        if (attackComponent != null) {
            // don't reduce HP, because this is an task of the fighting system
            return;
        }

        float tmp = this.reduceValue;

        if (shieldComponent != null) {
            tmp -= shieldComponent.getCurrentShieldHP();
            shieldComponent.subShieldHP(this.reduceValue);
        }

        if (tmp > 0) {
            if (this.hpComponent != null) {
                // reduce HP
                this.hpComponent.subHP(tmp);
            } else {
                System.err.println("GetDamagedOnCollisionComponent: entity doesnt have an HPComponent.");
            }
        }

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
        ProjectileComponent attackComponent = otherEntity.getComponent(ProjectileComponent.class);

        if (attackComponent != null) {
            // don't reduce HP, because this is an task of the fighting system
            return;
        }

        if (collisionInterval > 0 && this.collisionStartTime > 0) {
            long a = System.currentTimeMillis() - this.collisionStartTime;

            if (a > collisionInterval) {
                // reduce HP
                float tmp = this.reduceValue;

                if (shieldComponent != null) {
                    tmp -= shieldComponent.getCurrentShieldHP();
                    shieldComponent.subShieldHP(this.reduceValue);
                }

                if (tmp > 0) {
                    // reduce HP
                    this.hpComponent.subHP(tmp);
                }

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
