package dev.game.spacechaos.game.entities.outdated;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import dev.game.spacechaos.engine.camera.CameraWrapper;
import dev.game.spacechaos.engine.entity.DrawableEntity;
import dev.game.spacechaos.engine.entity.UpdatableEntity;
import dev.game.spacechaos.engine.game.BaseGame;
import dev.game.spacechaos.engine.time.GameTime;

/**
 * Created by Jo on 01.04.2017.
 */
public abstract class Projectile extends OldEntity implements UpdatableEntity, DrawableEntity {

    private Vector2 dir;
    private Texture projectileTexture;
    private float MAX_SPEED = 2f;

    /**
     * @param xPos x position
     * @param yPos y position
     */

    public Projectile(float xPos, float yPos, Vector2 dir, Texture projectileTexture) {
        super(xPos, yPos);
        this.dir = dir.nor().scl(MAX_SPEED); //normalize and scale vector
        this.projectileTexture = projectileTexture;
    }

    @Override
    public void update(BaseGame game, CameraWrapper camera, GameTime time) {
        //update entity
        move(dir.x, dir.y); //move towards shooting direction
    }

    @Override
    public void draw(GameTime time, CameraWrapper camera, SpriteBatch batch) {
        batch.draw(projectileTexture, getX(), getY());
    }

    @Override
    public void destroy() {
        //unload texture
        this.projectileTexture = null;

        //TODO: replace this line and dont dispose texture direct, unload texture in asset manager instead
        this.projectileTexture.dispose();
    }
}
