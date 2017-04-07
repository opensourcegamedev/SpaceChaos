package dev.game.spacechaos.engine.entity;

import dev.game.spacechaos.engine.game.BaseGame;

/**
 * Created by Justin on 10.02.2017.
 */
public interface IComponent {

    public void init(BaseGame game, Entity entity);

    public void onAddedToEntity(Entity entity);

    public void onRemovedFromEntity(Entity entity);

    public void dispose();

}
