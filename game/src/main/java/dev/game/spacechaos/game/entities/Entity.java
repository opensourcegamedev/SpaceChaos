package dev.game.spacechaos.game.entities;

/**
 * Created by Justin on 29.03.2017.
 */
public abstract class Entity {

    //current absolute position of entity
    private volatile float xPos = 0;
    private volatile float yPos = 0;

    //current dimension of entity
    private volatile float width = 0;
    protected volatile float height = 0;

    /**
    * default constructor
     *
     * @param xPos x position
     * @param yPos y position
    */
    public Entity (float xPos, float yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
    }

    /**
    * get absolute x position of entity
     *
     * @return x position
    */
    public float getX () {
        return this.xPos;
    }

    /**
     * get absolute y position of entity
     *
     * @return y position
     */
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

    public abstract void destroy ();

}
