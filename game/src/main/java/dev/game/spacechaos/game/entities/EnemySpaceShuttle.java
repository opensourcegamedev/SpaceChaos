package dev.game.spacechaos.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import dev.game.spacechaos.engine.camera.CameraWrapper;
import dev.game.spacechaos.engine.game.BaseGame;
import dev.game.spacechaos.engine.time.GameTime;

/**
 * Created by Jo on 01.04.2017.
 */
public class EnemySpaceShuttle extends SpaceShuttle{

    private SpaceShuttle target;
    private Vector2 moveDir = new Vector2();
    private float MAX_SPEED = 1f;

    public EnemySpaceShuttle(Texture shuttleTexture, float xPos, float yPos, SpaceShuttle target) {
        super(shuttleTexture, xPos, yPos);
        this.target = target;
    }


    @Override
    public void update(BaseGame game, CameraWrapper camera, GameTime time) {
        //calculate target direction and move in this direction
        moveDir.set(target.getMiddleX()-getMiddleX(), target.getMiddleY()-getMiddleY()).nor();
        moveDir.scl(MAX_SPEED);
        move(moveDir.x, moveDir.y);
        super.update(game, camera, time);
    }

    @Override
    public void move(float x, float y) {
        super.move(x, y);
    }

    @Override
    public void draw(GameTime time, CameraWrapper camera, SpriteBatch batch) {
        //center shuttle at given coordinates and point to target
        batch.draw(shuttleTextureRegion, this.getX(), this.getY(), getWidth() / 2, getHeight() / 2,
                getWidth(), getHeight(), 1f, 1f, moveDir.angle()-90);
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
