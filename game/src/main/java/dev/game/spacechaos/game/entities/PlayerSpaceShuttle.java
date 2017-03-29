package dev.game.spacechaos.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dev.game.spacechaos.engine.camera.CameraWrapper;
import dev.game.spacechaos.engine.game.BaseGame;
import dev.game.spacechaos.engine.time.GameTime;
import dev.game.spacechaos.engine.utils.MouseUtils;

/**
 * Created by Jo on 29.03.2017.
 */
public class PlayerSpaceShuttle extends SpaceShuttle {

    private float MAX_SPEED = 1f;

    public PlayerSpaceShuttle(Texture shuttleTexture, float xPos, float yPos) {
        super(shuttleTexture, xPos, yPos);
    }

    @Override
    public void move(float x, float y) {
        super.move(x, y);
    }

    @Override
    public void update(BaseGame game, CameraWrapper camera, GameTime time) {
        //handle user input
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            move(0, MAX_SPEED);
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            move(0, -MAX_SPEED);
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            move(MAX_SPEED,0);
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            move(-MAX_SPEED, 0);
        }

        //update super class (SpaceShuttle)
        super.update(game, camera, time);
    }

    @Override
    public void draw(GameTime time, CameraWrapper camera, SpriteBatch batch) {
        //get mouse angle relative to shuttle
        float angle = MouseUtils.getRelativeMouseAngle(camera, getMiddleX(), getMiddleY());

        batch.draw(shuttleTextureRegion, this.getX() - getWidth() / 2, this.getY() - getHeight() / 2, 0, 0, getWidth(), getHeight(), 1f, 1f, angle); //center shuttle at given coordinates
    }

}
