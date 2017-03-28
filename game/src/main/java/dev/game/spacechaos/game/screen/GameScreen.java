package dev.game.spacechaos.game.screen;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dev.game.spacechaos.engine.camera.CameraWrapper;
import dev.game.spacechaos.engine.game.ScreenBasedGame;
import dev.game.spacechaos.engine.screen.impl.BaseScreen;
import dev.game.spacechaos.engine.time.GameTime;

/**
 * Created by Justin on 28.03.2017.
 */
public class GameScreen extends BaseScreen {

    protected static final String BG_IMAGE_PATH = "./data/images/skybox/galaxy/galaxy+X.png";

    //background image
    protected Texture bgTexture = null;

    @Override protected void onInit(ScreenBasedGame game, AssetManager assetManager) {
        //load all neccessary assets

        //load skybox
        assetManager.load(BG_IMAGE_PATH, Texture.class);

        //wait while assets are loading
        assetManager.finishLoadingAsset(BG_IMAGE_PATH);

        //get asset
        this.bgTexture = assetManager.get(BG_IMAGE_PATH, Texture.class);
    }

    @Override public void onResume() {
        //
    }

    @Override public void onPause() {
        //
    }

    @Override public void update(ScreenBasedGame game, GameTime time) {
        //get camera
        CameraWrapper camera = game.getCamera();
    }

    @Override public void draw(GameTime time, SpriteBatch batch) {
        //draw background skybox image
        batch.draw(this.bgTexture, 0, 0, game.getViewportWidth(), game.getViewportHeight());
    }

    @Override public void destroy() {
        assetManager.unload(BG_IMAGE_PATH);
    }

}
