package dev.game.spacechaos.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dev.game.spacechaos.engine.camera.CameraWrapper;
import dev.game.spacechaos.engine.entity.DrawableEntity;
import dev.game.spacechaos.engine.entity.UpdatableEntity;
import dev.game.spacechaos.engine.game.BaseGame;
import dev.game.spacechaos.engine.time.GameTime;

/**
 * Created by Jo on 29.03.2017.
 */
public class SpaceShuttle extends Entity implements UpdatableEntity, DrawableEntity {

    private Texture shuttleTexture;

    public SpaceShuttle(Texture shuttleTexture, float xPos, float yPos) {
        super(xPos, yPos);

        this.shuttleTexture = shuttleTexture;

        //update width & height of space shuttle
        this.setDimension(shuttleTexture.getWidth(), shuttleTexture.getHeight());
    }

    @Override
    public void update(BaseGame game, CameraWrapper camera, GameTime time) {
        //update entity
    }

    @Override
    public void draw(GameTime time, CameraWrapper camera, SpriteBatch batch) {
        batch.draw(shuttleTexture, this.getX() - shuttleTexture.getWidth() / 2, this.getY() - shuttleTexture.getHeight() / 2); //center shuttle at given coordinates
    }

}
