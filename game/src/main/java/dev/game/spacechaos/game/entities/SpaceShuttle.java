package dev.game.spacechaos.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Jo on 29.03.2017.
 */
public class SpaceShuttle {

    private Texture shuttleTexture;
    private float xPos;
    private float yPos;

    public SpaceShuttle(Texture shuttleTexture, float xPos, float yPos) {
        this.shuttleTexture = shuttleTexture;
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public void update() {
    }

    public void draw(SpriteBatch batch) {
        batch.draw(shuttleTexture, xPos - shuttleTexture.getWidth() / 2, yPos - shuttleTexture.getHeight() / 2); //center shuttle at given coordinates
    }
}
