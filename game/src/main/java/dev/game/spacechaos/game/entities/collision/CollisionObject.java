package dev.game.spacechaos.game.entities.collision;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import dev.game.spacechaos.engine.camera.CameraWrapper;
import dev.game.spacechaos.engine.entity.DrawableEntity;
import dev.game.spacechaos.engine.entity.UpdatableEntity;
import dev.game.spacechaos.engine.game.BaseGame;
import dev.game.spacechaos.engine.time.GameTime;
import dev.game.spacechaos.game.entities.outdated.OldEntity;

/**
 * Created by Justin on 05.04.2017.
 */
public class CollisionObject extends OldEntity implements UpdatableEntity, DrawableEntity {

    protected TextureRegion textureRegion = null;
    protected Rectangle collisionBox = new Rectangle(0, 0, 0, 0);

    //rotation angle
    protected float angle = 0;
    protected float rotationSpeed = 1f;

    /**
     * default constructor
     *
     * @param xPos x position
     * @param yPos y position
     */
    public CollisionObject(float xPos, float yPos, float scale, TextureRegion textureRegion) {
        super(xPos, yPos);

        this.textureRegion = textureRegion;

        //update dimension, this means with and height
        setDimension(this.textureRegion.getRegionWidth() * scale, this.textureRegion.getRegionHeight() * scale);
    }

    @Override public void update(BaseGame game, CameraWrapper camera, GameTime time) {
        //update collision box
        this.collisionBox.set(getX(), getY(), this.textureRegion.getRegionWidth(), this.textureRegion.getRegionHeight());

        //rotate object
        this.angle = (this.angle + time.getDeltaTime() * rotationSpeed * 100) % 360;

        while (this.angle < 0) {
            this.angle += 360;
        }
    }

    @Override public void draw(GameTime time, CameraWrapper camera, SpriteBatch batch) {
        //rotate and draw collsion object
        batch.draw(this.textureRegion, this.getX(), this.getY(), getWidth() / 2, getHeight() / 2, getWidth(), getHeight(), 1f, 1f, angle);
    }

    @Override public void destroy() {
        //
    }

}
