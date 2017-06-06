package dev.game.spacechaos.game.screen;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dev.game.spacechaos.engine.game.ScreenBasedGame;
import dev.game.spacechaos.engine.screen.impl.BaseScreen;
import dev.game.spacechaos.engine.time.GameTime;

/**
 * Created by Justin on 05.06.2017.
 */
public class SplashscreenScreen extends BaseScreen {

    protected static final String LOGO_IMAGE_PATH = "./data/images/loading/loadscreen.png";
    private static final String SPACE_IMAGE_PATH = "./data/wallpaper/galaxy3/space.png";

    protected long startTime = 0;
    protected long interval = 1250;

    //assets
    protected Texture bgTexture = null;
    protected Texture logoTexture = null;

    @Override
    protected void onInit(ScreenBasedGame game, AssetManager assetManager) {
        assetManager.load(LOGO_IMAGE_PATH, Texture.class);
        assetManager.load(SPACE_IMAGE_PATH, Texture.class);
        assetManager.finishLoadingAsset(LOGO_IMAGE_PATH);
        assetManager.finishLoadingAsset(SPACE_IMAGE_PATH);
        this.logoTexture = assetManager.get(LOGO_IMAGE_PATH, Texture.class);
        this.bgTexture = assetManager.get(SPACE_IMAGE_PATH, Texture.class);
    }

    @Override
    public void onResume () {
        //
    }

    @Override
    public void onPause () {
        //
    }

    @Override
    public void update(ScreenBasedGame game, GameTime time) {
        //check, if it was the first update call
        if (startTime == 0) {
            this.startTime = GameTime.getInstance().getTime();
        }

        if ((startTime + interval) < time.getTime()) {
            game.getScreenManager().leaveAllAndEnter("menu");
        }
    }

    @Override
    public void draw(GameTime time, SpriteBatch batch) {
        // set camera projection matrix
        batch.setProjectionMatrix(game.getUICamera().combined);

        //draw background image
        batch.draw(this.bgTexture, 0, 0, game.getViewportWidth(), game.getViewportHeight());

        //get window width & height
        float viewPortWidth = game.getViewportWidth();
        float viewPortHeight = game.getViewportHeight();

        //get image dimension
        float imageWidth = this.logoTexture.getWidth();
        float imageHeight = this.logoTexture.getHeight();

        //draw image on center of window
        batch.draw(this.logoTexture, (viewPortWidth / 2) - (imageWidth / 2), (viewPortHeight / 2) - imageHeight / 2);
    }

    @Override
    public void destroy() {

    }

}
