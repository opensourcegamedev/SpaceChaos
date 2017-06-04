package dev.game.spacechaos.game;

import dev.game.spacechaos.engine.game.ScreenBasedGame;
import dev.game.spacechaos.engine.screen.IScreen;
import dev.game.spacechaos.engine.screen.ScreenManager;
import dev.game.spacechaos.game.screen.*;

/**
 * This class starts the game by creating all the necessary screens and then
 * displaying the menu.
 *
 * @author SpaceChaos-Team
 *         (https://github.com/opensourcegamedev/SpaceChaos/blob/master/CONTRIBUTORS.md)
 * @since 1.0.0-PreAlpha
 */
public class Game extends ScreenBasedGame {

    public Game() {
        super();
    }

    @Override
    protected void onCreateScreens(ScreenManager<IScreen> screenManager) {
        // add game screen
        screenManager.addScreen("game", new GameScreen());
        screenManager.addScreen("hud", new HUDOverlayScreen());
        screenManager.addScreen("gameover", new GameoverScreen());
        screenManager.addScreen("menu", new MainMenuScreen());
        screenManager.addScreen("credits", new CreditsScreen());

        // push screen
        screenManager.push("menu");

        // push HUD overlay screen (GUI)
        // screenManager.push("hud");
    }

}
