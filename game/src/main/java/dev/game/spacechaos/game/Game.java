package dev.game.spacechaos.game;

import com.badlogic.gdx.ApplicationAdapter;
import dev.game.spacechaos.engine.game.ScreenBasedGame;
import dev.game.spacechaos.engine.screen.IScreen;
import dev.game.spacechaos.engine.screen.ScreenManager;
import dev.game.spacechaos.game.screen.GameScreen;
import dev.game.spacechaos.game.screen.HUDOverlayScreen;

/**
 * Created by Justin on 06.02.2017.
 */
public class Game extends ScreenBasedGame {

    public Game() {
        super();
    }

    @Override
    protected void onCreateScreens(ScreenManager<IScreen> screenManager) {
        //add game screen
        screenManager.addScreen("game", new GameScreen());
        screenManager.addScreen("hud", new HUDOverlayScreen());

        //push screen
        screenManager.push("game");

        //push HUD overlay screen (GUI)
        screenManager.push("hud");
    }

}
