package dev.game.spacechaos.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
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
import dev.game.spacechaos.engine.time.GameTime;

/**
 * Created by Justin on 22.04.2017.
 */
public class MainMenuScreen extends BaseScreen {

    protected static final String BG_IMAGE_PATH = "./data/wallpaper/galaxy2/galaxy2.jpg";
    protected static final String SELECT_SOUND_PATH = "./data/sound/menu_selection_click/menu_selection_click_16bit.wav";
    protected static final String UI_SKIN_PATH = "./data/ui/skin/libgdx/uiSkin.json";

    protected Texture bgImage = null;
    protected Sound selectSound = null;

    //fonts
    protected BitmapFont buttonFont = null;

    //UI
    protected ShapeRenderer shapeRenderer = null;
    protected HUD hud = null;

    protected TextButton newGameButton = null;

    @Override
    protected void onInit(ScreenBasedGame game, AssetManager assetManager) {
        //create new HUD
        this.hud = new HUD();

        //create new shape renderer
        this.shapeRenderer = new ShapeRenderer();

        //create font
        this.buttonFont = BitmapFontFactory.createFont("./data/font/arial/arial.ttf", 32, Color.WHITE);

        //create UI
        float startX = game.getViewportWidth() / 2 - 200;

        this.newGameButton = new TextButton("New Game (Singleplayer)", this.buttonFont, startX, 400f);
        this.newGameButton.setDimension(400, 50);
        this.hud.addWidget(this.newGameButton);
    }

    @Override
    public void onResume () {
        //load assets
        assetManager.load(BG_IMAGE_PATH, Texture.class);
        assetManager.load(SELECT_SOUND_PATH, Sound.class);

        //load assets
        assetManager.finishLoadingAsset(BG_IMAGE_PATH);
        assetManager.finishLoadingAsset(SELECT_SOUND_PATH);

        //get assets
        this.bgImage = assetManager.get(BG_IMAGE_PATH, Texture.class);
        this.selectSound = assetManager.get(SELECT_SOUND_PATH, Sound.class);

        //set hover sounds
        this.newGameButton.setHoverSound(this.selectSound);
    }

    @Override
    public void onPause () {
        assetManager.unload(BG_IMAGE_PATH);
        assetManager.unload(SELECT_SOUND_PATH);

        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void update(ScreenBasedGame game, GameTime time) {
        //update HUD
        this.hud.update(game, time);
    }

    @Override
    public void draw(GameTime time, SpriteBatch batch) {
        //set camera projection matrix
        batch.setProjectionMatrix(game.getUICamera().combined);

        //draw background
        batch.draw(this.bgImage, 0, 0, game.getViewportWidth(), game.getViewportHeight());

        //draw first (SpriteBatch) layer of HUD
        this.hud.drawLayer0(time, batch);
        batch.flush();
        batch.end();

        //draw second layer (ShapeRenderer) of HUD
        this.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        this.shapeRenderer.setProjectionMatrix(game.getUICamera().combined);
        this.hud.drawLayer1(time, this.shapeRenderer);
        this.shapeRenderer.end();

        //draw last (SpriteBatch) layer of HUD
        batch.begin();
        batch.setProjectionMatrix(game.getUICamera().combined);
        this.hud.drawLayer2(time, batch);
    }

    @Override
    public void destroy() {

    }

}
