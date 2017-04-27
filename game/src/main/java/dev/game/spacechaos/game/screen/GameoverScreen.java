package dev.game.spacechaos.game.screen;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dev.game.spacechaos.engine.font.BitmapFontFactory;
import dev.game.spacechaos.engine.game.ScreenBasedGame;
import dev.game.spacechaos.engine.hud.ColoredTextButton;
import dev.game.spacechaos.engine.screen.impl.BaseScreen;
import dev.game.spacechaos.engine.sound.VolumeManager;
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
    protected BitmapFont buttonFont = null;

    protected ColoredTextButton replayButton = null;

    protected String timeText = "";

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
        this.buttonFont = BitmapFontFactory.createFont("data/font/arial/arial.ttf", 32, Color.RED);

        this.replayButton = new ColoredTextButton("Replay", this.buttonFont);
        this.replayButton.setBackgroundColor(Color.WHITE);
        this.replayButton.setBackgroundHoverColor(Color.YELLOW);
        this.replayButton.setDimension(200, 50);
        this.replayButton.setPosition(game.getViewportWidth() / 2 - (replayButton.getWidth() / 2), 100);

        this.replayButton.setClickListener(() -> {
            game.getScreenManager().leaveAllAndEnter("game");
        });
    }

    @Override
    public void onResume() {
        //play sound
        sound.play(VolumeManager.getInstance().getBackgroundMusicVolume());

        this.timeText = game.getSharedData().get("lastElapsedTimeText", String.class);
    }

    @Override
    public void onPause() {
        //
    }

    @Override
    public void update(ScreenBasedGame game, GameTime time) {
        this.replayButton.update(game, time);
    }

    @Override
    public void draw(GameTime time, SpriteBatch batch) {
        batch.setProjectionMatrix(game.getUICamera().combined);

        batch.draw(this.bgTexture, 0, 0);

        this.font.draw(batch, "GAME OVER", game.getViewportWidth() / 2 - 200, game.getViewportHeight() / 2);

        this.buttonFont.draw(batch, "Elapsed Time: " + this.timeText, 100, 100);

        //draw replay button
        this.replayButton.drawLayer0(time, batch);
    }

    @Override
    public void destroy() {

    }

}
