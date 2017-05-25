package dev.game.spacechaos.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

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

    protected final String MUSIC_PATH = "./data/music/neon-transit/Neon_Transit.ogg";

    //background music soundtrack
    protected Music music = null;

    @Override
    protected void onInit(ScreenBasedGame game, AssetManager assetManager) {
        //load assets
        assetManager.load(MUSIC_PATH, Music.class);
        assetManager.finishLoadingAsset(MUSIC_PATH);

        //get assets
        this.music = assetManager.get(MUSIC_PATH, Music.class);
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

    }

    @Override
    public void destroy() {

    }

}
