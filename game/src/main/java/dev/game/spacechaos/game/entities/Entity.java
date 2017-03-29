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

    /**
    * set absolute position of entity
     *
     * @param x x position
     * @param y y position
    */
    public void setPos (float x, float y) {
        this.xPos = x;
        this.yPos = y;
    }

    /**
    * move entity
     *
     * @param x speed x
     * @param y speed y
    */
    public void move (float x, float y) {
        this.xPos += x;
        this.yPos += y;
    }

    /**
    * get current width of entity
     *
     * @return width of entity
    */
    public float getWidth () {
        return this.width;
    }

    /**
     * get current height of entity
     *
     * @return height of entity
     */
    public float getHeight () {
        return this.height;
    }

    /**
    * set dimension of entity
     *
     * @param width width of entity
     * @param height height of entity
    */
    public void setDimension (float width, float height) {
        this.width = width;
        this.height = height;
    }

    /**
    * get center x position of entity
     *
     * @return center x position
    */
    public float getMiddleX () {
        return this.getX() + (width / 2);
    }

    /**
     * get center y position of entity
     *
     * @return center y position
     */
    public float getMiddleY () {
        return this.getY() + (height / 2);
    }

    /**
    * cleanUp entity and dispose all resources / assets
    */
    public abstract void destroy ();

}
