package dev.game.spacechaos.game.entities;

/**
 * Created by Justin on 29.03.2017.
 */
public class Entity {

    private volatile float xPos = 0;
    private volatile float yPos = 0;
    private volatile float width = 0;
    protected volatile float height = 0;

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

    public float getWidth () {
        return this.width;
    }

    public float getHeight () {
        return this.height;
    }

    public void setDimension (float width, float height) {
        this.width = width;
        this.height = height;
    }

    public float getMiddleX () {
        return this.getX() + (width / 2);
    }

    public float getMiddleY () {
        return this.getY() + (height / 2);
    }

}
