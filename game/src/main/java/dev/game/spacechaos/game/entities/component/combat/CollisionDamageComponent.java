package dev.game.spacechaos.game.entities.component.combat;

import dev.game.spacechaos.engine.collision.listener.CollisionListener;
import dev.game.spacechaos.engine.entity.BaseComponent;
import dev.game.spacechaos.engine.entity.Entity;
import dev.game.spacechaos.engine.entity.component.collision.CollisionComponent;
import dev.game.spacechaos.engine.game.BaseGame;

/**
 * Adds an projectile-component to entities which deal damage to another entity
 * on collision.
 *
 * @author SpaceChaos-Team
 *         (https://github.com/opensourcegamedev/SpaceChaos/blob/master/CONTRIBUTORS.md)
 * @since 1.0.0-PreAlpha
 */
public class CollisionDamageComponent extends BaseComponent implements CollisionListener {

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

    public CollisionDamageComponent(float damage, boolean removeOnCollision, Entity ownerEntity, boolean ignoreShield) {
        this.ownerEntity = ownerEntity;
        this.removeOnCollision = removeOnCollision;
        this.damage = damage;
        this.ignoreShield = ignoreShield;
    }

    public CollisionDamageComponent(float damage, boolean removeOnCollision, Entity ownerEntity) {
        this(damage, removeOnCollision, ownerEntity, false);
    }

    public CollisionDamageComponent(float damage, boolean removeOnCollision) {
        this(damage, removeOnCollision, null);
    }

    public CollisionDamageComponent(float damage) {
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
        // don't attack the owning entities, i.e. a shuttle shooting a
        // projectile
        if (otherEntity == ownerEntity) {
            return;
        }

        dealDamage(otherEntity);

        if (removeOnCollision) {
            game.runOnUIThread(() -> {
                this.entity.getEntityComponentSystem().removeEntity(this.entity);
            });
        } else {
            
        }
    }

    private void dealDamage(Entity entity) {
        HPComponent hpComponent = entity.getComponent(HPComponent.class);
        if (hpComponent == null) {
            // HP can't be reduced
            return;
        }

        ShieldComponent shieldComponent = entity.getComponent(ShieldComponent.class);

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
    }

    @Override
    public void onStay(Entity entity, Entity otherEntity) {

    }

    @Override
    public void onExit(Entity entity, Entity otherEntity) {

    }
}
