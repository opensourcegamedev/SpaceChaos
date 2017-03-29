package dev.game.spacechaos.game.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import dev.game.spacechaos.engine.camera.CameraWrapper;
import dev.game.spacechaos.engine.game.BaseGame;
import dev.game.spacechaos.engine.time.GameTime;
import dev.game.spacechaos.engine.utils.MouseUtils;
import dev.game.spacechaos.engine.utils.SpriteBatcherUtils;

/**
 * Created by Jo on 29.03.2017.
 */
public class PlayerSpaceShuttle extends SpaceShuttle {

    private float MAX_SPEED = 2f;
    protected Vector2 tmpVector = new Vector2();

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
        float mouseX = camera.getMousePosition().x;
        float mouseY = camera.getMousePosition().y;

        float speedX = 0;
        float speedY = 0;

        if (MouseUtils.getRelativePositionToEntity(camera, getX(), getY()).x > 0) {
            //right
            speedX = MAX_SPEED;
        } else if (MouseUtils.getRelativePositionToEntity(camera, getX(), getY()).x > 0) {
            //left
            speedX = -MAX_SPEED;
        }
        if (mouseY > getY()) {
            //up
            speedY = MAX_SPEED;
        } else if (mouseY < getY()) {
            //down
            speedY = -MAX_SPEED;
        }

        //set values to vector
        tmpVector.set(speedX, speedY);

        //normalize and scale vector
        tmpVector.nor();
        tmpVector.scl(MAX_SPEED);

        //move entity
        move(tmpVector.x, tmpVector.y);

        //update super class (SpaceShuttle)
        super.update(game, camera, time);
    }

    @Override
    public void draw(GameTime time, CameraWrapper camera, SpriteBatch batch) {
        //get mouse angle relative to shuttle
        float angle = MouseUtils.getRelativeMouseAngle(camera, getMiddleX(), getMiddleY()) - 90;

        batch.draw(shuttleTextureRegion, this.getX(), this.getY(), getWidth() / 2, getHeight() / 2, getWidth(), getHeight(), 1f, 1f, angle); //center shuttle at given coordinates

        //draw center, only for debugging purposes
        SpriteBatcherUtils.fillRectangle(batch, getMiddleX() - 10, getMiddleY() - 10, 20, 20, Color.YELLOW);

        //draw mouse position
        Vector3 mousePos = camera.getMousePosition();
        SpriteBatcherUtils.fillRectangle(batch, mousePos.x - 5, mousePos.y - 5, 10, 10, Color.RED);

        //draw position
        SpriteBatcherUtils.fillRectangle(batch, getX() - 5, getY() - 5, 10, 10, Color.BLUE);
    }

}
