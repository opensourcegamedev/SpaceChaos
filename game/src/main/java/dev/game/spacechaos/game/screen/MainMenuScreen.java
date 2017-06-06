package dev.game.spacechaos.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import dev.game.spacechaos.engine.font.BitmapFontFactory;
import dev.game.spacechaos.engine.game.ScreenBasedGame;
import dev.game.spacechaos.engine.hud.HUD;
import dev.game.spacechaos.engine.hud.widgets.TextButton;
import dev.game.spacechaos.engine.screen.impl.BaseScreen;
import dev.game.spacechaos.engine.sound.VolumeManager;
import dev.game.spacechaos.engine.time.GameTime;

/**
 * The screen of the main menu.
 * <p>
 * Contains several buttons, so far it's only the singleplayer-button.
 *
 * @author SpaceChaos-Team
 *         (https://github.com/opensourcegamedev/SpaceChaos/blob/master/CONTRIBUTORS.md)
 * @since 1.0.0-PreAlpha
 */
public class MainMenuScreen extends BaseScreen {

    private static final String BG_IMAGE_PATH = "./data/wallpaper/galaxy3/space.png";
    private static final String SELECT_SOUND_PATH = "./data/sound/menu_selection_click/menu_selection_click_16bit.wav";
    private static final String MUSIC_PATH = "./data/music/sci-fi-menus-2-shorts-tracks/menu_sci-fi.ogg";

    private Texture bgImage = null;

    // UI
    private ShapeRenderer shapeRenderer = null;
    private HUD hud = null;

    // buttons
    private TextButton newGameButton = null;
    private TextButton multiplayerButton = null;
    private TextButton creditsButton = null;
    private TextButton settingsButton = null;

    // music
    private Music music = null;

    @Override
    protected void onInit(ScreenBasedGame game, AssetManager assetManager) {
        // create new HUD
        this.hud = new HUD();

        // create new shape renderer
        this.shapeRenderer = new ShapeRenderer();

        // create font
        BitmapFont buttonFont = BitmapFontFactory.createFont("./data/font/arial/arial.ttf", 32, Color.WHITE);

        // create UI
        float startX = game.getViewportWidth() / 2 - 200;

        this.newGameButton = new TextButton("New Singleplayer Game", buttonFont, startX, 400f);
        this.newGameButton.setDimension(400, 50);
        this.newGameButton.setClickListener(() ->
        // TODO Implement loading screen
        // game.getScreenManager().leaveAllAndEnter("loading")
        game.getScreenManager().leaveAllAndEnter("game"));
        this.hud.addWidget(this.newGameButton);

        this.multiplayerButton = new TextButton("Multiplayer Lobby", buttonFont, startX, 340f);
        this.multiplayerButton.setDimension(400, 50);
        this.multiplayerButton.setClickListener(() -> {
            // game.getScreenManager().leaveAllAndEnter("game");
        });
        // this.hud.addWidget(this.multiplayerButton);

        this.creditsButton = new TextButton("Credits", buttonFont, startX, 280f);
        this.creditsButton.setDimension(400, 50);
        this.creditsButton.setClickListener(() -> game.getScreenManager().leaveAllAndEnter("credits"));
        this.hud.addWidget(this.creditsButton);

        this.settingsButton = new TextButton("Settings", buttonFont, startX, 220f);
        this.settingsButton.setDimension(400, 50);
        this.settingsButton.setClickListener(() -> {
            // game.getScreenManager().leaveAllAndEnter("game");
        });
        // this.hud.addWidget(this.settingsButton);
    }

    @Override
    public void onResume() {
        // load assets
        assetManager.load(BG_IMAGE_PATH, Texture.class);
        assetManager.load(SELECT_SOUND_PATH, Sound.class);

        // load assets
        assetManager.finishLoadingAsset(BG_IMAGE_PATH);
        assetManager.finishLoadingAsset(SELECT_SOUND_PATH);

        // get assets
        this.bgImage = assetManager.get(BG_IMAGE_PATH, Texture.class);
        Sound selectSound = assetManager.get(SELECT_SOUND_PATH, Sound.class);

        // set hover sounds
        this.newGameButton.setHoverSound(selectSound);
        this.multiplayerButton.setHoverSound(selectSound);
        this.creditsButton.setHoverSound(selectSound);
        this.settingsButton.setHoverSound(selectSound);

        // load and get soundtrack
        assetManager.load(MUSIC_PATH, Music.class);
        assetManager.finishLoadingAsset(MUSIC_PATH);
        this.music = assetManager.get(MUSIC_PATH, Music.class);

        // play background music
        this.music.setVolume(VolumeManager.getInstance().getBackgroundMusicVolume());
        this.music.setLooping(true);
        this.music.play();
    }

    @Override
    public void onPause() {
        assetManager.unload(BG_IMAGE_PATH);
        assetManager.unload(SELECT_SOUND_PATH);

        Gdx.input.setInputProcessor(null);

        // stop music
        this.music.stop();
    }

    @Override
    public void update(ScreenBasedGame game, GameTime time) {
        // update HUD
        this.hud.update(game, time);
    }

    @Override
    public void draw(GameTime time, SpriteBatch batch) {
        // set camera projection matrix
        batch.setProjectionMatrix(game.getUICamera().combined);

        // draw background
        batch.draw(this.bgImage, 0, 0, game.getViewportWidth(), game.getViewportHeight());

        // draw first (SpriteBatch) layer of HUD
        this.hud.drawLayer0(time, batch);
        batch.flush();
        batch.end();

        // draw second layer (ShapeRenderer) of HUD
        this.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        this.shapeRenderer.setProjectionMatrix(game.getUICamera().combined);
        this.hud.drawLayer1(time, this.shapeRenderer);
        this.shapeRenderer.end();

        // draw last (SpriteBatch) layer of HUD
        batch.begin();
        batch.setProjectionMatrix(game.getUICamera().combined);
        this.hud.drawLayer2(time, batch);
    }

    @Override
    public void destroy() {

    }

}
