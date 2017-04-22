package dev.game.spacechaos.game.screen;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dev.game.spacechaos.engine.game.ScreenBasedGame;
import dev.game.spacechaos.engine.screen.impl.BaseScreen;
import dev.game.spacechaos.engine.time.GameTime;

/**
 * Created by Justin on 22.04.2017.
 */
public class MainMenuScreen extends BaseScreen {

    protected static final String BG_IMAGE_PATH = "./data/wallpaper/galaxy2/galaxy2.png";

    protected Texture bgImage = null;

    @Override
    protected void onInit(ScreenBasedGame game, AssetManager assetManager) {
        //load assets
        assetManager.load(BG_IMAGE_PATH, Texture.class);
    }

    @Override
    public void onResume () {
        //load assets
        assetManager.finishLoadingAsset(BG_IMAGE_PATH);

        //get assets
        this.bgImage = assetManager.get(BG_IMAGE_PATH, Texture.class);
    }

    @Override
    public void onPause () {
        //
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
    }

    @Override
    public void destroy() {

    }

}
