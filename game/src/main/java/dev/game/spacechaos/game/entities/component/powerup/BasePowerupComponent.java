package dev.game.spacechaos.game.entities.component.powerup;

import dev.game.spacechaos.engine.collision.listener.CollisionListener;
import dev.game.spacechaos.engine.entity.BaseComponent;
import dev.game.spacechaos.engine.entity.Entity;
import dev.game.spacechaos.engine.entity.annotation.InjectComponent;
import dev.game.spacechaos.engine.entity.component.collision.CollisionComponent;
import dev.game.spacechaos.engine.game.BaseGame;
import dev.game.spacechaos.game.entity.listener.RemoveListener;

/**
 * Adds an collision effect to entities.
 * <p>
 * Requires a {@linkplain CollisionComponent collision component}.
 *
 * @author SpaceChaos-Team
 *         (https://github.com/opensourcegamedev/SpaceChaos/blob/master/CONTRIBUTORS.md)
 * @since 1.0.2-PreAlpha
 */
public abstract class BasePowerupComponent extends BaseComponent implements CollisionListener {

    @InjectComponent(nullable = false)
    private CollisionComponent collisionComponent;
    private int uses;

    //listener, which is called, if entity is removed from ecs
    protected RemoveListener removeListener = null;

    public BasePowerupComponent() {
        this(1);
    }

    public BasePowerupComponent(int uses) {
        this.uses = uses;
    }

    @Override
    protected void onInit(BaseGame game, Entity entity) {
        collisionComponent.addCollisionListener(this);
    }

    @Override
    public void onEnter(Entity entity, Entity otherEntity) {
        if (otherEntity.getComponent(CanUsePowerUpsComponent.class) == null) {
            //this entity cannot use this component
            return;
        }

        if (onEffect(otherEntity)) {
            uses--;
            if (uses <= 0) {
                game.runOnUIThread(() -> {
                    if (this.removeListener != null) {
                        this.removeListener.onRemoveEntity(this.entity);
                    }

                    this.entity.getEntityComponentSystem().removeEntity(this.entity);
                });
            }
        }
    }

    /**
     * Executes the effect on the colliding entity.
     * 
     * @param affectedEntity
     *            The entity that collided with the powerup.
     * @return Returns true if the effect was executed.
     */
    protected abstract boolean onEffect(Entity affectedEntity);
    
    /**
    * Sets a remove listener.
     *
     * @param listener listener, which is called, if entity will be removed
    */
    public void setRemoveListener (RemoveListener listener) {
        this.removeListener = listener;
    }

    @Override
    public void onStay(Entity entity, Entity otherEntity) {

    }

    @Override
    public void onExit(Entity entity, Entity otherEntity) {

    }
}
