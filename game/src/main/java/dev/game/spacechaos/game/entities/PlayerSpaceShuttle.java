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
        //get mouse position relative to shuttle
        Vector2 vector = MouseUtils.getRelativePositionToEntity(camera, getX(), getY());
        float mouseX = vector.x;
        float mouseY = vector.y;

        //avoid jerky
        if (Math.abs(mouseX) < 1) {
            mouseX = 0;
        }

        if (Math.abs(mouseY) < 1) {
            mouseY = 0;
        }

        //set values to vector
        tmpVector.set(mouseX, mouseY);

        //normalize and scale vector
        tmpVector.nor();
        tmpVector.scl(MAX_SPEED);

        if (Math.abs(mouseX) > 1 || Math.abs(mouseY) > 1) {
            //move entity
            move(tmpVector.x, tmpVector.y);
        }

        //move and centralize camera
        camera.setPosition(getMiddleX() - game.getViewportWidth() / 2, getMiddleY() - game.getViewportHeight() / 2);

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
