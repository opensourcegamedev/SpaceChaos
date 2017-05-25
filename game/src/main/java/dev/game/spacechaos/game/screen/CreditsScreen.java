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

}
