package dev.game.spacechaos.game.screen;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dev.game.spacechaos.engine.font.BitmapFontFactory;
import dev.game.spacechaos.engine.game.ScreenBasedGame;
import dev.game.spacechaos.engine.screen.impl.BaseScreen;
import dev.game.spacechaos.engine.time.GameTime;

/**
 * Created by Justin on 13.04.2017.
 */
public class GameoverScreen extends BaseScreen {

    protected static final String BG_IMAGE_PATH = "./data/wallpaper/galaxy1/galaxy1.png";
    protected static final String GAMEOVER_SOUND_PATH = "./data/sound/rock_breaking/rock_breaking.ogg";

    protected Texture bgTexture = null;
    protected Sound sound = null;

    protected BitmapFont font = null;

    @Override
    protected void onInit(ScreenBasedGame game, AssetManager assetManager) {
        game.getAssetManager().load(GAMEOVER_SOUND_PATH, Sound.class);
        game.getAssetManager().load(BG_IMAGE_PATH, Texture.class);
        game.getAssetManager().finishLoadingAsset(GAMEOVER_SOUND_PATH);
        game.getAssetManager().finishLoadingAsset(BG_IMAGE_PATH);
        sound = game.getAssetManager().get(GAMEOVER_SOUND_PATH);
        this.bgTexture = game.getAssetManager().get(BG_IMAGE_PATH);

        //generate fonts
        this.font = BitmapFontFactory.createFont("./data/font/spartakus/SparTakus.ttf", 48, Color.WHITE, Color.BLUE, 3);
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
        batch.setProjectionMatrix(game.getUICamera().combined);

        batch.draw(this.bgTexture, 0, 0);

        this.font.draw(batch, "GAME OVER", game.getViewportWidth() / 2 - 200, game.getViewportHeight() / 2);
    }

    @Override
    public void destroy() {

    }

}
