package dev.game.spacechaos.game.entities.component.collision;

import dev.game.spacechaos.engine.collision.listener.CollisionListener;
import dev.game.spacechaos.engine.entity.BaseComponent;
import dev.game.spacechaos.engine.entity.Entity;
import dev.game.spacechaos.engine.entity.component.collision.CollisionComponent;
import dev.game.spacechaos.engine.game.BaseGame;
import dev.game.spacechaos.game.entities.component.combat.HPComponent;
import dev.game.spacechaos.game.entities.component.combat.ShieldComponent;

/**
 * Adds a component to entities which deal damage to another entity on
 * collision.
 *
 * @author SpaceChaos-Team
 *         (https://github.com/opensourcegamedev/SpaceChaos/blob/master/CONTRIBUTORS.md)
 * @since 1.0.0-PreAlpha
 */
public class DealDamageOnCollisionComponent extends BaseComponent implements CollisionListener {

    private Entity ownerEntity = null;
    private float damage = 0;
    /**
     * Denotes if the damage of this entity should ignore shields.
     */
    private boolean ignoreShield;
    /**
     * Denotes if the entity containing this component should get removed after
     * collision.
     */
    private boolean removeOnCollision;
    private int timer = 0;

    private long lastCollisionTime = 0;
    private long damageInterval = 0;

    public DealDamageOnCollisionComponent(float damage, boolean removeOnCollision, Entity ownerEntity,
            boolean ignoreShield, long damageInterval) {
        this.ownerEntity = ownerEntity;
        this.removeOnCollision = removeOnCollision;
        this.damage = damage;
        this.ignoreShield = ignoreShield;
        this.damageInterval = damageInterval;
    }

    public DealDamageOnCollisionComponent(float damage, boolean removeOnCollision, Entity ownerEntity,
            boolean ignoreShield) {
        this(damage, removeOnCollision, ownerEntity, ignoreShield, 0);
    }

    public DealDamageOnCollisionComponent(float damage, boolean removeOnCollision, Entity ownerEntity) {
        this(damage, removeOnCollision, ownerEntity, false);
    }

    public DealDamageOnCollisionComponent(float damage, boolean removeOnCollision) {
        this(damage, removeOnCollision, null);
    }

    public DealDamageOnCollisionComponent(float damage) {
        this(damage, true);
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
        if (damageInterval == 0) {
            dealDamage(entity, otherEntity);
        }
    }

    @Override
    public void onStay(Entity entity, Entity otherEntity) {
        if (damageInterval > 0) {
            long a = System.currentTimeMillis() - this.lastCollisionTime;

            if (a > damageInterval) {

                if (dealDamage(entity, otherEntity)) {
                    this.lastCollisionTime = System.currentTimeMillis();
                }
            }

        }

    }

    private boolean dealDamage(Entity entity, Entity otherEntity) {
        // don't attack the owning entities, i.e. a shuttle shooting a
        // projectile
        if (otherEntity == ownerEntity) {
            return false;
        }

        // Deal the damage
        HPComponent hpComponent = otherEntity.getComponent(HPComponent.class);
        if (hpComponent == null) {
            // HP can't be reduced
            return false;
        }

        ShieldComponent shieldComponent = otherEntity.getComponent(ShieldComponent.class);

        float tmp = this.damage;

        if (!ignoreShield) {
            if (shieldComponent != null) {
                tmp -= shieldComponent.getCurrentShieldHP();
                shieldComponent.subShieldHP(this.damage);
            }
        }

        if (tmp > 0) {
            // reduce HP
            hpComponent.subHP(tmp, ownerEntity);
        }

        // Remove the entity containing this component if necessary
        if (removeOnCollision) {
            game.runOnUIThread(() -> {
                this.entity.getEntityComponentSystem().removeEntity(this.entity);
            });
        }

        return true;
    }

    @Override
    public void onExit(Entity entity, Entity otherEntity) {

    }
}
