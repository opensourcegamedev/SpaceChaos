package dev.game.spacechaos.game.screen;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import dev.game.spacechaos.engine.font.BitmapFontFactory;
import dev.game.spacechaos.engine.game.ScreenBasedGame;
import dev.game.spacechaos.engine.hud.widgets.ColoredTextButton;
import dev.game.spacechaos.engine.hud.widgets.ImageButton;
import dev.game.spacechaos.engine.screen.impl.BaseScreen;
import dev.game.spacechaos.engine.sound.VolumeManager;
import dev.game.spacechaos.engine.time.GameTime;

/**
 * The screen shown when you died contains a button to restart and a label which shows the time you survived.
 *
 * @author SpaceChaos-Team (https://github.com/opensourcegamedev/SpaceChaos/blob/master/CONTRIBUTORS.md)
 * @since 1.0.0-PreAlpha
 */
public class GameoverScreen extends BaseScreen {

    private static final String BG_IMAGE_PATH = "./data/wallpaper/galaxy1/galaxy1.png";
    private static final String GAMEOVER_SOUND_PATH = "./data/sound/rock_breaking/rock_breaking.ogg";

    private static final String BUTTON_IMAGE_PATH = "./data/images/hud/restart_button/restart_button.png";
    private static final String BUTTON_HOVER_PATH = "./data/images/hud/restart_button/restart_button_hovered.png";

    //font for buttons & text
    private BitmapFont font = null;
    private BitmapFont buttonFont = null;

    private Texture bgTexture = null;
    private Sound sound = null;

    //private ColoredTextButton replayButton = null;
    private Texture buttonTexture = null;
    private Texture hoverTexture = null;
    private ImageButton replayButton = null;

    private String timeText = "";
    private String scoreText = "";

    @Override
    protected void onInit(ScreenBasedGame game, AssetManager assetManager) {
        //load & get assets
        game.getAssetManager().load(GAMEOVER_SOUND_PATH, Sound.class);
        game.getAssetManager().load(BG_IMAGE_PATH, Texture.class);
        game.getAssetManager().finishLoadingAsset(GAMEOVER_SOUND_PATH);
        game.getAssetManager().finishLoadingAsset(BG_IMAGE_PATH);
        sound = game.getAssetManager().get(GAMEOVER_SOUND_PATH);
        this.bgTexture = game.getAssetManager().get(BG_IMAGE_PATH);

        //load & get button textures
        game.getAssetManager().load(BUTTON_IMAGE_PATH, Texture.class);
        game.getAssetManager().load(BUTTON_HOVER_PATH, Texture.class);
        game.getAssetManager().finishLoadingAsset(BUTTON_IMAGE_PATH);
        game.getAssetManager().finishLoadingAsset(BUTTON_HOVER_PATH);
        this.buttonTexture = game.getAssetManager().get(BUTTON_IMAGE_PATH, Texture.class);
        this.hoverTexture = game.getAssetManager().get(BUTTON_HOVER_PATH, Texture.class);

        //generate fonts
        this.font = BitmapFontFactory.createFont("./data/font/spartakus/SparTakus.ttf", 48, Color.WHITE, Color.BLUE, 3);
        this.buttonFont = BitmapFontFactory.createFont("data/font/arial/arial.ttf", 32, Color.RED);

        this.replayButton = new ImageButton(this.buttonTexture, this.hoverTexture);
        this.replayButton.setDimension(/*200*/400, /*50*/128);
        this.replayButton.setPosition(game.getViewportWidth() / 2 - (replayButton.getWidth() / 2), 128);

        this.replayButton.setClickListener(() -> game.getScreenManager().leaveAllAndEnter("game"));
    }

    @Override
    public void onResume() {
        //play sound
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
        this.replayButton.update(game, time);
    }

    @Override
    public void draw(GameTime time, SpriteBatch batch) {
        batch.setProjectionMatrix(game.getUICamera().combined);

        batch.draw(this.bgTexture, 0, 0);

        this.font.draw(batch, "GAME OVER", game.getViewportWidth() / 2 - 200, game.getViewportHeight() / 2);

        this.buttonFont.draw(batch, "Elapsed Time: " + this.timeText, 100, 100);
        this.buttonFont.draw(batch, "Score: "+this.scoreText, 100, 50);

        //draw replay button
        this.replayButton.drawLayer0(time, batch);
    }

    @Override
    public void destroy() {

    }

}
