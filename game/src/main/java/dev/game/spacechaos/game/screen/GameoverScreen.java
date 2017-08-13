package dev.game.spacechaos.game.screen;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import dev.game.spacechaos.engine.font.BitmapFontFactory;
import dev.game.spacechaos.engine.game.ScreenBasedGame;
import dev.game.spacechaos.engine.hud.widgets.TextButton;
import dev.game.spacechaos.engine.screen.impl.BaseScreen;
import dev.game.spacechaos.engine.sound.VolumeManager;
import dev.game.spacechaos.engine.time.GameTime;

/**
 * The screen shown when you died contains a button to restart and a label which
 * shows the time you survived.
 *
 * @author SpaceChaos-Team
 *         (https://github.com/opensourcegamedev/SpaceChaos/blob/master/CONTRIBUTORS.md)
 * @since 1.0.0-PreAlpha
 */
public class GameoverScreen extends BaseScreen {

    private static final String BG_IMAGE_PATH = "./data/wallpaper/galaxy1/galaxy1.png";
    private static final String GAMEOVER_SOUND_PATH = "./data/sound/rock_breaking/rock_breaking.ogg";

    // font for buttons & text
    private BitmapFont font = null;
    private BitmapFont buttonFont = null;

    private Texture bgTexture = null;
    private Sound sound = null;

    private TextButton menuButton = null;
    protected TextButton replayButton = null;

    private String timeText = "";
    private String scoreText = "";

    @Override
    protected void onInit(ScreenBasedGame game, AssetManager assetManager) {
        // load & get assets
        game.getAssetManager().load(GAMEOVER_SOUND_PATH, Sound.class);
        game.getAssetManager().load(BG_IMAGE_PATH, Texture.class);
        game.getAssetManager().finishLoadingAsset(GAMEOVER_SOUND_PATH);
        game.getAssetManager().finishLoadingAsset(BG_IMAGE_PATH);
        sound = game.getAssetManager().get(GAMEOVER_SOUND_PATH);
        this.bgTexture = game.getAssetManager().get(BG_IMAGE_PATH);

        // generate fonts
        this.font = BitmapFontFactory.createFont("./data/font/spartakus/SparTakus.ttf", 48, Color.WHITE, Color.BLUE, 3);
        this.buttonFont = BitmapFontFactory.createFont("data/font/arial/arial.ttf", 32, Color.WHITE);

        //back to menu button
        this.menuButton = new TextButton("Back to Menu", this.buttonFont);
        this.menuButton.setDimension(/* 200 */400, /* 50 */50);
        this.menuButton.setPosition(game.getViewportWidth() / 2 - (menuButton.getWidth() / 2), 260);
        this.menuButton.setClickListener(() -> game.getScreenManager().leaveAllAndEnter("menu"));

        //replay button
        this.replayButton = new TextButton("Replay", this.buttonFont);
        this.replayButton.setDimension(400, 50);
        this.replayButton.setPosition(game.getViewportWidth() / 2 - (menuButton.getWidth() / 2), 200);
        this.replayButton.setClickListener(() -> {
            game.getScreenManager().leaveAllAndEnter("game");
        });
    }

    @Override
    public void onResume() {
        // play sound
        sound.play(VolumeManager.getInstance().getBackgroundMusicVolume());

        this.timeText = game.getSharedData().get("lastElapsedTimeText", String.class);
        this.scoreText = game.getSharedData().get("score", String.class);
    }

    @Override
    public void onPause() {
        //
    }

    @Override
    public void update(ScreenBasedGame game, GameTime time) {
        //update buttons
        this.menuButton.update(game, time);
        this.replayButton.update(game, time);
    }

    @Override
    public void draw(GameTime time, SpriteBatch batch) {
        //set UI Camera projection matrix
        batch.setProjectionMatrix(game.getUICamera().combined);

        //draw background image
        batch.draw(this.bgTexture, 0, 0);

        //draw text
        this.font.draw(batch, "GAME OVER", game.getViewportWidth() / 2 - 245, game.getViewportHeight() / 2 + 70);

        this.buttonFont.draw(batch, "Elapsed Time: " + this.timeText, 75, 120);
        this.buttonFont.draw(batch, "Score: " + this.scoreText, 75, 70);

        // draw buttons
        this.menuButton.drawLayer0(time, batch);
        this.replayButton.drawLayer0(time, batch);
    }

    @Override
    public void destroy() {

    }

}
