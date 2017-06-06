package dev.game.spacechaos.game;

import dev.game.spacechaos.engine.game.ScreenBasedGame;
import dev.game.spacechaos.engine.screen.IScreen;
import dev.game.spacechaos.engine.screen.ScreenManager;
import dev.game.spacechaos.game.screen.CreditsScreen;
import dev.game.spacechaos.game.screen.GameScreen;
import dev.game.spacechaos.game.screen.GameoverScreen;
import dev.game.spacechaos.game.screen.HUDOverlayScreen;
import dev.game.spacechaos.game.screen.LoadingScreen;
import dev.game.spacechaos.game.screen.MainMenuScreen;
import dev.game.spacechaos.game.screen.SplashscreenScreen;

/**
 * This class starts the game by creating all the necessary screens and then
 * displaying the menu.
 *
 * @author SpaceChaos-Team
 *         (https://github.com/opensourcegamedev/SpaceChaos/blob/master/CONTRIBUTORS.md)
 * @since 1.0.0-PreAlpha
 */
public class Game extends ScreenBasedGame {

    private boolean debug, showSplashscreen;

    public Game(boolean debug, boolean showSplashscreen) {
        super();

        this.debug = debug;
        this.showSplashscreen = showSplashscreen;
    }

    @Override
    protected void onCreateScreens(ScreenManager<IScreen> screenManager) {
        // add game screen
        screenManager.addScreen("game", new GameScreen());
        screenManager.addScreen("hud", new HUDOverlayScreen());
        screenManager.addScreen("gameover", new GameoverScreen());
        screenManager.addScreen("menu", new MainMenuScreen());
        screenManager.addScreen("credits", new CreditsScreen());
        screenManager.addScreen("splash", new SplashscreenScreen());
        screenManager.addScreen("loading", new LoadingScreen());

        // push screen
        if (showSplashscreen)
            screenManager.push("splash");
        else
            screenManager.push("menu");

        getSharedData().put("debug", debug);

        // push HUD overlay screen (GUI)
        // screenManager.push("hud");
    }

}
