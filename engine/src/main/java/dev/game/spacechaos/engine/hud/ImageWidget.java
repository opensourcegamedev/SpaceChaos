package dev.game.spacechaos.engine.hud;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dev.game.spacechaos.engine.game.BaseGame;
import dev.game.spacechaos.engine.time.GameTime;

/**
 * Created by Justin on 09.02.2017.
 */
public class ImageWidget extends BaseHUDWidget {

    protected Texture texture = null;

    public ImageWidget (Texture texture) {
        this.texture = texture;
    }

    @Override public void update(BaseGame game, GameTime time) {
        //
    }

    @Override public void drawLayer0(GameTime time, SpriteBatch batch) {
        batch.draw(this.texture, getX(), getY());
    }
}
