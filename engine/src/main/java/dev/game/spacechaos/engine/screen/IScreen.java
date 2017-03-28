package dev.game.spacechaos.engine.screen;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dev.game.spacechaos.engine.game.ScreenBasedGame;
import dev.game.spacechaos.engine.time.GameTime;

/**
 * Created by Justin on 06.02.2017.
 */
public interface IScreen {

    /**
    * initialize game screen
    */
    public void init(ScreenBasedGame game, AssetManager assetManager);

    /**
    * update game screen
    */
    public void update(ScreenBasedGame game, GameTime time);

    /**
    * draw game screen
    */
    public void draw(GameTime time, SpriteBatch batch);

    public void onPause();

    public void onResume();

    /**
    * destroy game screen
    */
    public void destroy();

}
