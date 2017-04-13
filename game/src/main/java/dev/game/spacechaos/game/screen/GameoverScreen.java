package dev.game.spacechaos.game.screen;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dev.game.spacechaos.engine.game.ScreenBasedGame;
import dev.game.spacechaos.engine.screen.impl.BaseScreen;
import dev.game.spacechaos.engine.time.GameTime;

/**
 * Created by Justin on 13.04.2017.
 */
public class GameoverScreen extends BaseScreen {

    protected static final String GAMEOVER_SOUND_PATH = "./data/sound/rock_breaking/rock_breaking.ogg";

    protected Sound sound = null;

    @Override
    protected void onInit(ScreenBasedGame game, AssetManager assetManager) {
        game.getAssetManager().load(GAMEOVER_SOUND_PATH, Sound.class);
        game.getAssetManager().finishLoadingAsset(GAMEOVER_SOUND_PATH);
        sound = game.getAssetManager().get(GAMEOVER_SOUND_PATH);
    }

    @Override
    public void onResume () {
        //play sound
        sound.play();
    }

    @Override
    public void onPause () {
        //
    }

    @Override
    public void update(ScreenBasedGame game, GameTime time) {

    }

    @Override
    public void draw(GameTime time, SpriteBatch batch) {

    }

    @Override
    public void destroy() {

    }

}
