package dev.game.spacechaos.game.screen;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import dev.game.spacechaos.engine.game.ScreenBasedGame;
import dev.game.spacechaos.engine.screen.impl.BaseScreen;
import dev.game.spacechaos.engine.time.GameTime;

/**
 * Loads all the necessary assets for the {@linkplain GameScreen}.
 */
public class LoadingScreen extends BaseScreen {

    protected static final String BAR_ORANGE_IMAGE_PATH = "./data/images/gui/barOrange.png";
    protected static final String BAR_BLUE_IMAGE_PATH = "./data/images/gui/barBlue.png";
    private static final String SPACE_IMAGE_PATH = "./data/wallpaper/galaxy3/space.png";

    // assets
    protected Texture bgTexture = null;
    protected Texture orangeBarTexture = null;
    protected Texture blueBarTexture = null;

    @Override
    protected void onInit(ScreenBasedGame game, AssetManager assetManager) {
        assetManager.load(BAR_ORANGE_IMAGE_PATH, Texture.class);
        assetManager.load(BAR_BLUE_IMAGE_PATH, Texture.class);
        assetManager.load(SPACE_IMAGE_PATH, Texture.class);
        assetManager.finishLoadingAsset(BAR_ORANGE_IMAGE_PATH);
        assetManager.finishLoadingAsset(BAR_BLUE_IMAGE_PATH);
        assetManager.finishLoadingAsset(SPACE_IMAGE_PATH);
        this.orangeBarTexture = assetManager.get(BAR_ORANGE_IMAGE_PATH, Texture.class);
        this.blueBarTexture = assetManager.get(BAR_BLUE_IMAGE_PATH, Texture.class);
        this.bgTexture = assetManager.get(SPACE_IMAGE_PATH, Texture.class);
    }

    @Override
    public void onResume() {
        // TODO Implement loading mechanism
        // game.getScreenManager().
        // assetManager.load();
    }

    @Override
    public void onPause() {

    }

    @Override
    public void update(ScreenBasedGame game, GameTime time) {
        if (assetManager.update()) {
            game.getScreenManager().leaveAllAndEnter("game");
        }
    }

    @Override
    public void draw(GameTime time, SpriteBatch batch) {
        // set camera projection matrix
        batch.setProjectionMatrix(game.getUICamera().combined);

        // draw background image
        batch.draw(this.bgTexture, 0, 0, game.getViewportWidth(), game.getViewportHeight());

        float viewPortWidth = game.getViewportWidth();
        float viewPortHeight = game.getViewportHeight();

        // get image dimension
        float imageWidth = this.orangeBarTexture.getWidth();
        float imageHeight = this.orangeBarTexture.getHeight();

        // draw loading bar
        batch.draw(this.blueBarTexture, (viewPortWidth / 2) - (imageWidth / 2) + 1,
                (viewPortHeight / 4) - imageHeight / 2);
        batch.draw(this.orangeBarTexture, (viewPortWidth / 2) - (imageWidth / 2),
                (viewPortHeight / 4) - imageHeight / 2, imageWidth * assetManager.getProgress(), imageHeight);
    }

    @Override
    public void destroy() {

    }

}
