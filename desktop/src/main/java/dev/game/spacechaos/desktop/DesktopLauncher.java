package dev.game.spacechaos.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import dev.game.spacechaos.engine.utils.FileUtils;
import dev.game.spacechaos.game.Game;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Created by Justin on 27.03.2017.
 */
public class DesktopLauncher {

    public static void main (String[] args) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "Space Chaos";
        config.height = 720;
        config.width = 1280;
        //config.addIcon("./data/icons/icon.png", Files.FileType.Absolute);

        try {
            //start game
            new LwjglApplication(new Game(), config);
        } catch (Exception e) {
            e.printStackTrace();

            try {
                //write crash dump
                FileUtils.writeFile("./crash.log", e.getLocalizedMessage(), StandardCharsets.UTF_8);
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            System.exit(-1);
        }
    }

}
