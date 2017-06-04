package dev.game.spacechaos.game.entities.component.combat;

import dev.game.spacechaos.engine.entity.BaseComponent;
import dev.game.spacechaos.engine.entity.Entity;
import dev.game.spacechaos.engine.entity.listener.HPDeathListener;
import dev.game.spacechaos.engine.game.BaseGame;

/**
 * Adds an remove-component to an entity, so if it gets destroyed, it will be
 * removed from the game.
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
