package dev.game.spacechaos.game.entities.component.combat;

import dev.game.spacechaos.engine.entity.BaseComponent;
import dev.game.spacechaos.engine.entity.Entity;
import dev.game.spacechaos.engine.entity.listener.HPHitListener;
import dev.game.spacechaos.engine.game.BaseGame;
import dev.game.spacechaos.game.entities.listener.EntityRemoveListener;

/**
 * Adds an remove-component to an entity, so if it gets destroyed, it will be removed from the game.
 *
 * @author SpaceChaos-Team (https://github.com/opensourcegamedev/SpaceChaos/blob/master/CONTRIBUTORS.md)
 * @since 1.0.0-PreAlpha
 */
public class RemoveOnHitComponent extends BaseComponent implements HPHitListener {

    private EntityRemoveListener listener = null;

    public RemoveOnHitComponent (EntityRemoveListener listener) {
        this.listener = listener;
    }

    public RemoveOnHitComponent () {
        //
    }

    @Override
    protected void onInit(BaseGame game, Entity entity) {
        HPComponent hpComponent = entity.getComponent(HPComponent.class);

        if (hpComponent == null) {
            throw new IllegalStateException("entity doesn't have an HPComponent.");
        }

        hpComponent.addHitListener(this);
    }

    @Override
    public void onHit(float oldValue, float newValue, float maxHP) {
        if (this.listener != null) {
            this.listener.beforeRemove(this.entity);
        }

        //remove entity
        game.runOnUIThread(() -> {
            //remove entity from ecs
            this.entity.getEntityComponentSystem().removeEntity(this.entity);
        });
    }
}
