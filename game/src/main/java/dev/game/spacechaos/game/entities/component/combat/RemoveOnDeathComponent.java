package dev.game.spacechaos.game.entities.component.combat;

import dev.game.spacechaos.engine.entity.BaseComponent;
import dev.game.spacechaos.engine.entity.Entity;
import dev.game.spacechaos.engine.game.BaseGame;
import dev.game.spacechaos.game.entity.listener.HPDeathListener;

/**
 * Removes an entity from the game if it dies.
 * <p>
 * Requires a {@linkplain HPComponent health component}.
 *
 * @author SpaceChaos-Team
 *         (https://github.com/opensourcegamedev/SpaceChaos/blob/master/CONTRIBUTORS.md)
 * @since 1.0.0-PreAlpha
 */
public class RemoveOnDeathComponent extends BaseComponent implements HPDeathListener {

    @Override
    protected void onInit(BaseGame game, Entity entity) {
        HPComponent hpComponent = entity.getComponent(HPComponent.class);

        if (hpComponent == null) {
            throw new IllegalStateException("entity doesn't have an HPComponent.");
        }

        hpComponent.addDeathListener(this);
    }

    @Override
    public void onDeath(Entity causingEntity) {
        game.runOnUIThread(() -> {
            this.entity.getEntityComponentSystem().removeEntity(this.entity);
        });
    }
}
