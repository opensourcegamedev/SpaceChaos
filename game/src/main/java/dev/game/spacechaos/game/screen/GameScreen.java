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

    //background image
    protected Texture bgTexture = null;

    @Override protected void onInit(ScreenBasedGame game, AssetManager assetManager) {
        //
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

    }

    @Override public void destroy() {

    }

}
