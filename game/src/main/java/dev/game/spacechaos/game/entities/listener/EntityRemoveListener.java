package dev.game.spacechaos.game.entities.listener;

import dev.game.spacechaos.engine.entity.Entity;

/**
 *
 * @author SpaceChaos-Team (https://github.com/opensourcegamedev/SpaceChaos/blob/master/CONTRIBUTORS.md)
 * @since 1.0.0-PreAlpha
 */
public interface EntityRemoveListener {
    void beforeRemove(Entity entity);
}
