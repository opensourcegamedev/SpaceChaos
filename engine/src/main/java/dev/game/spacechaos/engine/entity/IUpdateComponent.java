package dev.game.spacechaos.engine.entity;

import dev.game.spacechaos.engine.entity.priority.ECSPriority;
import dev.game.spacechaos.engine.game.BaseGame;
import dev.game.spacechaos.engine.time.GameTime;

/**
 * Created by Justin on 10.02.2017.
 */
public interface IUpdateComponent {

    /**
     * update
     */
    public void update(BaseGame game, GameTime time);

    public ECSPriority getUpdateOrder();

}
