package dev.game.spacechaos.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import dev.game.spacechaos.engine.font.BitmapFontFactory;
import dev.game.spacechaos.engine.game.ScreenBasedGame;
import dev.game.spacechaos.engine.screen.impl.BaseScreen;
import dev.game.spacechaos.engine.sound.VolumeManager;
import dev.game.spacechaos.engine.time.GameTime;
import dev.game.spacechaos.engine.utils.FileUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author SpaceChaos-Team (https://github.com/opensourcegamedev/SpaceChaos/blob/master/CONTRIBUTORS.md)
 * @version 1.0.0-PreAlpha
 */
public class CreditsScreen extends BaseScreen {

    //asset paths
    protected final String MUSIC_PATH = "./data/music/neon-transit/Neon_Transit.ogg";
    protected final String BACKGROUND_IMAGE_PATH = "./data/images/skybox/galaxy/galaxy+X.png";

    //background music soundtrack
    protected Music music = null;

    //background texture
    protected Texture bgTexture = null;

    //font
    protected BitmapFont titleFont = null;

    //credit lines
    protected String[] creditLines = new String[1];

    protected final int MAX_CHARS_PER_LINE = 30;

    @Override
    protected void onInit(ScreenBasedGame game, AssetManager assetManager) {
        //load assets
        assetManager.load(MUSIC_PATH, Music.class);
        assetManager.load(BACKGROUND_IMAGE_PATH, Texture.class);
        assetManager.finishLoadingAsset(MUSIC_PATH);
        assetManager.finishLoadingAsset(BACKGROUND_IMAGE_PATH);

        //get assets
        this.music = assetManager.get(MUSIC_PATH, Music.class);
        this.bgTexture = assetManager.get(BACKGROUND_IMAGE_PATH, Texture.class);

        //generate fonts
        this.titleFont = BitmapFontFactory.createFont("./data/font/spartakus/SparTakus.ttf", 48, Color.WHITE, Color.BLUE, 3);
        //this.font2 = BitmapFontFactory.createFont("./data/font/spartakus/SparTakus.ttf", 48, Color.RED, Color.WHITE, 3);

        //generate credits text array
        try {
            this.generateCreditLines();
        } catch (IOException e) {
            e.printStackTrace();

            //TODO: remove this line and handle exception with exception window
            System.exit(0);
        }
    }

    @Override
    public void onResume () {
        //set music volume
        this.music.setVolume(VolumeManager.getInstance().getBackgroundMusicVolume());

        //set looping
        this.music.setLooping(true);

        //play music
        this.music.play();
    }

    @Override
    public void onPause () {
        this.music.stop();
    }

    @Override
    public void update(ScreenBasedGame game, GameTime time) {
        if (Gdx.input.isTouched()) {
            //back to main menu
            game.getScreenManager().leaveAllAndEnter("menu");
        }
    }

    @Override
    public void draw(GameTime time, SpriteBatch batch) {
        //set UI camera
        batch.setProjectionMatrix(this.game.getUICamera().combined);

        //draw background
        batch.draw(this.bgTexture, 0, 0, game.getViewportWidth(), game.getViewportHeight());

        //draw title
        this.titleFont.draw(batch, "Credits", 50, game.getViewportHeight() - 50);
    }

    @Override
    public void destroy() {

    }

    protected void generateCreditLines () throws IOException {
        List<String> lines = new ArrayList<>();

        //read lines from file
        List<String> lines1 = FileUtils.readLines("./CONTRIBUTORS.md", StandardCharsets.UTF_8);

        //look for too long lines
        for (String line : lines1) {
            if (line.length() > MAX_CHARS_PER_LINE) {
                //split line into 2 lines
                lines.add(line.substring(0, MAX_CHARS_PER_LINE));
                lines.add(line.substring(MAX_CHARS_PER_LINE));
            } else {
                //add line to list
                lines.add(line.replace("\\", ""));
            }
        }

        //create new array and convert list to array for fast access
        this.creditLines = new String[lines.size()];
        this.creditLines = lines.toArray(this.creditLines);
    }

}
