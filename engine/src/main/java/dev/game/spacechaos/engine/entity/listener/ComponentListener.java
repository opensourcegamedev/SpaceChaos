package dev.game.spacechaos.engine.entity.listener;

import dev.game.spacechaos.engine.entity.Entity;
import dev.game.spacechaos.engine.entity.IComponent;

/**
 * The listener interface for receiving component events.
 */
public interface ComponentListener {

    public <T extends IComponent> void onComponentAdded(Entity entity, T component, Class<T> cls);

    public <T extends IComponent> void onComponentRemoved(Entity entity, T component, Class<T> cls);

}
