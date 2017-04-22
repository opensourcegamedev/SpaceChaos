package dev.game.spacechaos.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import dev.game.spacechaos.engine.game.ScreenBasedGame;
import dev.game.spacechaos.engine.screen.impl.BaseScreen;
import dev.game.spacechaos.engine.skin.SkinFactory;
import dev.game.spacechaos.engine.sound.VolumeManager;
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

    //UI
    protected Skin uiSkin = null;
    protected Stage uiStage = null;

    //UI buttons
    protected TextButton newGameButton = null;
    protected TextButton lobbyButton = null;
    protected TextButton creditsButton = null;
    protected TextButton settingsButton = null;

    protected InputListener soundInputListener = new InputListener() {

        @Override
        public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
            System.out.println("enter");

            //play sound
            selectSound.play(VolumeManager.getInstance().getEnvVolume());
        }

        @Override
        public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
            //
        }

    };

    @Override
    protected void onInit(ScreenBasedGame game, AssetManager assetManager) {
        //create stage for user interface (UI)
        this.uiStage = new Stage();

        //add support for window resizing
        game.addResizeListener(((width, height) -> {
            //update viewport of stage
            uiStage.getViewport().update(width, height, true);
        }));

        //create and load ui skin from json file
        this.uiSkin = SkinFactory.createSkin(UI_SKIN_PATH);

        //create UI
        float startX = game.getViewportWidth() / 2 - 200;

        //create new game button
        this.newGameButton = new TextButton("New Game (Singleplayer)", this.uiSkin);
        this.newGameButton.setPosition(startX, 400);
        this.newGameButton.setWidth(400);
        this.newGameButton.setHeight(50);
        /*this.newGameButton.addListener(new ClickListener() {

            protected boolean hovered = true;

            @Override
            public void clicked (InputEvent event, float x, float y) {
                game.getScreenManager().leaveAllAndEnter("game");
            }

            public boolean mouseMoved (InputEvent event, float x, float y) {
                super.mouseMoved(event, x, y);

                System.out.println("mouseMoved.");

                if (!hovered) {
                    //
                }

                return false;
            }

        });*/
        this.newGameButton.addCaptureListener(this.soundInputListener);
        this.uiStage.addActor(this.newGameButton);
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

        //set input processor so UI stage can handle input events
        Gdx.input.setInputProcessor(this.uiStage);
    }

    @Override
    public void onPause () {
        assetManager.unload(BG_IMAGE_PATH);
        assetManager.unload(SELECT_SOUND_PATH);

        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void update(ScreenBasedGame game, GameTime time) {

    }

    @Override
    public void draw(GameTime time, SpriteBatch batch) {
        //set camera projection matrix
        batch.setProjectionMatrix(game.getUICamera().combined);

        //draw background
        batch.draw(this.bgImage, 0, 0, game.getViewportWidth(), game.getViewportHeight());

        batch.flush();

        //draw UI widgets
        this.uiStage.draw();
    }

    @Override
    public void destroy() {

    }

}
