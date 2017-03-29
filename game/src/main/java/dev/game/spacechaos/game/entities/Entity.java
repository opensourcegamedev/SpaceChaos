package dev.game.spacechaos.game.entities;

/**
 * Created by Justin on 29.03.2017.
 */
public class Entity {

    private volatile float xPos = 0;
    private volatile float yPos = 0;

    public Entity (float xPos, float yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public float getX () {
        return this.xPos;
    }

    public float getY () {
        return this.yPos;
    }

    public void setPos (float x, float y) {
        this.xPos = x;
        this.yPos = y;
    }

    public void move (float x, float y) {
        this.xPos += x;
        this.yPos += y;
    }

}
