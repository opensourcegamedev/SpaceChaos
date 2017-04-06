package dev.game.spacechaos.game.fx;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import dev.game.spacechaos.engine.animation.IAnimation;
import dev.game.spacechaos.engine.camera.CameraWrapper;
import dev.game.spacechaos.engine.entity.DrawableEntity;
import dev.game.spacechaos.engine.entity.UpdatableEntity;
import dev.game.spacechaos.engine.game.BaseGame;
import dev.game.spacechaos.engine.time.GameTime;
import dev.game.spacechaos.engine.utils.AnimationTextureUtils;
import dev.game.spacechaos.game.entities.Entity;

/**
 * Created by Justin on 06.04.2017.
 */
public class ExplosionIAnimation extends Entity implements UpdatableEntity, DrawableEntity, IAnimation {

    protected Animation<TextureRegion> animation = null;
    protected TextureRegion currentFrame = null;

    protected float elapsed = 0;
    protected boolean playing = false;

    /**
     * default constructor
     *
     * @param xPos x position
     * @param yPos y position
     */
    public ExplosionIAnimation(float xPos, float yPos, int rows, int cols, float duration, Texture texture) {
        super(xPos, yPos);

        if (texture == null) {
            throw new NullPointerException("texture cannot be null.");
        }

        if (!texture.isManaged()) {
            throw new IllegalStateException("texture isnt managed or wasnt loaded.");
        }

        //create animation
        this.animation = AnimationTextureUtils.createAnimationFromTexture(texture, duration / 1000, rows, cols);
        this.animation.setPlayMode(Animation.PlayMode.NORMAL);

        //set current frame
        this.currentFrame = this.animation.getKeyFrame(1);

        //update dimension
        setDimension(currentFrame.getRegionWidth(), currentFrame.getRegionHeight());
    }

    @Override public void update(BaseGame game, CameraWrapper camera, GameTime time) {
        if (playing) {
            //calculate elapsed time in milliseconds
            this.elapsed += time.getDeltaTime();

            //get current frame of animation
            this.currentFrame = animation.getKeyFrame(elapsed, true);
        }
    }

    @Override public void draw(GameTime time, CameraWrapper camera, SpriteBatch batch) {
        if (playing) {
            batch.draw(this.currentFrame, getX(), getY(), getWidth(), getHeight());
        }
    }

    @Override public void destroy() {

    }

    @Override public void start() {
        this.playing = true;
    }

    @Override public void stop() {
        this.playing = false;
    }

    @Override public boolean isPlaying() {
        return this.playing;
    }

    @Override public void reset() {
        this.elapsed = 0;
        this.playing = false;
    }
}
