package dev.game.spacechaos.desktop;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import dev.game.spacechaos.engine.utils.FileUtils;
import dev.game.spacechaos.game.Game;
import dev.game.spacechaos.game.utils.MicroOptions;

/**
 * Starts the application for the desktop-based builds.
 *
 * @author SpaceChaos-Team
 *         (https://github.com/opensourcegamedev/SpaceChaos/blob/master/CONTRIBUTORS.md)
 * @since 1.0.0-PreAlpha
 */
public class DesktopLauncher {

    /**
     * The start-method for the whole application which is creating a new
     * configuration for the stage and a new game so one could play.
     *
     * @param args
     *            The start arguments.
     */
    public static void main(String[] args) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "Space Chaos";
        config.height = 720;
        config.width = 1280;
        // config.addIcon("./data/icons/icon.png", Files.FileType.Absolute);
        
        boolean debug = false;
        if(args != null && args.length > 0){
            if(args[0].equalsIgnoreCase("debug")){
                debug = true;
            }
        }
        
        MicroOptions options = new MicroOptions();
        options.option("novid").describedAs("no splashscreen").isUnary();
        options.option("debug").describedAs("enables debugmode").isUnary();
        try {
            options.parse(args);
        } catch (MicroOptions.OptionException e) {
            System.err.println("Usage:");
            System.err.println(options.usageString());
            System.exit(-1);
        }
        
        //options.getArg("file", "/tmp/out");

        try {
            // start game
            new LwjglApplication(new Game(options.has("debug"), !options.has("novid")), config);
        } catch (Exception e) {
            e.printStackTrace();

            try {
                // write crash dump
                FileUtils.writeFile("./crash.log", e.getLocalizedMessage(), StandardCharsets.UTF_8);
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            System.exit(-1);
        }
    }

}
