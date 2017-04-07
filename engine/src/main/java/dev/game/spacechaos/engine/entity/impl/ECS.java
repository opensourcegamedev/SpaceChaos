package dev.game.spacechaos.engine.entity.impl;

import dev.game.spacechaos.engine.entity.Entity;
import dev.game.spacechaos.engine.entity.IComponent;
import dev.game.spacechaos.engine.game.BaseGame;

/**
 * Created by Justin on 10.02.2017.
 */
public class ECS extends BaseECS {

    public ECS(BaseGame game) {
        super(game);
    }

    @Override protected void onEntityAdded(Entity entity) {

    }

    @Override protected void onEntityRemoved(Entity entity) {

    }

    @Override public <T extends IComponent> void onComponentAdded(Entity entity, T component, Class<T> cls) {
        //
    }

    @Override public <T extends IComponent> void onComponentRemoved(Entity entity, T component, Class<T> cls) {
        //
    }
}
