package dev.game.spacechaos.game.screen;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dev.game.spacechaos.engine.camera.CameraWrapper;
import dev.game.spacechaos.engine.game.ScreenBasedGame;
import dev.game.spacechaos.engine.screen.impl.BaseScreen;
import dev.game.spacechaos.engine.time.GameTime;
import dev.game.spacechaos.game.entities.SpaceShuttle;

/**
 * Created by Justin on 28.03.2017.
 */
public class GameScreen extends BaseScreen {

    protected static final String BG_IMAGE_PATH = "./data/images/skybox/galaxy/galaxy+X.png";
    protected static final String SHUTTLE_IMAGE_PATH = "./data/images/entities/starship/spaceshuttle.png";

    //background image
    protected Texture bgTexture = null;

    //spaceshuttle
    protected SpaceShuttle spaceShuttle;

    @Override protected void onInit(ScreenBasedGame game, AssetManager assetManager) {
        //load all neccessary assets

        //load skybox
        assetManager.load(BG_IMAGE_PATH, Texture.class);
        assetManager.load(SHUTTLE_IMAGE_PATH, Texture.class);

        //wait while assets are loading
        assetManager.finishLoadingAsset(BG_IMAGE_PATH);
        assetManager.finishLoadingAsset(SHUTTLE_IMAGE_PATH);

        //get asset
        this.bgTexture = assetManager.get(BG_IMAGE_PATH, Texture.class);

        //create space shuttle img, x, y
        spaceShuttle = new SpaceShuttle(assetManager.get(SHUTTLE_IMAGE_PATH, Texture.class), game.getViewportWidth()/2, game.getViewportHeight()/2);
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

        //update shuttle
        spaceShuttle.update(game, game.getCamera(), time);
    }

    @Override public void draw(GameTime time, SpriteBatch batch) {
        //draw background skybox image
        batch.draw(this.bgTexture, 0, 0, game.getViewportWidth(), game.getViewportHeight());

        //draw shuttle
        spaceShuttle.draw(time, game.getCamera(), batch);
    }

    @Override public void destroy() {
        assetManager.unload(BG_IMAGE_PATH);
    }

}
