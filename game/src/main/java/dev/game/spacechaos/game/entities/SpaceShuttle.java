package dev.game.spacechaos.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Jo on 29.03.2017.
 */
public class SpaceShuttle {

    private Texture shuttleTexture;

    public SpaceShuttle(Texture shuttleTexture) {
        this.shuttleTexture = shuttleTexture;
    }

    public void update() {
    }

    public void draw(SpriteBatch batch, float x, float y) {
        batch.draw(shuttleTexture, x - shuttleTexture.getWidth() / 2, y - shuttleTexture.getHeight() / 2); //center shuttle at given coordinates
    }
}
