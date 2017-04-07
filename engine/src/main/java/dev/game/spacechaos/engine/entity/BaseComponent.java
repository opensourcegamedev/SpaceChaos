package dev.game.spacechaos.engine.entity;

import dev.game.spacechaos.engine.entity.annotation.SharableComponent;
import dev.game.spacechaos.engine.game.BaseGame;

/**
 * Created by Justin on 10.02.2017.
 */
public abstract class BaseComponent implements IComponent {

    protected BaseGame game = null;
    protected Entity entity = null;

    @Override
    public final void init (BaseGame game, Entity entity) {
        this.game = game;
        this.entity = entity;

        if (getClass().isAnnotationPresent(SharableComponent.class)) {
            //you cannot access entity on this way
            this.entity = null;
        }

        this.onInit(game, entity);
    }

    protected abstract void onInit (BaseGame game, Entity entity);

    @Override
    public void onAddedToEntity(Entity entity) {

    }

    @Override
    public void onRemovedFromEntity(Entity entity) {

    }

    @Override
    public void dispose() {

    }

}
