package dev.game.spacechaos.engine.hud.actionbar;

import dev.game.spacechaos.engine.game.BaseGame;
import dev.game.spacechaos.engine.time.GameTime;

/**
 * Created by Justin on 06.03.2017.
 */
public interface CustomHoverAdapter {

    public boolean isHovered(BaseGame game, ActionBarItem item, GameTime time);

}
